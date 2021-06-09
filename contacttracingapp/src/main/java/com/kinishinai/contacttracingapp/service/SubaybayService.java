package com.kinishinai.contacttracingapp.service;

import com.kinishinai.contacttracingapp.dto.*;
import com.kinishinai.contacttracingapp.exception.SubaybayException;
import com.kinishinai.contacttracingapp.model.*;
import com.kinishinai.contacttracingapp.repository.*;
import com.kinishinai.contacttracingapp.security.JwtProvider;
import com.kinishinai.contacttracingapp.twilio.SmsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

// main business logic
@Service

public class SubaybayService {
    @Autowired
    private PasswordEncoder PASSWORDENCODER;
    @Autowired
    private UserRepository USERREPOSITORY;
    @Autowired
    private OTPRepository OTPREPOSITORY;
    @Autowired
    private TwilioService TWILIOSERVICE;
    @Autowired
    @Qualifier("USERAUTHENTICATIONMANAGER")
    private AuthenticationManager AUTHENTICATIONMANAGER;
    @Autowired
    @Qualifier("EstablishmentAuthenticationManager")
    private AuthenticationManager AUTHENTICATIONMANAGER1;
    @Autowired
    private JwtProvider JWTPROVIDER;
    @Autowired
    private AddressRepository ADDRESSREPOSITORY;
    @Autowired
    private ImageRepository IMAGEREPOSITORY;
    @Autowired
    private RefreshTokenService REFRESHTOKENSERVICE;

    // establishment constant
    @Autowired
    private EstablishmentRepository ESTABLISHMENTREPOSITORY;
    @Autowired
    private LogoRepository LOGOREPOSITORY;
    @Autowired
    private MailService MAILSERVICE;

    // masyadong mahaba lol
    @Autowired
    private VerificationTokenRepository TOKENREPOSITORY;

    @Transactional
    public void signup(SignupRequest signupRequest){
        User user = new User();
        user.setFirstname(signupRequest.getFirstname());
        user.setMiddlename(signupRequest.getMiddlename());
        user.setLastname(signupRequest.getLastname());
        user.setBirthday(signupRequest.getBirthday());
        user.setMobileNumber(signupRequest.getMobileNumber());
        user.setVaccinated(signupRequest.isVaccinated());
        user.setPassword(PASSWORDENCODER.encode(signupRequest.getPassword()));
        user.setDateRegistered(Instant.now());
        user.setVerified(false);
        user.setAdmin(signupRequest.isAdmin());

        Address address = new Address();
        address.setUser(user);
        address.setBarangay(signupRequest.getBarangay());
        address.setCity(signupRequest.getCity());
        address.setProvince(signupRequest.getProvince());
        address.setRegion(signupRequest.getRegion());

        Image image = new Image();
        image.setUser(user);
        image.setImageName(signupRequest.getImageName());
        image.setPath(signupRequest.getPath());

        USERREPOSITORY.save(user);
        ADDRESSREPOSITORY.save(address);
        IMAGEREPOSITORY.save(image);

        String otp = generateVerificationNumber(user);
        SmsRequest smsRequest = new SmsRequest("+63"+ user.getMobileNumber(),"your subaybay app verification code is "+otp);
        TWILIOSERVICE.sendSms(smsRequest);
    }


    // check if phone number is already exist in the database
    public boolean isMobileNumberAlreadyExist(SignupRequest signupRequest) {
        long mobileNumber = signupRequest.getMobileNumber();
        return USERREPOSITORY.existsByMobileNumber(mobileNumber);
    }

    protected String generateVerificationNumber(User user) {
        String otp = generateOTP();
        OTP oneTimePassword = new OTP();
        oneTimePassword.setOtp(otp);
        oneTimePassword.setUser(user);
        OTPREPOSITORY.save(oneTimePassword);

        return otp;
    }

    private String generateOTP(){
        int randomPin   =(int) (Math.random()*9000)+1000;
        String otp  = String.valueOf(randomPin);
        return otp;
    }

    public void verifyAccount(String otp) {
        Optional<OTP> verifyOtp = OTPREPOSITORY.findByOtp(otp);
        verifyOtp.orElseThrow(()-> new SubaybayException("Invalid otp"));
        fetchUserAndEnable(verifyOtp.get());
        OTPREPOSITORY.deleteById(verifyOtp.get().getId());
    }
    @Transactional
    private void fetchUserAndEnable(OTP otp) {
        long userId = otp.getUser().getId();
        User user = USERREPOSITORY.findById(userId).orElseThrow( ()-> new SubaybayException("not found"));
        user.setVerified(true);
    }
    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = AUTHENTICATIONMANAGER.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getMobileNumber(),loginRequest.getPasssword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        String token = JWTPROVIDER.generateToken(authenticate);
        return new AuthenticationResponse(token,
                loginRequest.getMobileNumber(),
                REFRESHTOKENSERVICE.generateRefreshToken().getToken(),
                Instant.now().plusMillis(JWTPROVIDER.getJwtExpirationInMillis()));
    }

    public void verifyForgotPassword(long mobileNumber) {
        boolean userExist = USERREPOSITORY.existsByMobileNumber(mobileNumber);
        if(userExist){
            long userId = USERREPOSITORY.findByMobileNumber(mobileNumber).get().getId();
            User user = USERREPOSITORY.findById(userId).orElseThrow( ()-> new SubaybayException("not found"));

            String otp = generateVerificationNumber(user);
            SmsRequest smsRequest = new SmsRequest("+63"+ user.getMobileNumber(),"your subaybay app verification code is "+otp);
            TWILIOSERVICE.sendSms(smsRequest);
        }else {
            throw new SubaybayException("user not found");
        }
    }


    // applicable for admin, establishment, and user
    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        REFRESHTOKENSERVICE.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = JWTPROVIDER.generateTokenWithUserName(refreshTokenRequest.getUsername());
        return new AuthenticationResponse(token,
                refreshTokenRequest.getUsername(),
                REFRESHTOKENSERVICE.generateRefreshToken().getToken(),
                Instant.now().plusMillis(JWTPROVIDER.getJwtExpirationInMillis()));
    }

    @Transactional
    public void forgotPassword(String otp, ForgotPassRequest password){
        Optional<OTP> verifyOtp = OTPREPOSITORY.findByOtp(otp);
        verifyOtp.orElseThrow(()-> new SubaybayException("Invalid otp"));
        long userId = verifyOtp.get().getUser().getId();
        User user = USERREPOSITORY.findById(userId).orElseThrow( ()-> new SubaybayException("not found"));
        user.setPassword(PASSWORDENCODER.encode(password.getPassword()));
        OTPREPOSITORY.deleteById(verifyOtp.get().getId());
        USERREPOSITORY.save(user);
    }


    // TODO establishment business logic

    @Transactional
    public void signup(EstablishmentRequest establishmentRequest){
        Establishment establishment = new Establishment();
        establishment.setEstablishmentName(establishmentRequest.getEstablishmentName());
        establishment.setEmail(establishmentRequest.getEmail());
        establishment.setMobileNumber(establishmentRequest.getMobileNumber());
        establishment.setOwnerFullName(establishmentRequest.getOwnerFullName());
        establishment.setPassword(PASSWORDENCODER.encode(establishmentRequest.getPassword()));
        establishment.setDateRegistered(Instant.now());
        establishment.setVerified(false);

        Logo logo = new Logo();
        logo.setEstablishment(establishment);
        logo.setLogoName(establishmentRequest.getLogoName());
        logo.setLogoPath(establishmentRequest.getLogoPath());

        ESTABLISHMENTREPOSITORY.save(establishment);
        LOGOREPOSITORY.save(logo);

        String token = generateVerificationToken(establishment);
        MAILSERVICE.sendMail(new NotificationEmail("Please activate your account",
                establishment.getEmail(),
                "thank you for signing up to Subaybay, "+"please click the link below to verify your account: "+
                        "http://localhost:8080/subaybay/auth/establishmentVerification/" + token));
    }

    public String generateVerificationToken(Establishment establishment){
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setEstablishment(establishment);
        TOKENREPOSITORY.save(verificationToken);

        return token;
    }

    public void verifyEstablishment(String token) {
        Optional<VerificationToken> verificationToken = TOKENREPOSITORY.findByToken(token);
        verificationToken.orElseThrow(() -> new SubaybayException("invalid token"));
        fetchEstablishmentAndEnable(verificationToken.get());
        TOKENREPOSITORY.deleteById(verificationToken.get().getId());
    }

    @Transactional
    private void fetchEstablishmentAndEnable(VerificationToken verificationToken) {
        String email = verificationToken.getEstablishment().getEmail();
        Establishment establishment = ESTABLISHMENTREPOSITORY.findByEmail(email).orElseThrow(()-> new SubaybayException("user not found"));
        establishment.setVerified(true);
        ESTABLISHMENTREPOSITORY.save(establishment);
    }

    public AuthenticationResponseForEstablishment loginEstablishment(LoginRequestForEstablishment loginRequest) {
        Authentication authenticate = AUTHENTICATIONMANAGER1.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = JWTPROVIDER.generateToken(authenticate);
        return new AuthenticationResponseForEstablishment(token,loginRequest.getEmail(),
                REFRESHTOKENSERVICE.generateRefreshToken().getToken(),
                Instant.now().plusMillis(JWTPROVIDER.getJwtExpirationInMillis()));
    }

// TODO testing
    // It is also applicable when the establishment is online or authenticated
    public void verifyForgotPasswordForEstablishment(String email){
        boolean establishmentExist = ESTABLISHMENTREPOSITORY.existsByEmail(email);
        if(establishmentExist){
            Establishment establishment = ESTABLISHMENTREPOSITORY.findByEmail(email).orElseThrow( ()-> new SubaybayException("not found"));
            String otp = generateVerificationCode(establishment);
            MAILSERVICE.sendMail(new NotificationEmail("Hello, Sir/Ma'am",
                    email,
                    "Please use this verification code to change your password: " + otp));
        }
        else{
            throw new SubaybayException("Establishment not found.");
        }
    }

    public String generateVerificationCode(Establishment establishment){
        String verificationCode = generateOTP();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(verificationCode);
        verificationToken.setEstablishment(establishment);
        TOKENREPOSITORY.save(verificationToken);

        return verificationCode;
    }
// TODO testing
    @Transactional
    public void forgotPasswordForEstablishment(String otp, ForgotPassRequest password){
        Optional<VerificationToken> verificationCode = TOKENREPOSITORY.findByToken(otp);
        verificationCode.orElseThrow(()-> new SubaybayException("Invalid otp"));
        long establishmentId = verificationCode.get().getEstablishment().getId();
        Establishment establishment = ESTABLISHMENTREPOSITORY.findById(establishmentId).orElseThrow( ()-> new SubaybayException("not found"));
        establishment.setPassword(PASSWORDENCODER.encode(password.getPassword()));
        TOKENREPOSITORY.deleteById(verificationCode.get().getId());
        ESTABLISHMENTREPOSITORY.save(establishment);
    }
    // it also applicable for admin, establishment, and user
    public void logout(RefreshTokenRequest refreshTokenRequest) {
        REFRESHTOKENSERVICE.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
    }
}
