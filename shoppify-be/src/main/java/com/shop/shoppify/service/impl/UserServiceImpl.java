package com.shop.shoppify.service.impl;

import com.shop.shoppify.dto.ChangePasswordDTO;
import com.shop.shoppify.dto.UserDTO;
import com.shop.shoppify.enums.RoleEnum;
import com.shop.shoppify.model.Role;
import com.shop.shoppify.model.User;
import com.shop.shoppify.repository.RoleRepository;
import com.shop.shoppify.repository.UserRepository;
import com.shop.shoppify.security.SecurityConfiguration;
import com.shop.shoppify.service.UserService;
import com.shop.shoppify.util.ShoppifyException;
import com.shop.shoppify.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    SecurityConfiguration configuration;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDTO register(UserDTO userDto) {
        User existUser = userRepository.findByEmail(userDto.getEmail());
        if(existUser != null) {
            throw new ShoppifyException(HttpStatus.BAD_REQUEST, "Email already exist!");
        }
        User user = new User(userDto);
        user.setPassword(configuration.passwordEncoder().encode(userDto.getPassword()));
        user.setRole(roleRepository.getOne(RoleEnum.USER.getValue()));
        String token = TokenUtil.generateToken();
        user.setVerificationToken(token);

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("name", user.getFirstName());
        paramMap.put("token", token);
        boolean isSent = SendEmail.sendEmail(user.getEmail(), "", " ", "Confirm Registration", paramMap);
        if(isSent){
            System.out.println("Success");
        }
        userRepository.saveAndFlush(user);
        return new UserDTO(user);
    }

    @Override
    public void verify(String token) {
        User user = userRepository.findByVerificationToken(token);
        if(user == null) {
            throw new ShoppifyException(HttpStatus.BAD_REQUEST, "Wrong token!");
        }
        user.setVerificationToken(null);
        user.setIsVerified(true);
        userRepository.save(user);
    }

    @Override
    public void changePassword(ChangePasswordDTO changePasswordDTO, String email) {
        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new ShoppifyException(HttpStatus.NOT_FOUND, "User does not exist!");
        }
        if(!configuration.passwordEncoder().matches(changePasswordDTO.getOldPassword(), user.getPassword())) {
            throw new ShoppifyException(HttpStatus.BAD_REQUEST, "Wrong password!");
        }
        user.setPassword(configuration.passwordEncoder().encode(changePasswordDTO.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        Role role = roleRepository.getOne(RoleEnum.USER.getValue());
        List<User> users = userRepository.findByRole(role);
        return users.stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public void banUser(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ShoppifyException(HttpStatus.NOT_FOUND, "Wrong id!"));
        user.setIsActive(false);
        userRepository.save(user);
    }

    @Override
    public void unbanUser(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ShoppifyException(HttpStatus.NOT_FOUND, "Wrong id!"));
        user.setIsActive(true);
        userRepository.save(user);
    }

    @Override
    public UserDTO editProfile(UserDTO userDTO, String email) {
        User loggedInUser = userRepository.findByEmail(email);
        if(loggedInUser.getRole().getId() != RoleEnum.ADMIN.getValue() && !(loggedInUser.getRole().getId() == RoleEnum.USER.getValue() && loggedInUser.getId() == userDTO.getId())) {
            throw new ShoppifyException(HttpStatus.FORBIDDEN, "Can not edit someone others profile!");
        }
        if(loggedInUser.getId() != userDTO.getId()) {
            loggedInUser = userRepository.findById(userDTO.getId()).orElseThrow(() -> new ShoppifyException(HttpStatus.NOT_FOUND, "Wrong id!"));
        }
        loggedInUser.setFirstName(userDTO.getFirstName());
        loggedInUser.setLastName(userDTO.getLastName());
        loggedInUser.setCountry(userDTO.getCountry());
        loggedInUser.setCity(userDTO.getCity());
        loggedInUser.setAddress(userDTO.getAddress());
        loggedInUser.setPhoneNumber(userDTO.getPhoneNumber());
        loggedInUser.setPostalCode(userDTO.getPostalCode());
        userRepository.saveAndFlush(loggedInUser);
        return new UserDTO(loggedInUser);
    }

    @Override
    public UserDTO getById(int id, String email) {
        User loggedInUser = userRepository.findByEmail(email);
        if(loggedInUser.getRole().getId() != RoleEnum.ADMIN.getValue() && !(loggedInUser.getRole().getId() == RoleEnum.USER.getValue() && loggedInUser.getId() == id)) {
            throw new ShoppifyException(HttpStatus.FORBIDDEN, "Can not view someone others profile!");
        }
        if(loggedInUser.getId() != id) {
            loggedInUser = userRepository.findById(id).orElseThrow(() -> new ShoppifyException(HttpStatus.NOT_FOUND, "Wrong id!"));
        }
        return new UserDTO(loggedInUser);
    }
}
