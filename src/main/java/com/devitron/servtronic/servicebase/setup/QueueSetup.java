package com.devitron.servtronic.servicebase.setup;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;

public class QueueSetup {

    private String rabbitMQHost = "localhost";
    private Integer rabbitMQPort = 5672;

    public Binding setupDirectQueue(String exchangeName, String queueName, String routingKey) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(rabbitMQHost);
        connectionFactory.setPort(rabbitMQPort);

        RabbitAdmin admin = new RabbitAdmin(connectionFactory);

        Queue q = new Queue(queueName, true);
        admin.declareQueue(q);

        DirectExchange e = new DirectExchange(exchangeName, true, false);
        admin.declareExchange(e);

        Binding b =  BindingBuilder.bind(q).to(e).with(routingKey);
        admin.declareBinding(b);

        return b;
    }

    public String getRabbitMQHost() {
        return rabbitMQHost;
    }

    public void setRabbitMQHost(String rabbitMQHost) {
        this.rabbitMQHost = rabbitMQHost;
    }

    public Integer getRabbitMQPort() {
        return rabbitMQPort;
    }

    public void setRabbitMQPort(Integer rabbitMQPort) {
        this.rabbitMQPort = rabbitMQPort;
    }
}
