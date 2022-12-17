package com.devitron.servtronic.servicebase.services;

import com.devitron.servtronic.messages.HeaderRequest;
import com.devitron.servtronic.messages.MessageReply;
import com.devitron.servtronic.messages.MessageRequest;
import com.devitron.servtronic.servicebase.config.ConfigBase;
import com.devitron.servtronic.servicebase.data.FunctionArguments;
import com.devitron.servtronic.servicebase.data.FunctionToMethodMap;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Service
public class Listener {

    private FunctionToMethodMap ftmm;

    private ConfigBase configBase;

    private Publisher publisher;


    public Listener(ConfigBase configBase, Publisher publisher) {
        ftmm = FunctionToMethodMap.FunctionToMethodFactory();
        this.configBase = configBase;
        this.publisher = publisher;
    }

    @RabbitListener(queues = "#{getConfig.getServiceName}")
    public void incoming(String inRequest) {

        MessageRequest  messageRequest = null;

        try {
            messageRequest = convertToBaseMessageRequest(inRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("===============================================>");
        System.out.println(inRequest);
        System.out.println("===============================================>");


        HeaderRequest headerRequest = messageRequest.getHeader();
        String function = headerRequest.getFunction();
        System.out.println("==============================> " + function);
        FunctionArguments functionArguments = ftmm.get(function);
        MessageRequest message = null;
        Class<?> requestClass =  functionArguments.getRequestClass();
        message = convertToMessageRequest(inRequest, requestClass);

        Class<?> replyClass = functionArguments.getReplyClass();
        Method method = functionArguments.getMethod();

        MessageReply reply = null;
        try {
            reply = (MessageReply) method.invoke(null, message);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        if (reply != null) {
            try {
                publisher.send(reply);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private MessageRequest convertToBaseMessageRequest(String inRequest) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);;
        MessageRequest mr = objectMapper.readValue(inRequest.getBytes(), MessageRequest.class);
        return mr;
    }

    private MessageRequest convertToMessageRequest(String inRequest, Class<?> messageClass)  {

        ObjectMapper objectMapper = new ObjectMapper();
        MessageRequest message = null;
        try {
            message = (MessageRequest)objectMapper.readValue(inRequest, messageClass);
        } catch (JsonProcessingException e) {
            System.out.println("-----------------------------------------------------------------");
            e.getMessage();
            e.printStackTrace();
            System.out.println("-----------------------------------------------------------------");

        }
        return message;
    }



}
