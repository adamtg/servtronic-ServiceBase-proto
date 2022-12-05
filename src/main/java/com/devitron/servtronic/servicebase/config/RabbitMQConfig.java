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

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(ConfigBase.getHostname());
        connectionFactory.setPort(ConfigBase.getPort());
        connectionFactory.setUsername(ConfigBase.getUsername());
        connectionFactory.setPassword(ConfigBase.getPassword());

        RabbitAdmin admin = new RabbitAdmin(connectionFactory);

        Queue q = new Queue(ConfigBase.getServiceName(), true);
        admin.declareQueue(q);

        DirectExchange e = new DirectExchange(ConfigBase.getExchange(), true, false);
        admin.declareExchange(e);

        Binding b =  BindingBuilder.bind(q).to(e).with(ConfigBase.getRoutingKey());
        admin.declareBinding(b);


        return connectionFactory;
    }
}
