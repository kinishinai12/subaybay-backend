package com.kinishinai.contacttracingapp.config;

import com.kinishinai.contacttracingapp.security.JwtAuthenticationFilter;
import com.kinishinai.contacttracingapp.security.JwtAuthenticationFilterForEstablishment;
import com.kinishinai.contacttracingapp.service.EstablishmentDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;


@Configuration
@Order(2)
public class EstablishmentSecurityConfig  extends WebSecurityConfigurerAdapter {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    @Resource(name = "EstablishmentDetailsServiceImpl")
    private EstablishmentDetailsServiceImpl ESTABLISHMENTSERVICE;
    @Autowired(required=false)
    @Qualifier("JwtAuthenticationFilterForEstablishment")
    private JwtAuthenticationFilterForEstablishment JWTAUTHENTICATIONFILTER;

    @Bean(name = "EstablishmentAuthenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf().disable()
                .authorizeRequests()
                .antMatchers("/**")
                .permitAll()
                .antMatchers("/subaybay/establishment")
                .hasAuthority("ESTABLISHMENT")
                .anyRequest()
                .authenticated();
        httpSecurity.addFilterBefore(JWTAUTHENTICATIONFILTER,
                UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(ESTABLISHMENTSERVICE)
                .passwordEncoder(bCryptPasswordEncoder);
    }
//    @Bean(name="PASSWORDENCODERFORESTABLISHMENT")
//    PasswordEncoder passwordEncoder1(){
//        return new BCryptPasswordEncoder();
//    }
}
