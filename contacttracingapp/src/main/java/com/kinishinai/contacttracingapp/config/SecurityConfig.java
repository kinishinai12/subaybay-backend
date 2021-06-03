package com.kinishinai.contacttracingapp.config;

import com.kinishinai.contacttracingapp.security.JwtAuthenticationFilter;
import com.kinishinai.contacttracingapp.service.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
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
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    @Resource(name = "UserDetailsServiceImpl")
    private UserDetailsServiceImpl USERDETAILSSERVICE;
    @Autowired(required = false)
    @Qualifier("JwtAuthenticationFilter")
    private JwtAuthenticationFilter JWTAUTHENTICATIONFILTER;
//    @Autowired
//    @Qualifier("authenticationManager1")
//    private Security authManager1;

//    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Bean(name = "USERAUTHENTICATIONMANAGER")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    //TODO kailngan mong iedit to pag gagawa ka ng bagong controller
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf().disable()
                .authorizeRequests()
                .antMatchers("/subaybay/auth/**")
                .permitAll()
                .antMatchers("/subaybay/user/**")
                .hasAnyAuthority("USER","ADMIN")
                .antMatchers("/subaybay/admin/**")
                .hasAuthority("ADMIN")
                .anyRequest()
                .authenticated();
        httpSecurity.addFilterBefore(JWTAUTHENTICATIONFILTER,
                UsernamePasswordAuthenticationFilter.class);
    }
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(USERDETAILSSERVICE)
                .passwordEncoder(bCryptPasswordEncoder);
    }
//    @Bean(name="PASSWORDENCODERFORUSER")
//    @Primary
//    PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
}
