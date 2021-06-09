package com.kinishinai.contacttracingapp.config;

import com.kinishinai.contacttracingapp.security.JwtAuthenticationFilter;
import com.kinishinai.contacttracingapp.service.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.aspectj.lang.reflect.DeclarePrecedence;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
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
    @Primary
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
                .antMatchers("/v2/api-docs",
                        "/configuration/**",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/swagger-resources/configuration/ui",
                        "/v3/api-docs/**",
                        "/swagger-ui/**")
                .permitAll()
                .antMatchers("/subaybay/user")
                .hasAnyAuthority("USER","ADMIN")
                .antMatchers("/subaybay/admin")
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

//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowCredentials(true);
//        configuration.setAllowedHeaders(Arrays.asList("Authorization"));
//        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
//        configuration.setAllowedMethods(Arrays.asList("*"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//    @Bean
//    public WebMvcConfigurer configurer(){
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/*").allowedOrigins("http://localhost:3000");
//            }
//        };
//    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowedOriginPatterns(Arrays.asList("*"));
//        config.setAllowedHeaders(Arrays.asList("*"));
//        config.setAllowedMethods(Arrays.asList("*"));
//        config.setAllowCredentials(Boolean.TRUE);
//        return source;
//    }
//    @Bean(name="PASSWORDENCODERFORUSER")
//    @Primary
//    PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
}
