package com.devitron.servtronic.servicebase.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(ConfigBase.getHostname());
        connectionFactory.setPort(ConfigBase.getPort());
        connectionFactory.setUsername(ConfigBase.getUsername());
        connectionFactory.setPassword(ConfigBase.getPassword());
        return connectionFactory;
    }
}
