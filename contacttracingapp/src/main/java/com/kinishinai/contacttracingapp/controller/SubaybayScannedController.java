package com.kinishinai.contacttracingapp.controller;

import com.kinishinai.contacttracingapp.dto.ScannedUserRequest;
import com.kinishinai.contacttracingapp.model.User;
import com.kinishinai.contacttracingapp.service.ScannedUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@Slf4j
//@CrossOrigin(origins="https://localhost:3000")
public class SubaybayScannedController {
    private final ScannedUserService SCANNEDUSERSERVICE;
    //TODO testing
    @PostMapping("/subaybay/user/scanneduser/{}/{whoScanId}") // endpoint
    public ResponseEntity<String> scanUser(@PathVariable long userid,@PathVariable long whoScanId, @RequestBody ScannedUserRequest scannedUserRequest){
        SCANNEDUSERSERVICE.scanUser(userid,whoScanId,scannedUserRequest);
        return new ResponseEntity<>("Successfully scan", HttpStatus.CREATED);
    }

    @GetMapping("/subaybay/admin/scanneduser/{pageNumber}/{pageSize}")
    public Page<User> getAllPaginatedUser(@PathVariable int pageNumber, @PathVariable int pageSize){
        return SCANNEDUSERSERVICE.getAllPaginatedUser(pageNumber,pageSize);
    }


    @GetMapping("/subaybay/admin/{pageNumber}/{sortBy}/{firstname}")
    public Page<User> searchByFirstname(@PathVariable Optional<String> firstname, @PathVariable Optional<Integer> pageNumber, @PathVariable Optional<String> sortBy){
        return SCANNEDUSERSERVICE.searchByFirstname(firstname,pageNumber,sortBy);
    }

    @DeleteMapping("/subaybay/admin/{scannedid}")
    public ResponseEntity<String> deleteScannedUser(@PathVariable long scannedid){
        SCANNEDUSERSERVICE.deleteAllOldRecords(scannedid);
        return new ResponseEntity<>("record deleted", HttpStatus.OK);
    }


}
