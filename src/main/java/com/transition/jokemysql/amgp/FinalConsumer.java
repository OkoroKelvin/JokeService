package com.transition.jokemysql.amgp;

import com.transition.jokemysql.data.model.Joke;
import com.transition.jokemysql.data.model.User;
import com.transition.jokemysql.service.EmailSenderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class FinalConsumer {

    private final EmailSenderService emailSenderService;
    @RabbitListener(queues = "${spring.rabbitmq.queue.emails}")
    public void jokeConsumer (User user){
        log.info("Consumed {} from queue ",user);
        emailSenderService.sendEmail(user.getEmail(),
                user.getJoke().getContent(),
                user.getJoke().getContent());
    }
}
