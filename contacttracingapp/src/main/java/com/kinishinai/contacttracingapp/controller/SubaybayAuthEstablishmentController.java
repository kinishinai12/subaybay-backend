package com.kinishinai.contacttracingapp.controller;

import com.kinishinai.contacttracingapp.dto.AuthenticationResponseForEstablishment;
import com.kinishinai.contacttracingapp.dto.EstablishmentRequest;
import com.kinishinai.contacttracingapp.dto.LoginRequestForEstablishment;
import com.kinishinai.contacttracingapp.service.SubaybayService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subaybay/authestablishment")
@AllArgsConstructor
public class SubaybayAuthEstablishmentController {

    private final SubaybayService SUBAYBAYSERVICE;

    // Establishment controller
    @PostMapping("/signup")
    public ResponseEntity<String> signupEstablishment(@RequestBody EstablishmentRequest establishmentRequest){
        SUBAYBAYSERVICE.signup(establishmentRequest);
        return new ResponseEntity<>("please verified your establishment account", HttpStatus.CREATED);
    }

    @GetMapping("/establishmentVerification/{token}")
    public ResponseEntity<String> verifyEstablishment(@PathVariable String token){
        SUBAYBAYSERVICE.verifyEstablishment(token);
        return new ResponseEntity<>("account verified please login", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponseForEstablishment loginEstablishment(@RequestBody LoginRequestForEstablishment loginRequestForEstablishment){
        System.out.println("dumaan dito bago magreturn");
        return SUBAYBAYSERVICE.loginEstablishment(loginRequestForEstablishment);
    }
}
