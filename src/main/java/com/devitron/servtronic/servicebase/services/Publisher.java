package com.devitron.servtronic.servicebase.services;

import com.devitron.servtronic.messages.ServiceRegistration;
import com.devitron.servtronic.servicebase.config.ConfigBase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class Publisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ConfigBase config;

    public Publisher() {
    }

    public <T> void send(T message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        //Class<T> c = (Class<T>) message.getClass();
        String stringMessage = objectMapper.writeValueAsString(message);

        rabbitTemplate.convertAndSend(config.getMcIncomingExchange(), config.getMcIncomingRoutingKey(), stringMessage);
    }

    public void register() throws JsonProcessingException {
        ServiceRegistration.Request request = new ServiceRegistration.Request(
                config.getServiceName(), config.getRoutingKey(), config.getExchange());

        send(request);
    }
}
