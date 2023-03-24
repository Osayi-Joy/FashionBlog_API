package com.jconcept.fashionblog;

import com.jconcept.fashionblog.DTO.request.EmailDto;
import com.jconcept.fashionblog.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class FashionBlogApplication {
    @Autowired
    private EmailService sendMailService;

    public static void main(String[] args) {
        SpringApplication.run(FashionBlogApplication.class, args);
    }
        EmailDto emailDto = new EmailDto("THIS IS THE SUBJECT","osayijoy17@gmail.com", "thisnsis a test", "codewithjconcept@gmail.com");
        @EventListener(ApplicationReadyEvent.class)
        public void sendMail(){
            sendMailService.sendEmail(emailDto);
        }





}
