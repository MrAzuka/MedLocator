package com.mrazuka.medlocator.Config;

import com.mrazuka.medlocator.Service.StoreDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final StoreDetailsService storeDetailsService;

    public SecurityConfiguration(StoreDetailsService storeDetailsService) {
        this.storeDetailsService = storeDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        disable csrf
        httpSecurity.csrf(customizer -> customizer.disable());
//        only authorized requests
        httpSecurity.authorizeHttpRequests(requests -> requests.anyRequest().authenticated());
//        allow request from other sources that are not the browser
        httpSecurity.httpBasic(Customizer.withDefaults());
//        create a stateless http, this is because we disabled csrf
        httpSecurity.sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );




        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(storeDetailsService);


        return provider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
