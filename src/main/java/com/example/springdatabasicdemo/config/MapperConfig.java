package com.example.springdatabasicdemo.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

public class MapperConfig {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
