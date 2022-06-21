package com.example.backend;

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
    	mailSender.setPort(1025);
    	return mailSender;
    }
}
