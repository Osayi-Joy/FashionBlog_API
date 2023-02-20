package com.jconcept.fashionblog.services.implementation;

import com.jconcept.fashionblog.DTO.request.UserLoginRequest;
import com.jconcept.fashionblog.DTO.request.UserRegisterRequest;
import com.jconcept.fashionblog.DTO.response.DisplayUsersResponse;
import com.jconcept.fashionblog.DTO.response.UserInfoResponse;
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
    public UserInfoResponse registerCustomer(UserRegisterRequest userRegisterRequest) {
        User user = register(userRegisterRequest);
        user.setRole(Role.CUSTOMER);
        user = userRepository.save(user);
        return new UserInfoResponse(user.getId(), user.getName(), user.getRole().toString());
    }

    @Override
    public UserInfoResponse registerDesigner(UserRegisterRequest userRegisterRequest) {
        User user = register(userRegisterRequest);
        user.setRole(Role.DESIGNER);
        userRepository.save(user);
        return new UserInfoResponse(user.getId(), user.getName(), user.getRole().toString());
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
    public String login(UserLoginRequest loginRequest) {
        User guest = getUserByEmail(loginRequest.getEmail());
        if (guest != null){
            if (guest.getPassword().equals(loginRequest.getPassword())){
                return "Login Success";
            }else {
               return "Password Incorrect";
            }
        }else {
            throw new UserNotFoundException("User Not Found");
        }
    }

    @Override
    public String findUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User With ID: " + id + " Not Found "));
        return user.getName();
    }
    @Override
    public String findUserByEmail(String email){
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("User With ID: " + email + " Not Found "));;
        return user.getName();
    }

    @Override
    public DisplayUsersResponse getAllUserByRole(String role) {
        if(role.equals("CUSTOMER")){
            List<User> userList = userRepository.findAllByRole(Role.CUSTOMER);
            return new DisplayUsersResponse("success", LocalDateTime.now(), userList);
        }else if(role.equals("DESIGNER")){
            List<User> userList = userRepository.findAllByRole(Role.DESIGNER);
            return new DisplayUsersResponse("success", LocalDateTime.now(), userList);
        }else{
            throw new UserNotFoundException("Role Not Found");
        }

    }
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("User With ID: " + email + " Not Found "));
    }

}
