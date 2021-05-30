package com.kinishinai.contacttracingapp.service;

import com.kinishinai.contacttracingapp.dto.AddressRequest;
import com.kinishinai.contacttracingapp.model.Address;
import com.kinishinai.contacttracingapp.model.User;
import com.kinishinai.contacttracingapp.repository.AddressRepository;
import com.kinishinai.contacttracingapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class AddressService {
    private final AddressRepository ADDRESSREPOSITORY;
    private final UserRepository USERREPOSITORY;

    @Transactional
    public void addAddress(long userid, AddressRequest addressRequest){

        User user = USERREPOSITORY.findById(userid).get();
        Address address = new Address();
        address.setUser(user);
        address.setRegion(addressRequest.getRegion());
        address.setProvince(addressRequest.getProvince());
        address.setCity(addressRequest.getCity());
        address.setBarangay(addressRequest.getBarangay());

        ADDRESSREPOSITORY.save(address);
    }
    public List<Address> allUserAddress(long userid){
        User user = USERREPOSITORY.findById(userid).get();
        List<Address> address = ADDRESSREPOSITORY.findByUser(user);
        return address;
    }
//    public Page<User> getAllPaginatedUser(int pageNumber, int pageSize){
//        Pageable requestedPage = PageRequest.of(pageNumber, pageSize, Sort.by("id").ascending());
//        Page<User> users = USERREPOSITORY.findAll(requestedPage);
//        return users;
//    }
    @Transactional
    public void updateAddress(long addressid, AddressRequest addressRequest){
        Address address = ADDRESSREPOSITORY.findById(addressid).get();
        address.setRegion(addressRequest.getRegion());
        address.setProvince(addressRequest.getProvince());
        address.setCity(addressRequest.getCity());
        address.setBarangay(addressRequest.getBarangay());

        ADDRESSREPOSITORY.save(address);
    }
    public void deleteAddress(long addressId){
        ADDRESSREPOSITORY.deleteById(addressId);
    }
}
