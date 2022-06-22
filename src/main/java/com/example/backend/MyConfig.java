package com.example.backend;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MyConfig {

    @Bean
    public JavaMailSender javaMailSender()
    {
    	JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

    	mailSender.setHost("smtp-relay.sendinblue.com");
        mailSender.setPort(587);
        
        mailSender.setUsername(System.getenv("email_bieb"));
        mailSender.setPassword(System.getenv("email_api_key"));

    	return mailSender;
    }
}
