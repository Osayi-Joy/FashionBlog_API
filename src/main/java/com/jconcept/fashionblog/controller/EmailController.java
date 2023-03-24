package com.jconcept.fashionblog.controller;

import com.jconcept.fashionblog.DTO.request.EmailDto;
import com.jconcept.fashionblog.DTO.response.BaseResponse;
import com.jconcept.fashionblog.services.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@Tag(name = "Email Controller")
@AllArgsConstructor
@RequestMapping("mail")
public class EmailController {

    private final EmailService sendMailService;

    @PostMapping()
    @Operation(summary = "Send Email",
            security = {@SecurityRequirement(name = "bearer-token")},
            responses = {
                    @ApiResponse(responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Boolean.class)))})
    public ResponseEntity<BaseResponse<Boolean>> sendMail(@RequestBody @Valid final EmailDto emailDto) {
        log.info("email controller -: sending email to [{}]", emailDto.getRecipient());
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> sendMailService.sendEmail(emailDto));

        return
                future.isDone() ?
                        ResponseEntity.ok(BaseResponse.<Boolean>builder()
                                .message("email sent successfully")
                                .data(true)
                                .build()
                        )
                        :
                        ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(BaseResponse.<Boolean>builder()
                                .message("email Not successful")
                                .data(false)
                                .build()
                        );

    }
}
