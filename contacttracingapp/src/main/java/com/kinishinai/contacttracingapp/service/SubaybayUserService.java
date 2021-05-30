package com.kinishinai.contacttracingapp.service;

import com.kinishinai.contacttracingapp.dto.RegisterRequest;
import com.kinishinai.contacttracingapp.exception.SubaybayException;
import com.kinishinai.contacttracingapp.model.OTP;
import com.kinishinai.contacttracingapp.model.User;
import com.kinishinai.contacttracingapp.repository.OTPRepository;
import com.kinishinai.contacttracingapp.repository.UserRepository;
import com.kinishinai.contacttracingapp.twilio.SmsRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class SubaybayUserService {

    private final UserRepository USERREPOSITORY;
    private final TwilioService TWILIOSERVICE;
    private final SubaybayService SUBAYBAYSERVICE;
    private final PasswordEncoder PASSWORDENCODER;
    private final OTPRepository OTPREPOSITORY;

    public User getProfile(long mobileNumber) {
        User user = USERREPOSITORY.findByMobileNumber(mobileNumber).get();
        return user;
    }
    // hindi ko alam bat mobile number yung ginamit ko lol
    @Transactional
    public void updateUser(long mobileNumber, RegisterRequest registerRequest){

        long userid = USERREPOSITORY.findByMobileNumber(mobileNumber).get().getId();
        User user = USERREPOSITORY.findById(userid).get();

        if(USERREPOSITORY.existsByMobileNumber(mobileNumber)){
            if(isUserSame(userid,registerRequest)){

                user.setFirstname(registerRequest.getFirstname());
                user.setMiddlename(registerRequest.getMiddlename());
                user.setLastname(registerRequest.getLastname());
                user.setBirthday(registerRequest.getBirthday());
                user.setVaccinated(registerRequest.isVaccinated());

                USERREPOSITORY.save(user);
            }
            else{
                throw new SubaybayException("user already exist");
            }
        }else{
            user.setFirstname(registerRequest.getFirstname());
            user.setMiddlename(registerRequest.getMiddlename());
            user.setLastname(registerRequest.getLastname());
            user.setBirthday(registerRequest.getBirthday());
            user.setVaccinated(registerRequest.isVaccinated());
            user.setMobileNumber(registerRequest.getMobileNumber());
            user.setVerified(false);

            USERREPOSITORY.save(user);

            String otp = SUBAYBAYSERVICE.generateVerificationNumber(user);
            SmsRequest smsRequest = new SmsRequest("+63"+ user.getMobileNumber(),"your subaybay app verification code is "+otp);
            TWILIOSERVICE.sendSms(smsRequest);
        }

    }
    public void deleteUser(long mobileNumber){
        User user = USERREPOSITORY.findByMobileNumber(mobileNumber).get();
        USERREPOSITORY.delete(user);
    }

    public boolean isUserSame(long id,RegisterRequest registerRequest){
        long userid = USERREPOSITORY.findByMobileNumber(registerRequest.getMobileNumber()).get().getId();
        return userid == id;
    }

    public void verifyForgotPassword(long mobileNumber) {

        User user = USERREPOSITORY.findByMobileNumber(mobileNumber).orElseThrow( ()-> new SubaybayException("not found"));

        String otp = SUBAYBAYSERVICE.generateVerificationNumber(user);
        SmsRequest smsRequest = new SmsRequest("+63"+ user.getMobileNumber(),"your subaybay app verification code is "+otp);
        TWILIOSERVICE.sendSms(smsRequest);

    }

    @Transactional
    public void forgotPassword(String otp, RegisterRequest password){
        Optional<OTP> verifyOtp = OTPREPOSITORY.findByOtp(otp);
        verifyOtp.orElseThrow(()-> new SubaybayException("Invalid otp"));
        long userId = verifyOtp.get().getUser().getId();
        User user = USERREPOSITORY.findById(userId).orElseThrow( ()-> new SubaybayException("not found"));
        user.setPassword(PASSWORDENCODER.encode(password.getPassword()));
        OTPREPOSITORY.deleteById(verifyOtp.get().getId());
        USERREPOSITORY.save(user);
    }


}
