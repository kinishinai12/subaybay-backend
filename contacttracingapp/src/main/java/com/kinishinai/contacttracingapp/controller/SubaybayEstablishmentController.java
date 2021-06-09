package com.kinishinai.contacttracingapp.controller;

import com.kinishinai.contacttracingapp.dto.EstablishmentRequest;
import com.kinishinai.contacttracingapp.dto.ForgotPassRequest;
import com.kinishinai.contacttracingapp.model.Establishment;
import com.kinishinai.contacttracingapp.repository.EstablishmentRepository;
import com.kinishinai.contacttracingapp.service.SubaybayEstablishmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subaybay/establishment/")
@AllArgsConstructor
public class SubaybayEstablishmentController {
    //TODO wala pang testing

    private final SubaybayEstablishmentService ESTABLISHMENTSERVICE;

    @PutMapping("/updateestablishment/{establishmentid}")
    public ResponseEntity<String> updateEstablishment(@PathVariable long establishmentid, @RequestBody EstablishmentRequest establishmentRequest){
        ESTABLISHMENTSERVICE.updateEstablishment(establishmentid, establishmentRequest);
        return new ResponseEntity<>("successfully updated. if you change your email in the process please see our mail to activate your account again. thank you", HttpStatus.OK);
    }
    @GetMapping("/getestablishment/{email}")
    public Establishment getProfile(@PathVariable String email){
        return ESTABLISHMENTSERVICE.getProfile(email);
    }
    @GetMapping("/deleteestablishment/{establishmentid}")
    public ResponseEntity<String> deleteEstablishment(@PathVariable long establishmentid){
        ESTABLISHMENTSERVICE.delete(establishmentid);
        return new ResponseEntity<>("establishment deleted", HttpStatus.OK);
    }

}
