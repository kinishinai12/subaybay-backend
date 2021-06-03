//package com.kinishinai.contacttracingapp.security;
//
//import com.kinishinai.contacttracingapp.service.EstablishmentDetailsServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component("JwtAuthenticationFilterForEstablishment")
//@Configuration
//public class JwtAuthenticationFilterForEstablishment extends OncePerRequestFilter {
//    @Autowired
//    private JwtProvider jwtProvider;
//    @Autowired
//    @Qualifier("EstablishmentDetailsServiceImpl")
//    private EstablishmentDetailsServiceImpl userDetailsService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
//        String jwt = getJwtRequest(httpServletRequest);
//        System.out.println("gawan mo to ng logic burahin mo na yung isang filter");
//        if(StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt)){
//
//            String username = jwtProvider.getUsernameFromJwt(jwt);
//
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//        filterChain.doFilter(httpServletRequest,httpServletResponse);
//    }
//
//    private String getJwtRequest(HttpServletRequest httpServletRequest) {
//        String bearerToken = httpServletRequest.getHeader("Authorization");
//
//        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
//            return bearerToken.substring(7);
//        return bearerToken;
//    }
//}
