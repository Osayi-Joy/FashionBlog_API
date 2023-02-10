package com.jconcept.fashionblog.services.implementation;

import com.jconcept.fashionblog.DTO.request.UserLoginRequest;
import com.jconcept.fashionblog.DTO.request.UserRegisterRequest;
import com.jconcept.fashionblog.DTO.response.DisplayUsersResponse;
import com.jconcept.fashionblog.DTO.response.LoginResponse;
import com.jconcept.fashionblog.DTO.response.UserRegisterResponse;
import com.jconcept.fashionblog.entity.Role;
import com.jconcept.fashionblog.entity.User;
import com.jconcept.fashionblog.exception.UserAlreadyExistException;
import com.jconcept.fashionblog.exception.UserNotFoundException;
import com.jconcept.fashionblog.repository.UserRepository;
import com.jconcept.fashionblog.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@Service
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserRegisterResponse registerCustomer(UserRegisterRequest userRegisterRequest) throws UserAlreadyExistException {
        User user = register(userRegisterRequest);
        user.setRole(Role.CUSTOMER);
        userRepository.save(user);
        return new UserRegisterResponse("success" , LocalDateTime.now() , user);
    }

    @Override
    public UserRegisterResponse registerDesigner(UserRegisterRequest userRegisterRequest) throws UserAlreadyExistException {
        User user = register(userRegisterRequest);
        user.setRole(Role.DESIGNER);
        userRepository.save(user);
        return new UserRegisterResponse("success" , LocalDateTime.now() , user);
    }

    private User register(UserRegisterRequest userRegisterRequest) {
        Optional<User> existingUser = userRepository.findByEmail(userRegisterRequest.getEmail());
        if(existingUser.isPresent()) throw new UserAlreadyExistException("User Already exist");
        User user =  new User();
        user.setName(userRegisterRequest.getName());
        user.setEmail(userRegisterRequest.getEmail());
        user.setPassword(userRegisterRequest.getPassword());
        return user;
    }

    @Override
    public LoginResponse login(UserLoginRequest loginRequest) {
        User guest = findUserByEmail(loginRequest.getEmail());
        LoginResponse loginResponse = null;
        if (guest != null){
            if (guest.getPassword().equals(loginRequest.getPassword())){
                loginResponse = new LoginResponse("success" , LocalDateTime.now());
            }else {
                loginResponse = new LoginResponse("password MisMatch" , LocalDateTime.now());
            }
        }
        return loginResponse;
    }

    @Override
    public User findUserById(Long id){
        return userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User With ID: " + id + " Not Found "));
    }
    @Override
    public User findUserByEmail(String email){
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()) throw new UserNotFoundException("User With email: " + email + " Not Found ");
        return user.get();
    }

    @Override
    public DisplayUsersResponse getAllUserByRole(String role) {
        if(role.equals("CUSTOMER")){
            List<User> userList = userRepository.findAllByRole(Role.CUSTOMER);
            return new DisplayUsersResponse("success", LocalDateTime.now(), userList);
        }else {
            List<User> userList = userRepository.findAllByRole(Role.DESIGNER);
            return new DisplayUsersResponse("success", LocalDateTime.now(), userList);
        }

    }

}
