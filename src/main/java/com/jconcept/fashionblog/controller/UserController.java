package com.jconcept.fashionblog.controller;

import com.jconcept.fashionblog.DTO.request.UserLoginRequest;
import com.jconcept.fashionblog.DTO.request.UserRegisterRequest;
import com.jconcept.fashionblog.DTO.response.DisplayUsersResponse;
import com.jconcept.fashionblog.DTO.response.LoginResponse;
import com.jconcept.fashionblog.DTO.response.UserRegisterResponse;
import com.jconcept.fashionblog.entity.Post;
import com.jconcept.fashionblog.entity.Role;
import com.jconcept.fashionblog.entity.User;
import com.jconcept.fashionblog.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@Slf4j
@RequestMapping( value = "/api")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping(value = "/register-customer")
    public ResponseEntity<UserRegisterResponse> registerCustomer(@RequestBody UserRegisterRequest userRegisterRequest){
        log.info("Successfully Registered {} " , userRegisterRequest.getEmail());
        return new ResponseEntity<>(userService.registerCustomer(userRegisterRequest) , CREATED);
    }
    @PostMapping(value = "/register-designer")
    public ResponseEntity<UserRegisterResponse> registerDesigner(@RequestBody UserRegisterRequest userRegisterRequest){
        log.info("Successfully Registered {} " , userRegisterRequest.getEmail());
        return new ResponseEntity<>(userService.registerDesigner(userRegisterRequest) , CREATED);
    }
    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> userLogin(@RequestBody UserLoginRequest loginRequest) {
        log.info("Welcome! Successfully logged in {} " , loginRequest.getEmail());
        return new ResponseEntity<>(userService.login(loginRequest), OK);
    }

//    @GetMapping(value = "/displayUsers/{role}")
//    public ResponseEntity<DisplayUsersResponse> fetchAllUsers(@PathVariable(value = "role") String role) {
//        log.info("Welcome! Successfully displayed all Users who are {} ", role);
//        return new ResponseEntity<>(userService.getAllUserByRole(role), OK);
//    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<User> findUserById(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok().body(userService.findUserById(id));
    }

    @GetMapping(value = "/users/email/{email}")
    public ResponseEntity<User> findUserByEmail(@PathVariable(value="email") String email){
        return ResponseEntity.ok().body(userService.findUserByEmail(email));
    }

    @GetMapping(value = "/users/role")
    public ResponseEntity<DisplayUsersResponse> findUserByRole(@RequestParam String role){
        return new ResponseEntity<>(userService.getAllUserByRole(role), OK);
    }
}
