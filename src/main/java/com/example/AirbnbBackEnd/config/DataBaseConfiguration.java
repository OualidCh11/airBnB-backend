package com.example.AirbnbBackEnd.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories({"com.example.AirbnbBackEnd",})
@EnableTransactionManagement
@EnableJpaAuditing
public class DataBaseConfiguration {
}
