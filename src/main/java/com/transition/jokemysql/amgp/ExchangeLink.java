package com.transition.jokemysql.amgp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangeLink {

    @Value("${spring.rabbitmq.exchanges.internal}")
    private String internalExchange;

    @Value("${spring.rabbitmq.queue.emails}")
    private String emailsQueue;


    @Value("${spring.rabbitmq.routing-key.internal-emails}")
    private String internalEmailsRoutingKey;

    @Bean
    public Queue emailsQueue(){
        return new Queue(this.emailsQueue);
    }

    @Bean
    public Binding internalToEmailBinding(){
        return BindingBuilder.bind(emailsQueue()).to(internalTopicExchange())
                .with(this.internalEmailsRoutingKey);
    }

    @Bean
    public TopicExchange internalTopicExchange(){
        return new TopicExchange(this.internalExchange);
    }


    public String getInternalExchange() {
        return internalExchange;
    }

    public void setInternalExchange(String internalExchange) {
        this.internalExchange = internalExchange;
    }

    public String getEmailsQueue() {
        return emailsQueue;
    }

    public void setEmailsQueue(String emailsQueue) {
        this.emailsQueue = emailsQueue;
    }

    public String getInternalEmailsRoutingKey() {
        return internalEmailsRoutingKey;
    }

    public void setInternalEmailsRoutingKey(String internalEmailsRoutingKey) {
        this.internalEmailsRoutingKey = internalEmailsRoutingKey;
    }
}
