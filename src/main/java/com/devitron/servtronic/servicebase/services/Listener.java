package com.devitron.servtronic.servicebase.services;

import com.devitron.servtronic.messages.MessageRequest;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class Listener {

    @RabbitListener(queues = "#{ConfigBase.getServiceName}")
    public void getMessage(MessageRequest request) {

    }
}
