package com.jconcept.fashionblog.services;


import com.jconcept.fashionblog.DTO.request.EmailDto;

public interface EmailService {
    void sendEmail(String to, String subject, String text);
    void sendEmail(EmailDto emailDto);


}
