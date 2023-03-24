package com.jconcept.fashionblog.services.implementation;

import com.jconcept.fashionblog.DTO.request.EmailDto;
import com.jconcept.fashionblog.exception.CustomException;
import com.jconcept.fashionblog.services.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
@Slf4j
@Service
public class EmailServiceImplementation implements EmailService {
    @Value("${spring.mail.username}")
    private String emailFrom;

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String emailTo, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom);
        message.setTo(emailTo);
        message.setSubject(subject);
        message.setText(content);

        javaMailSender.send(message);
    }

    @Override
    @Async
    public void sendEmail(EmailDto emailDto) {
        log.info("inside Send email, building mail!!");
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom(emailDto.getSender());
            mimeMessageHelper.setTo(emailDto.getRecipient());
            mimeMessageHelper.setSubject(emailDto.getSubject());
            mimeMessageHelper.setText(emailDto.getBody());
        };
        try {

            CompletableFuture.runAsync(() ->
                    javaMailSender.send(mimeMessagePreparator)).exceptionally(exp -> {
                throw new CustomException("Exception occurred sending mail [message]: " + exp.getLocalizedMessage());
            });
            log.info("email has sent!!");
        }catch (MailException exception) {
            log.error("Exception occurred when sending mail {}",exception.getMessage());
            throw new CustomException("Exception occurred when sending mail to " + emailDto.getRecipient(), HttpStatus.EXPECTATION_FAILED);
        }
    }






}
