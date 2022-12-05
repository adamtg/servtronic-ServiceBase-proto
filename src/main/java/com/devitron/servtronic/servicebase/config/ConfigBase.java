package com.devitron.servtronic.servicebase.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Paths;

@Configuration
public class ConfigBase {

    private static ConfigBase configBase = null;
    private String hostname = "localhost";
    private Integer port = 5672;
    private String username = "guest";
    private String password = "guest";
    private String serviceName = "service";

    private String exchange = "ServiceExchange";

    private String routingKey = "servicekey";

    private ConfigBase() {}

    public static ConfigBase configBaseFactory() {
        if (configBase == null) {
            configBase = new ConfigBase();
        }

        return configBase;
    }


    public ConfigBase load(String configFilename, Class<? extends ConfigBase> configClass) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ConfigBase config = mapper.readValue(Paths.get(configFilename).toFile(), configClass);

        return config;
    }

    public void setConfig(String configFilename, Class<? extends ConfigBase> configClass) throws IOException {
            ConfigBase config = load(configFilename, configClass);
            configBase = config;
    }


    @Bean
    public ConfigBase getConfigBase() {
        return configBase;
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
}
