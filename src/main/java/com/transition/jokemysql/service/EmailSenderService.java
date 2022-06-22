package com.transition.jokemysql.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service

@AllArgsConstructor
public class  EmailSenderService    {


    private final JavaMailSender mailSender;

    public void sendEmail(String to, String body, String subject) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("okorokelvinemoakpo@gmail.com");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setText(body);
        simpleMailMessage.setSubject(subject);

        mailSender.send(simpleMailMessage);
        System.out.println("mail sent ----");
    }
}
