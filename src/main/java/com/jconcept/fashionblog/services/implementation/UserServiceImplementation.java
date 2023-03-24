package com.jconcept.fashionblog.services.implementation;

import com.jconcept.fashionblog.DTO.request.UserLoginRequest;
import com.jconcept.fashionblog.DTO.request.UserRegisterRequest;
import com.jconcept.fashionblog.DTO.response.UserInfoResponse;
import com.jconcept.fashionblog.entity.User;
import com.jconcept.fashionblog.enums.Role;
import com.jconcept.fashionblog.exception.CustomException;
import com.jconcept.fashionblog.exception.UserNotFoundException;
import com.jconcept.fashionblog.repository.PasswordResetTokenRepository;
import com.jconcept.fashionblog.repository.UserRepository;
import com.jconcept.fashionblog.services.UserService;
import com.jconcept.fashionblog.util.EmailValidatorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
@Slf4j
@AllArgsConstructor
@Service
public class UserServiceImplementation implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> getUserByPasswordResetToken(String token) {

        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).get().getUser());

    }




    @Override
    public UserInfoResponse registerCustomer(UserRegisterRequest userRegisterRequest) {
        User user = register(userRegisterRequest);
        user.setRole(Role.CUSTOMER);
        user.addRole(Role.CUSTOMER);
        userRepository.save(user);
        return new UserInfoResponse(user.getName(), user.getEmail());
    }


    @Override
    public UserInfoResponse registerDesigner(UserRegisterRequest userRegisterRequest) {
        User user = register(userRegisterRequest);
        user.setRole(Role.DESIGNER);
        user.addRole(Role.DESIGNER);
        userRepository.save(user);
        return new UserInfoResponse(user.getName(), user.getEmail());
    }

    private User register(UserRegisterRequest request) {
        if ("".equals(request.getEmail().trim())) {
            throw new CustomException("Email can not be empty");
        }
        String email = request.getEmail().toLowerCase();
        if(!EmailValidatorService.isValid(email)){
            throw new CustomException("Enter a valid email address");
        }
        var newUser = userRepository.findByEmail(email);
        if (newUser.isPresent()) {
            throw new CustomException("User already exist");
        }
        User user =  new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
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

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("User With ID: " + email + " Not Found "));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("userService loadUserByUserName - email :: [{}] ::", email);
        log.info("User ==> [{}]", userRepository.findByEmail(email));
        User user = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> {
                            throw new CustomException("user does not exist");
                        }
                );

        Collection<SimpleGrantedAuthority> authorities = Collections
                .singleton(new SimpleGrantedAuthority(user.getRole().name()));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }


//    @Override
//    public DisplayUsersResponse getAllUserByRole(String role) {
//        if(role.equals("CUSTOMER")){
//            List<User> userList = userRepository.findAllByRole(RoleEnum.USER);
//            return new DisplayUsersResponse("success", LocalDateTime.now(), userList);
//        }else if(role.equals("DESIGNER")){
//            List<User> userList = userRepository.findAllByRole(RoleEnum.ADMIN);
//            return new DisplayUsersResponse("success", LocalDateTime.now(), userList);
//        }else{
//            throw new UserNotFoundException("Role Not Found");
//        }
//
//    }

}
