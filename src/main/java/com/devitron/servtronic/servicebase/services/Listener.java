package com.devitron.servtronic.servicebase.services;

import com.devitron.servtronic.messages.HeaderRequest;
import com.devitron.servtronic.messages.MessageReply;
import com.devitron.servtronic.messages.MessageRequest;
import com.devitron.servtronic.servicebase.config.ConfigBase;
import com.devitron.servtronic.servicebase.data.FunctionArguments;
import com.devitron.servtronic.servicebase.data.FunctionToMethodMap;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Service
public class Listener {

    FunctionToMethodMap ftmm;
    ConfigBase configBase;

    public Listener(ConfigBase configBase) {
        ftmm = FunctionToMethodMap.FunctionToMethodFactory();
        this.configBase = configBase;
    }


    @RabbitListener(queues = "#{configBase.getServiceName()}")
    public void incoming(String inRequest) {

        MessageRequest  messageRequest = null;

        try {
            messageRequest = convertToBaseMessageRequest(inRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HeaderRequest headerRequest = messageRequest.getHeader();
        String function = headerRequest.getFunction();
        FunctionArguments functionArguments = ftmm.get(function);
        MessageRequest message = null;
        Class<?> requestClass =  functionArguments.getRequestClass();
        try {
            message = convertToMessageRequest(inRequest, requestClass);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Class<?> replyClass = functionArguments.getReplyClass();
        Method method = functionArguments.getMethod();

        MessageReply reply = null;
        try {
            reply = (MessageReply) method.invoke(null, message);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    private MessageRequest convertToBaseMessageRequest(String inRequest) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        MessageRequest mr = objectMapper.readValue(inRequest.getBytes(), MessageRequest.class);
        return mr;
    }

    private MessageRequest convertToMessageRequest(String inRequest, Class<?> messageClass) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        MessageRequest message = (MessageRequest)objectMapper.readValue(inRequest, messageClass);
        return message;
    }



}
