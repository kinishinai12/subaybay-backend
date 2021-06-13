package com.kinishinai.contacttracingapp.service;

import com.kinishinai.contacttracingapp.dto.ScannedUserByEstablishmentRequest;
import com.kinishinai.contacttracingapp.model.Establishment;
import com.kinishinai.contacttracingapp.model.ScannedUserByEstablishment;
import com.kinishinai.contacttracingapp.model.User;
import com.kinishinai.contacttracingapp.repository.EstablishmentRepository;
import com.kinishinai.contacttracingapp.repository.ScannedUserByEstablishmentRepository;
import com.kinishinai.contacttracingapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class ScannedUserByEstablishmentService {

    //TODO testing

    private final ScannedUserByEstablishmentRepository SUBER;
    private final EstablishmentRepository ESTABLISHMENTREPOSITORY;
    private final UserRepository USERREPOSITORY;

    public Page<Establishment> getAllEstablishment(int pageNumber, int pageSize){
        Pageable requestedPage = PageRequest.of(pageNumber, pageSize, Sort.by("id").ascending());
        Page<Establishment> establishments = ESTABLISHMENTREPOSITORY.findAll(requestedPage);
        return establishments;
    }

    @Transactional
    public void ScanUserByEstablishment(long userid,
                                        long establishmentid,
                                        ScannedUserByEstablishmentRequest
                                                scannedUserByEstablishmentRequest){

        ScannedUserByEstablishment scannedUserByEstablishment = new ScannedUserByEstablishment();
        User user = USERREPOSITORY.findById(userid).get();
        Establishment establishment = ESTABLISHMENTREPOSITORY.findById(establishmentid).get();
        scannedUserByEstablishment.setUser(user);
        scannedUserByEstablishment.setScanByWhatEstablihsment(establishment);
        scannedUserByEstablishment.setDateScanned(Instant.now());
        scannedUserByEstablishment.setTemperature(scannedUserByEstablishmentRequest.getTemperature());

        SUBER.save(scannedUserByEstablishment);
    }

    public void deleteScanned(long scanid){
        SUBER.deleteById(scanid);
    }

    public Page searchByEstablishment(){
        return null;
    }

    public Page searchByFirstname(){
        return null;
    }

    public Page sortByDate(){
        return null;
    }

    public Page sortByEstablishment(){
        return null;
    }

    public Page<ScannedUserByEstablishment> allScannedByEstablishment(int pageNumber, int pageSize) {
        Pageable requestedPage = PageRequest.of(pageNumber, pageSize, Sort.by("dateScanned").ascending());
        Page<ScannedUserByEstablishment> ScannedUsersByEstablishment = SUBER.findAll(requestedPage);
        return ScannedUsersByEstablishment;
    }


    // TODO sort endpoint
    // TODO search endpoint
}
