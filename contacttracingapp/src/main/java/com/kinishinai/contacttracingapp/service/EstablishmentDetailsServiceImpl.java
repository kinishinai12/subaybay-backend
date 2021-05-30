package com.kinishinai.contacttracingapp.service;

import com.kinishinai.contacttracingapp.model.Establishment;
import com.kinishinai.contacttracingapp.repository.EstablishmentRepository;
import lombok.AllArgsConstructor;
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

import static java.util.Collections.singletonList;

@Service("EstablishmentDetailsServiceImpl")
@AllArgsConstructor
@Configuration
public class EstablishmentDetailsServiceImpl implements UserDetailsService {

    private final EstablishmentRepository ESTABLISHMENTREPOSITORY;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("hello andito ako");
        Establishment establishment = ESTABLISHMENTREPOSITORY.findByEmail(username).get();


        return new org.springframework.security
                .core.userdetails.User(establishment.getEmail(), establishment.getPassword(),
                establishment.isVerified(), true, true,
                true, getAuthorities("ESTABLISHMENT"));
    }
    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return singletonList(new SimpleGrantedAuthority(role));
    }
}
