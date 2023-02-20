package com.jconcept.fashionblog.services;

import com.jconcept.fashionblog.DTO.request.UserLoginRequest;
import com.jconcept.fashionblog.DTO.request.UserRegisterRequest;
import com.jconcept.fashionblog.DTO.response.DisplayUsersResponse;
import com.jconcept.fashionblog.DTO.response.UserInfoResponse;
import com.jconcept.fashionblog.exception.UserAlreadyExistException;

public interface UserService {
  UserInfoResponse registerCustomer(UserRegisterRequest userRegisterRequest) throws UserAlreadyExistException;
  UserInfoResponse registerDesigner(UserRegisterRequest userRegisterRequest) throws UserAlreadyExistException;

  String login(UserLoginRequest loginRequest);
  DisplayUsersResponse getAllUserByRole(String role);
  String findUserByEmail(String email);
  String findUserById(Long id);


}
