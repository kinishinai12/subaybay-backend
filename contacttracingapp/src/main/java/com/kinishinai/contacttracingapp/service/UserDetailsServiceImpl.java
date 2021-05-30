package com.kinishinai.contacttracingapp.service;

import com.kinishinai.contacttracingapp.model.Establishment;
import com.kinishinai.contacttracingapp.model.User;
import com.kinishinai.contacttracingapp.repository.EstablishmentRepository;
import com.kinishinai.contacttracingapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

import static java.util.Collections.singletonList;

@Service("UserDetailsServiceImpl")
@Configuration
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository USERREPOSITORY;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        User user = USERREPOSITORY.findByMobileNumber(Long.parseLong(username)).get();


        return new org.springframework.security
                .core.userdetails.User(String.valueOf(user.getMobileNumber()), user.getPassword(),
                user.isVerified(), true, true,
                true, getAuthorities(getRole(user)));

//
//        if(!USERREPOSITORY.existsByMobileNumber(Long.parseLong(username))){
//            System.out.println("dumaan dito sa establishment");
//            Optional<Establishment> establishmentOptional = ESTABLISHMENTREPOSITORY.findByEmail(username);
//
//            Establishment establishment = establishmentOptional
//                    .orElseThrow(() -> new UsernameNotFoundException("No establishment " +
//                            "Found with email : " + username));
//
//            return new org.springframework.security
//                    .core.userdetails.User(establishment.getEmail(), establishment.getPassword(),
//                    establishment.isVerified(), true, true,
//                    true, getAuthorities("ESTABLISHMENT"));
//
//        }
//        System.out.println("dumaan dito sa user");
//        Optional<User> userOptional = USERREPOSITORY.findByMobileNumber(Long.parseLong(username));
//
//        User user = userOptional
//                .orElseThrow(() -> new UsernameNotFoundException("No user " +
//                        "Found with Mobile number : " + username));
//
//        return new org.springframework.security
//                .core.userdetails.User(String.valueOf(user.getMobileNumber()), user.getPassword(),
//                user.isVerified(), true, true,
//                true, getAuthorities(getRole(user)));



    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return singletonList(new SimpleGrantedAuthority(role));
    }

    private String getRole(User user){
        return user.isAdmin()? "ADMIN":"USER";
    }
}
