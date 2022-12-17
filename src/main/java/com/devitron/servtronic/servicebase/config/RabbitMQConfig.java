package com.devitron.servtronic.servicebase.config;

import com.devitron.servtronic.messages.ServiceRegistration;
import com.devitron.servtronic.servicebase.services.Publisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Configuration
public class RabbitMQConfig {

    private ConfigBase config;

    @Bean
    public ConnectionFactory connectionFactory(ConfigBase config) {
        System.out.println("=============================================================");
        System.out.println("Running connectionFactory");
        System.out.println("=============================================================");


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

        System.out.println("=============================================================");
        System.out.println("Registering");
        System.out.println("=============================================================");

        ServiceRegistration.Request request = new ServiceRegistration.Request(
                config.getServiceName(), config.getRoutingKey(), config.getExchange());

        ObjectMapper objectMapper = new ObjectMapper();
        String stringMessage = null;
        try {
            stringMessage = objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

        try {
            com.rabbitmq.client.ConnectionFactory factory = new com.rabbitmq.client.ConnectionFactory();
            factory.setHost(config.getHostname());
            factory.setPort(config.getPort());
            factory.setUsername(config.getUsername());
            factory.setPassword(config.getPassword());
            Connection connection  = factory.newConnection();
            Channel channel = connection.createChannel();



            channel.basicPublish(config.getRegistrationExchange(), config.getRegistrationRoutingKey(),
                    null, stringMessage.getBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        } catch (TimeoutException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

        return connectionFactory;
    }
}
