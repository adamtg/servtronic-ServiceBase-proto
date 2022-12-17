package com.devitron.servtronic.servicebase.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Paths;

public class ConfigBase {

    private String hostname = "localhost";
    private Integer port = 5672;
    private String username = "guest";
    private String password = "guest";
    private String serviceName = "service";

    private String exchange = "ServiceExchange";

    private String routingKey = "servicekey";

    private String registrationExchange = "MessageController";
    private String registrationRoutingKey = "registration";

    private String mcIncomingExchange = "MessageController";
    private String mcIncomingRoutingKey = "incoming";


    public ConfigBase() {}


    public static ConfigBase load(String configFilename, Class<? extends ConfigBase> configClass) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ConfigBase config = mapper.readValue(Paths.get(configFilename).toFile(), configClass);

        return config;
    }


    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public String getRegistrationExchange() {
        return registrationExchange;
    }

    public void setRegistrationExchange(String registrationExchange) {
        this.registrationExchange = registrationExchange;
    }

    public String getRegistrationRoutingKey() {
        return registrationRoutingKey;
    }

    public void setRegistrationRoutingKey(String registrationRoutingKey) {
        this.registrationRoutingKey = registrationRoutingKey;
    }

    public String getMcIncomingExchange() {
        return mcIncomingExchange;
    }

    public void setMcIncomingExchange(String mcIncomingExchange) {
        this.mcIncomingExchange = mcIncomingExchange;
    }

    public String getMcIncomingRoutingKey() {
        return mcIncomingRoutingKey;
    }

    public void setMcIncomingRoutingKey(String mcIncomingRoutingKey) {
        this.mcIncomingRoutingKey = mcIncomingRoutingKey;
    }
}
