package com.kinishinai.contacttracingapp.controller;


import com.kinishinai.contacttracingapp.dto.AddressRequest;
import com.kinishinai.contacttracingapp.dto.ImageRequest;
import com.kinishinai.contacttracingapp.dto.RegisterRequest;
import com.kinishinai.contacttracingapp.model.Address;
import com.kinishinai.contacttracingapp.model.Image;
import com.kinishinai.contacttracingapp.model.User;
import com.kinishinai.contacttracingapp.service.AddressService;
import com.kinishinai.contacttracingapp.service.ImageService;
import com.kinishinai.contacttracingapp.service.SubaybayUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
//@CrossOrigin(origins="https://localhost:3000")
public class SubaybayUserController {

    private final SubaybayUserService SUBAYBAYUSERSERVICE;
    private final ImageService IMAGESERVICE;
    private final AddressService ADDRESSERVICE;

    @GetMapping("/subaybay/user/profile/{mobileNumber}")
    public User getProfile(@PathVariable long mobileNumber){
        return SUBAYBAYUSERSERVICE.getProfile(mobileNumber);
    }

    @PutMapping("/subaybay/user/profile/update/{mobileNumber}")
    public ResponseEntity<String> updateUser(@PathVariable long mobileNumber, @RequestBody RegisterRequest registerRequest){

        SUBAYBAYUSERSERVICE.updateUser(mobileNumber, registerRequest);

        return new ResponseEntity<>("Successfully Updated", HttpStatus.OK);
    }

    @DeleteMapping("/subaybay/user/profile/delete/{mobileNumber}")
    public ResponseEntity<String> deleteUser(@PathVariable long mobileNumber){
        SUBAYBAYUSERSERVICE.deleteUser(mobileNumber);
        return new ResponseEntity<>("user deleted successfully",HttpStatus.OK);
    }

    @GetMapping("/subaybay/user/profile/verifyforgotpassword/{mobileNumber}")
    public ResponseEntity<String> verifyForgotPassword(@PathVariable long mobileNumber){
        SUBAYBAYUSERSERVICE.verifyForgotPassword(mobileNumber);
        return new ResponseEntity<>("please change your password", HttpStatus.OK);
    }

    @PutMapping("/subaybay/user/profile/forgotpassword/{otp}")
    public ResponseEntity<String> forgetPassword(@PathVariable String otp, @RequestBody RegisterRequest registerRequest){
        SUBAYBAYUSERSERVICE.forgotPassword(otp,registerRequest);
        return new ResponseEntity<>("changed password successfully", HttpStatus.OK);
    }

    /*TODO image controller*/

    //TODO update profile picture and testing
    @PutMapping("/subaybay/user/profile/changedimage/{userid}")
    public ResponseEntity<String> updateProfileImage(@PathVariable long userid, @RequestBody ImageRequest imageRequest){
        IMAGESERVICE.updateImage(userid,imageRequest);
        return new ResponseEntity<>("image saved",HttpStatus.CREATED);
    }

    @DeleteMapping("/subaybay/user/profile/deleteimage/{imageid}")
    public ResponseEntity<String> deleteProfileImage(@PathVariable long imageid){
        IMAGESERVICE.deleteImage(imageid);
        return new ResponseEntity<>("image deleted",HttpStatus.OK);
    }

    @GetMapping("/subaybay/user/profile/getimage/{userid}")
    public Image getImage(@PathVariable long userid){
        Image image = IMAGESERVICE.getImage(userid);
        return image;
    }


    @GetMapping("/subaybay/user/profile/getalluseradress/{userid}")
    public List<Address> getAllAddressOfTheUser(@PathVariable long userid){
        return ADDRESSERVICE.allUserAddress(userid);
    }

    @PutMapping("/subaybay/user/profile/updateaddress/{addressid}")
    public ResponseEntity<String> updateAddress(@PathVariable long addressid, @RequestBody AddressRequest addressRequest){
        ADDRESSERVICE.updateAddress(addressid,addressRequest);
        return new ResponseEntity<>("address updated", HttpStatus.OK);
    }

    @DeleteMapping("/subaybay/user/profile/deleteaddress/{addressid}")
    public ResponseEntity<String> deleteAddress(@PathVariable  long addressid){
        ADDRESSERVICE.deleteAddress(addressid);
        return new ResponseEntity<>("address deleted", HttpStatus.OK);
    }

    @PostMapping("/subaybay/user/profile/addaddress/{userid}")
    public ResponseEntity<String> addAddress(@PathVariable long userid, @RequestBody AddressRequest addressRequest){
        ADDRESSERVICE.addAddress(userid,addressRequest);
        return new ResponseEntity<>("address added", HttpStatus.CREATED);
    }

//    @GetMapping("/search/{keyword}")
//    public Page

//    @GetMapping("/SearchingProducts/{productName}/{pageNumber}")
//    public Page<Products> liveSearch(@PathVariable Optional<String> productName,
//                                     @PathVariable Optional<Integer> pageNumber,
//                                     @RequestParam Optional<String> sortBy){
//
//        Pageable requestedPage = PageRequest.of(pageNumber.orElse(0), 5, Sort.Direction.ASC, sortBy.orElse("id"));
//        return productRepository.findByProductName(productName.orElse("_"), requestedPage);
//        //		return productRepository.findByProductName(productName.orElse("_"),
////				new PageRequest(pageNumber.orElse(0), 5, Sort.Direction.ASC, sortBy.orElse("id")));
//    }
}
