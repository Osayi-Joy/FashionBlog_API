package com.jconcept.fashionblog.services;

import com.jconcept.fashionblog.DTO.request.UserLoginRequest;
import com.jconcept.fashionblog.DTO.request.UserRegisterRequest;
import com.jconcept.fashionblog.DTO.response.DisplayUsersResponse;
import com.jconcept.fashionblog.DTO.response.LoginResponse;
import com.jconcept.fashionblog.DTO.response.UserRegisterResponse;
import com.jconcept.fashionblog.entity.Role;
import com.jconcept.fashionblog.entity.User;
import com.jconcept.fashionblog.exception.UserAlreadyExistException;

public interface UserService {
  UserRegisterResponse registerCustomer(UserRegisterRequest userRegisterRequest) throws UserAlreadyExistException;
  UserRegisterResponse registerDesigner(UserRegisterRequest userRegisterRequest) throws UserAlreadyExistException;

  LoginResponse login(UserLoginRequest loginRequest);
  DisplayUsersResponse getAllUserByRole(String role);
  User findUserByEmail(String email);
  User findUserById(Long id);


}
