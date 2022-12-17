package com.devitron.servtronic.servicebase.config;

import com.devitron.servtronic.servicebase.services.Publisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceBaseConfig {

    @Bean
    Publisher getPublisher() {
        return new Publisher();
    }

}
