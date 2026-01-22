package com.backend.springboot.tickets.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TicketConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


}
