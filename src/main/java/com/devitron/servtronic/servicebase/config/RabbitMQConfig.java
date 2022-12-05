package com.devitron.servtronic.servicebase.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        System.out.println("=============================================================");
        System.out.println("Running connectionFactory");
        System.out.println("=============================================================");

        ConfigBase config = ConfigBase.configBaseFactory();

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(config.getHostname());
        connectionFactory.setPort(config.getPort());
        connectionFactory.setUsername(config.getUsername());
        connectionFactory.setPassword(config.getPassword());

        RabbitAdmin admin = new RabbitAdmin(connectionFactory);

        Queue q = new Queue(config.getServiceName(), true);
        admin.declareQueue(q);

        DirectExchange e = new DirectExchange(config.getExchange(), true, false);
        admin.declareExchange(e);

        Binding b =  BindingBuilder.bind(q).to(e).with(config.getRoutingKey());
        admin.declareBinding(b);


        return connectionFactory;
    }
}
