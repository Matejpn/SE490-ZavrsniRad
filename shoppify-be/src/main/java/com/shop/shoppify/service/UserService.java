package com.shop.shoppify.service;

import com.shop.shoppify.dto.ChangePasswordDTO;
import com.shop.shoppify.dto.UserDTO;
import com.shop.shoppify.model.User;

import java.util.List;

public interface UserService {
    User findByEmail(String email);

    UserDTO register(UserDTO userDto);

    void verify(String token);

    void changePassword(ChangePasswordDTO changePasswordDTO, String email);

    List<UserDTO> findAllUsers();

    void banUser(int id);

    void unbanUser(int id);

    UserDTO editProfile(UserDTO userDTO, String email);

    UserDTO getById(int id, String email);
}
