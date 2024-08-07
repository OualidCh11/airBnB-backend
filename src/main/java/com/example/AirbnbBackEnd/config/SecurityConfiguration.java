package com.example.AirbnbBackEnd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.CsrfTokenRequestResolver;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception{
        CsrfTokenRequestAttributeHandler requestAttributeHandler = new CsrfTokenRequestAttributeHandler();
        requestAttributeHandler.setCsrfRequestAttributeName(null);
        httpSecurity.authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.GET,"api/tenant-listing/get-all-by-category").permitAll()
                .requestMatchers(HttpMethod.GET, "api/tenant-listing/get-one").permitAll()
                .requestMatchers(HttpMethod.POST, "api/tenant-listing/search").permitAll()
                .requestMatchers(HttpMethod.GET, "api/booking/check-availability").permitAll()
                .requestMatchers(HttpMethod.GET, "assets/*").permitAll()
                .anyRequest().authenticated()).csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .csrfTokenRequestHandler(requestAttributeHandler)).oauth2Login(Customizer.withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .oauth2Client(Customizer.withDefaults());
        return httpSecurity.build();
    }
}
