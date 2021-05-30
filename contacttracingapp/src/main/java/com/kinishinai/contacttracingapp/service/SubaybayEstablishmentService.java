package com.kinishinai.contacttracingapp.service;

import com.kinishinai.contacttracingapp.dto.EstablishmentRequest;
import com.kinishinai.contacttracingapp.exception.SubaybayException;
import com.kinishinai.contacttracingapp.model.Establishment;
import com.kinishinai.contacttracingapp.model.NotificationEmail;
import com.kinishinai.contacttracingapp.repository.EstablishmentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class SubaybayEstablishmentService {

    private final EstablishmentRepository ESTABLISHMENTREPOSITORY;
    private final SubaybayService SUBAYBAYSERVICE;
    private final MailService MAILSERVICE;

    // TODO testing
    public Establishment getProfile(String email){
        Establishment establishment = ESTABLISHMENTREPOSITORY.findByEmail(email).get();
        return establishment;
    }
    // TODO TESTING
    @Transactional
    public void updateEstablishment(long establishmentid, EstablishmentRequest establishmentRequest){

        Establishment establishment = ESTABLISHMENTREPOSITORY.findById(establishmentid).get();
        if(ESTABLISHMENTREPOSITORY.existsByEmail(establishment.getEmail())){
            if(isUserSame(establishmentid,establishmentRequest)){
                establishment.setEstablishmentName(establishmentRequest.getEstablishmentName());
                establishment.setEmail(establishmentRequest.getEmail());
                establishment.setMobileNumber(establishmentRequest.getMobileNumber());
                establishment.setOwnerFullName(establishmentRequest.getOwnerFullName());

                ESTABLISHMENTREPOSITORY.save(establishment);
            }
            else{
                throw new SubaybayException("establishment already exist");
            }
        }

        else{

            establishment.setEstablishmentName(establishmentRequest.getEstablishmentName());
            establishment.setEmail(establishmentRequest.getEmail());
            establishment.setMobileNumber(establishmentRequest.getMobileNumber());
            establishment.setOwnerFullName(establishmentRequest.getOwnerFullName());
            establishment.setVerified(false);

            ESTABLISHMENTREPOSITORY.save(establishment);

            String token = SUBAYBAYSERVICE.generateVerificationToken(establishment);
            MAILSERVICE.sendMail(new NotificationEmail("Please activate your account",
                    establishment.getEmail(),
                    "this is Subaybay, " + "please click the link below to verify your account: " +
                            "http://localhost:8080/subaybay/establishmentauth/establishmentVerification/" + token));
        }
    }
    // checks if the user is same
    public boolean isUserSame(long id, EstablishmentRequest establishmentRequest){
        long establishmentid = ESTABLISHMENTREPOSITORY.findByEmail(establishmentRequest.getEmail()).get().getId();
        return establishmentid == id;
    }

    public void delete(long id){
        Establishment establishment = ESTABLISHMENTREPOSITORY.findById(id).get();
        ESTABLISHMENTREPOSITORY.delete(establishment);
    }

    // TODO forgot password

}
