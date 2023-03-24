package com.jconcept.fashionblog.controller;
import com.jconcept.fashionblog.DTO.request.ForgotPasswordDto;
import com.jconcept.fashionblog.DTO.request.UserLoginRequest;
import com.jconcept.fashionblog.DTO.request.UserRegisterRequest;
import com.jconcept.fashionblog.DTO.response.BaseResponse;
import com.jconcept.fashionblog.DTO.response.TokenResponse;
import com.jconcept.fashionblog.DTO.response.UserInfoResponse;
import com.jconcept.fashionblog.entity.ResetPasswordDto;
import com.jconcept.fashionblog.entity.User;
import com.jconcept.fashionblog.exception.CustomException;
import com.jconcept.fashionblog.services.PasswordResetService;
import com.jconcept.fashionblog.services.UserService;
import com.jconcept.fashionblog.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.jconcept.fashionblog.util.SecurityConstants.PASSWORD_NOT_MATCH_MSG;
import static com.jconcept.fashionblog.util.SecurityUtil.doesBothStringMatch;


@Slf4j
@Tag(name = "Authentication Controller")
@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;
    private final PasswordResetService passwordService;


    @PostMapping(path = "/register/designer")
    @Operation(summary = "Register New User", responses = {
            @ApiResponse(responseCode = "201",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserRegisterRequest.class)))})
    public ResponseEntity<BaseResponse<?>> registerDesigner(@RequestBody @Valid final UserRegisterRequest request) {
        log.info("controller register: register user :: [{}] ::", request.getEmail());
        validateUserRegistrationDto(request);
        UserInfoResponse response = userService.registerDesigner(request);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/auth/register/designer").toUriString());
        return ResponseEntity.created(uri).body(BaseResponse.builder()
                .data(response)
                .message("Successfully Registered")
                .status(HttpStatus.CREATED).build());
    }

    @PostMapping(path = "/register/customer")
    @Operation(summary = "Register New User", responses = {
            @ApiResponse(responseCode = "201",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserRegisterRequest.class)))})
    public ResponseEntity<BaseResponse<?>> registerCustomer(@RequestBody @Valid final UserRegisterRequest request) {
        log.info("controller register: register user :: [{}] ::", request.getEmail());
        validateUserRegistrationDto(request);
        UserInfoResponse response = userService.registerCustomer(request);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/auth/register/customer").toUriString());
        return ResponseEntity.created(uri).body(BaseResponse.builder()
                .data(response)
                .message("Successfully Registered")
                .status(HttpStatus.CREATED).build());
    }


    @PostMapping("/login")
    @Operation(summary = "Login User", responses = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TokenResponse.class)))})
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody UserLoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        if (authentication.isAuthenticated()) {
            TokenResponse response = SecurityUtil.generateToken(authentication);
            return ResponseEntity.status(200)
                    .body(BaseResponse
                            .builder()
                            .data(response)
                            .message("Authenticated")
                            .status(HttpStatus.OK)
                            .build());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

    private void validateUserRegistrationDto(UserRegisterRequest request) {
        log.info("validating user registration request for email :: {}", request.getEmail());
        if (!doesBothStringMatch(request.getConfirmPassword(), request.getPassword())) {
            throw new CustomException(PASSWORD_NOT_MATCH_MSG, HttpStatus.BAD_REQUEST);
        }
        List<String> roleEnum = List.of("CUSTOMER", "DESIGNER");

        String role = request.getRole();
        if (role != null) {
            role = role.trim().toUpperCase();
            if (!roleEnum.contains(role)) {
                throw new CustomException("Invalid role, Options includes: CUSTOMER, DESIGNER", HttpStatus.BAD_REQUEST);
            }
        }
        log.info("successful validation for user registration request for email :: {}", request.getEmail());
    }


    @PostMapping("/forget_password")
    public ResponseEntity<BaseResponse<?>> forgetPassword(@RequestBody ForgotPasswordDto passwordDto,
                                                          HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
        User user = userService.getUserByEmail(passwordDto.getEmail());
        String url = "";
        if(user == null) {
            String token = UUID.randomUUID().toString();
            passwordService.createPasswordResetTokenForUser(user, token);
            url = passwordService.passwordResetTokenMail(user, passwordService.applicationUrl(request), token);
        }
        log.info("Sending a reset password link to {}", passwordDto.getEmail());
        passwordService.sendEmail(passwordDto.getEmail(), url);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/user/forget_password").toUriString());
        return ResponseEntity.created(uri).body(BaseResponse.builder().message("Password reset link sent to your email").build());
    }

    @PostMapping("/reset_Password")
    public ResponseEntity<BaseResponse<?>> resetPassword(@RequestParam("token") String token, @RequestBody ResetPasswordDto passwordDto) {
        String result = passwordService.validatePasswordResetToken(token);

        if(!result.equalsIgnoreCase("valid")) {
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/user/reset_password").toUriString());
            return ResponseEntity.created(uri).body(BaseResponse.builder().message("Invalid Token").build());
        }

        Optional<User> user = userService.getUserByPasswordResetToken(token);
        if(user.isPresent()) {
            passwordService.changePassword(user.get(), passwordDto);
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/user/reset_password").toUriString());
            return ResponseEntity.created(uri).body(BaseResponse.builder().message("Password changed successfully").build());
        } else {
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/user/reset_password").toUriString());
            return ResponseEntity.created(uri).body(BaseResponse.builder().message("User not found").build());
        }
    }


}
