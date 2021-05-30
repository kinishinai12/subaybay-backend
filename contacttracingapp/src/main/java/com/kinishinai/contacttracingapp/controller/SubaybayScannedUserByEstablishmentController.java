package com.kinishinai.contacttracingapp.controller;

import com.kinishinai.contacttracingapp.dto.ScannedUserByEstablishmentRequest;
import com.kinishinai.contacttracingapp.model.Establishment;
import com.kinishinai.contacttracingapp.service.ScannedUserByEstablishmentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/subaybay/admin/")
public class SubaybayScannedUserByEstablishmentController {
    // TODO testing
    // SUBES means Scanned User By Establishment Service
    private final ScannedUserByEstablishmentService SUBES;

    @PostMapping("/scanbyestablishment/{userid}/{establishmentid}")
    public ResponseEntity<String> scanUser(@PathVariable long userid, @PathVariable long establishmentid, @RequestBody ScannedUserByEstablishmentRequest request){
        SUBES.ScanUserByEstablishment(userid,establishmentid,request);
        return new ResponseEntity<>("done scanning", HttpStatus.CREATED);
    }

    @DeleteMapping("/deletescanuser/{id}")
    public ResponseEntity<String> deleteScannedUser(long scanid){
        SUBES.deleteScanned(scanid);
        return new ResponseEntity<>("scanned deleted", HttpStatus.OK);
    }

    @GetMapping("/getallestablishment/{pageNumber}/{pageSize}")
    public Page<Establishment> getAllEstablishment(@PathVariable int pageNumber, @PathVariable int pageSize){
        return SUBES.getAllEstablishment(pageNumber, pageSize);
    }



}
