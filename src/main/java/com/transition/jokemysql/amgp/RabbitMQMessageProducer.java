package com.transition.jokemysql.amgp;

import com.transition.jokemysql.data.model.Joke;
import com.transition.jokemysql.data.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class RabbitMQMessageProducer {
    private final AmqpTemplate amqpTemplate;
    public void publish(User payload, String exchange, String routingKey){
        log.info("Publishing to {} using routingKey {}. Payload:{}",exchange, routingKey,payload);
        amqpTemplate.convertAndSend(exchange,routingKey,payload);
        log.info("Published to {} using routingKey {}. Payload:{}",exchange, routingKey,payload);
    }

}
