package com.kinishinai.contacttracingapp.controller;

import com.kinishinai.contacttracingapp.model.Address;
import com.kinishinai.contacttracingapp.model.Establishment;
import com.kinishinai.contacttracingapp.model.ScannedUserByEstablishment;
import com.kinishinai.contacttracingapp.model.User;
import com.kinishinai.contacttracingapp.repository.AddressRepository;
import com.kinishinai.contacttracingapp.repository.ScannedUserByEstablishmentRepository;
import com.kinishinai.contacttracingapp.repository.UserRepository;
import com.kinishinai.contacttracingapp.service.ScannedUserByEstablishmentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/subaybay/auth/open/endpoint")
public class OpenEndpointController {

    private final UserRepository USERREPOSITORY;
    private final AddressRepository ADDRESSREPOSITORY;

    private final ScannedUserByEstablishmentRepository SUBER;
    private final ScannedUserByEstablishmentService SUBES;

    @GetMapping("/ScannedByEstablishment/{pageNumber}/{pageSize}")
    public Page<Establishment> getAllEstablishment(@PathVariable int pageNumber, @PathVariable int pageSize){
        return SUBES.getAllEstablishment(pageNumber, pageSize);
    }

    @GetMapping("/getAllScannedByEstablisment/{pageNumber}/{pageSize}")
    public Page<ScannedUserByEstablishment> getAllScanned(@PathVariable int pageNumber, @PathVariable int pageSize){
        Pageable requestedPage = PageRequest.of(pageNumber, pageSize, Sort.by("dateScanned").ascending());
        Page<ScannedUserByEstablishment> ScannedUsersByEstablishment = SUBER.findAll(requestedPage);
        return ScannedUsersByEstablishment;
    }
    @GetMapping("userAddress/{userid}")
    public List<Address> allUserAddress(@PathVariable long userid){
        User user = USERREPOSITORY.findById(userid).get();
        List<Address> address = ADDRESSREPOSITORY.findByUser(user);
        return address;
    }

    @GetMapping("/Elective")
    public String walaNamanTinuroNagpaprojectPa(){
        return "<h1>Sanay ka ba magturo? o hanggang basa ka lang ng ppt mo tapos tatagalugin mo lang? " +
                "mas mabilis ka pa magklase kesa sa fast talk ni boy abunda e. " +
                "ano ka si girl amunda?</h1>";
    }
}
