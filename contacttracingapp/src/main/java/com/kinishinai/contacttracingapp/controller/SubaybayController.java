package com.kinishinai.contacttracingapp.controller;

import com.kinishinai.contacttracingapp.dto.*;
import com.kinishinai.contacttracingapp.service.SubaybayService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/subaybay/auth")
@AllArgsConstructor
public class SubaybayController {

    private final SubaybayService SUBAYBAYSERVICE;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest signupRequest){
        if(SUBAYBAYSERVICE.isMobileNumberAlreadyExist(signupRequest))
            return new ResponseEntity<>("user already exist", HttpStatus.FOUND);

        SUBAYBAYSERVICE.signup(signupRequest);

        return new ResponseEntity<>(
                "you are successfully register, you just need to verify your account"
                , HttpStatus.CREATED);
    }

    @GetMapping("/verify/{otp}")
    public ResponseEntity<String> verifyAccount(@PathVariable String otp){
        SUBAYBAYSERVICE.verifyAccount(otp);
        return new ResponseEntity<String> ("verified",HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest){
        return SUBAYBAYSERVICE.login(loginRequest);
    }

    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return SUBAYBAYSERVICE.refreshToken(refreshTokenRequest);
    }

    @GetMapping("/verifyforgotpassword/{mobileNumber}")
    public ResponseEntity<String> verifyForgotPassword(@PathVariable long mobileNumber){
        SUBAYBAYSERVICE.verifyForgotPassword(mobileNumber);
        return new ResponseEntity<>("please change your password", HttpStatus.OK);
    }

    @PutMapping("/forgotpassword/{otp}")
    public ResponseEntity<String> forgetPassword(@PathVariable String otp, @RequestBody ForgotPassRequest forgotPassRequest){
        SUBAYBAYSERVICE.forgotPassword(otp,forgotPassRequest);
        return new ResponseEntity<>("changed password successfully", HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        SUBAYBAYSERVICE.logout(refreshTokenRequest);
        return ResponseEntity.status(HttpStatus.OK).body("Refresh Token Deleted Successfully!!");
    }

}
