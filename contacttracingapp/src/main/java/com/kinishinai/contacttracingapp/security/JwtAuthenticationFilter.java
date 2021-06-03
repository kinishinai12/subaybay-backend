package com.kinishinai.contacttracingapp.security;

import com.kinishinai.contacttracingapp.service.EstablishmentDetailsServiceImpl;
import com.kinishinai.contacttracingapp.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

// TODO debug this and make another filter
@Component("JwtAuthenticationFilter")
@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired(required=false)
    @Qualifier("UserDetailsServiceImpl")
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    @Qualifier("EstablishmentDetailsServiceImpl")
    private EstablishmentDetailsServiceImpl establishmentDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String jwt = getJwtRequest(httpServletRequest);

        if(StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt)){

            UsernamePasswordAuthenticationToken authentication = null;

            String username = jwtProvider.getUsernameFromJwt(jwt);


            if(!isNumber(username)){
                UserDetails establishmentDetails = establishmentDetailsService.loadUserByUsername(username);
                authentication = new UsernamePasswordAuthenticationToken(establishmentDetails, null, establishmentDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            }else{
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            }


            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    private String getJwtRequest(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
            return bearerToken.substring(7);
        return bearerToken;
    }

    private boolean isNumber(String username){
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        return username == null? false:pattern.matcher(username).matches();
    }
}
