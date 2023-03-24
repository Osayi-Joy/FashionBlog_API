package com.jconcept.fashionblog.DTO.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailDto {

    @NotBlank
    private String subject;

    @NotBlank
    @Email
    private String recipient;

    @NotBlank
    private String body;

    @NotBlank
    private String sender;

    private String attachment;
    private String cc;
    private String bcc;

    public EmailDto(String subject, String recipient, String body, String sender) {
        this.subject = subject;
        this.recipient = recipient;
        this.body = body;
        this.sender = sender;
    }
}