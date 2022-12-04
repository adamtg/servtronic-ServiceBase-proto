package com.devitron.servtronic.servicebase.config;

public class ConfigBase {

    private static String hostname = "localhost";
    private static Integer port = 5672;
    private static String username = "guest";
    private static String password = "guest";
    private static String serviceName;

    public static String getHostname() {
        return hostname;
    }

    public static void setHostname(String hostname) {
        ConfigBase.hostname = hostname;
    }

    public static Integer getPort() {
        return port;
    }

    public static void setPort(Integer port) {
        ConfigBase.port = port;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        ConfigBase.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        ConfigBase.password = password;
    }

    public static String getServiceName() {
        return serviceName;
    }

    public static void setServiceName(String serviceName) {
        ConfigBase.serviceName = serviceName;
    }
}
