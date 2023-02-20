package com.jconcept.fashionblog.controller;

import com.jconcept.fashionblog.DTO.request.UserLoginRequest;
import com.jconcept.fashionblog.DTO.request.UserRegisterRequest;
import com.jconcept.fashionblog.DTO.response.BaseResponse;
import com.jconcept.fashionblog.DTO.response.DisplayUsersResponse;
import com.jconcept.fashionblog.DTO.response.UserInfoResponse;
import com.jconcept.fashionblog.services.UserService;
import com.jconcept.fashionblog.util.ApiResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping( value = "/api")
public class UserController {
    private final UserService userService;


    @PostMapping(value = "/register-customer")
    public ResponseEntity<BaseResponse<UserInfoResponse>> registerCustomer(@RequestBody UserRegisterRequest userRegisterRequest){
        return ApiResponseUtil.response(CREATED, userService.registerCustomer(userRegisterRequest), "Successfully Registered");
    }
    @PostMapping(value = "/register-designer")
    public ResponseEntity<BaseResponse<UserInfoResponse>> registerDesigner(@RequestBody UserRegisterRequest userRegisterRequest){
        return ApiResponseUtil.response(CREATED, userService.registerDesigner(userRegisterRequest), "Successfully Registered");
    }
    @PostMapping(value = "/login")
    public ResponseEntity<BaseResponse<String>> userLogin(@RequestBody UserLoginRequest loginRequest) {
        return ApiResponseUtil.response(OK, userService.login(loginRequest), "Successfully Logged In");
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<BaseResponse<String>> findUserById(@PathVariable(value = "id") Long id){
        return ApiResponseUtil.response(OK, userService.findUserById(id), "Found User");
    }

    @GetMapping(value = "/users/email/{email}")
    public ResponseEntity<BaseResponse<String>> findUserByEmail(@PathVariable(value="email") String email){
        return ApiResponseUtil.response(OK, userService.findUserByEmail(email), "Found User");
    }

    @GetMapping(value = "/users/role")
    public ResponseEntity<DisplayUsersResponse> findUserByRole(@RequestParam String role){
        return new ResponseEntity<>(userService.getAllUserByRole(role), OK);
    }
}
