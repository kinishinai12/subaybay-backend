package com.kinishinai.contacttracingapp.service;

import com.kinishinai.contacttracingapp.dto.ScannedUserRequest;
import com.kinishinai.contacttracingapp.model.ScannedUser;
import com.kinishinai.contacttracingapp.model.User;
import com.kinishinai.contacttracingapp.repository.ScannedUserRepository;
import com.kinishinai.contacttracingapp.repository.UserRepository;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@AllArgsConstructor
@Slf4j
public class ScannedUserService {
    private final ScannedUserRepository SCANNEDUSERREPOSITORY;
    private final UserRepository USERREPOSITORY;

    //TODO controller
    @Transactional
    public void scanUser(long userid, long idWhoscanBy, ScannedUserRequest scannedUserRequest){
        User user = USERREPOSITORY.findById(userid).get();
        User whoScan = USERREPOSITORY.findById(idWhoscanBy).get();
        ScannedUser scannedUser = new ScannedUser();
        scannedUser.setUser(user);
        scannedUser.setDateScanned(scannedUserRequest.getDateScanned());
        scannedUser.setTemperature(scannedUserRequest.getTemperature());
        scannedUser.setLocationWhereScanned(scannedUserRequest.getLocationWhereScanned());
        scannedUser.setPrevLocation(scannedUserRequest.getPrevLocation());
        scannedUser.setPositive(false);
        scannedUser.setScanBy(whoScan);

        SCANNEDUSERREPOSITORY.save(scannedUser);
    }
    //TODO testing
    public Page<User> getAllPaginatedUser(int pageNumber, int pageSize){
        Pageable requestedPage = PageRequest.of(pageNumber, pageSize, Sort.by("id").ascending());
        Page<User> users = USERREPOSITORY.findAll(requestedPage);
        return users;
    }
    // TODO live search and testing all search below and controller
    // paginated list of users.
    // search by firstname
    public Page<User> searchByFirstname(Optional<String> firstname, Optional<Integer> pageNumber, Optional<String> sortBy){
        Pageable requestedPage = PageRequest.of(pageNumber.orElse(0), 5, Sort.Direction.ASC, sortBy.orElse("id"));
        return USERREPOSITORY.findByFirstname(firstname.orElse("_"), requestedPage);
    }

    public Page<ScannedUser> searchByPrevLocation(){
        return null;
    }

    public Page<ScannedUser> searchByAddress(){
        return null;
    }

    public Page<ScannedUser> searchByLocationWhereScanned(){
        return null;
    }

    // tentative
    public Page<ScannedUser> searchByPositive(){
        return null;
    }
    // TODO testing and controller
    public Page<ScannedUser> paginatedByDateScanned(int pageNumber, int pageSize){
        Pageable requestedPage = PageRequest.of(pageNumber, pageSize, Sort.by("dateScanned").descending());
        Page<ScannedUser> scannedUsers = SCANNEDUSERREPOSITORY.findAll(requestedPage);
        return scannedUsers;
    }
    // TODO testing and controller
    public Page<ScannedUser> paginatedByLocation(int pageNumber, int pageSize){
        Pageable requestedPage = PageRequest.of(pageNumber, pageSize, Sort.by("locationWhereScanned").descending());
        Page<ScannedUser> scannedUsers = SCANNEDUSERREPOSITORY.findAll(requestedPage);
        return scannedUsers;
    }
    // TODO testing and controller
    public void deleteAllOldRecords(long scannedid){
        SCANNEDUSERREPOSITORY.deleteById(scannedid);
    }

//    @GetMapping("/paginatedproducts/{pageNumber}/{pageSize}")
//    public Page<Products> paginatedAndSortedProducts(@PathVariable int pageNumber, @PathVariable int pageSize){
//        Pageable requestedPage = PageRequest.of(pageNumber, pageSize, Sort.by("productName").ascending());
//        Page<Products> products = productRepository.findAll(requestedPage);
//        return products;
//    }
}
