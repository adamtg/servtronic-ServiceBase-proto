package com.devitron.servtronic.servicebase.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public ConfigBase getConfigBase() {
        System.out.println("=========================================");
        System.out.println("getConfigBase in class");
        System.out.println("=========================================");

        return new ConfigBase();
    }

}
