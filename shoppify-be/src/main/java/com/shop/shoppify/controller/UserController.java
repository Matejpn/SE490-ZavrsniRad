package com.shop.shoppify.controller;

import com.shop.shoppify.dto.ChangePasswordDTO;
import com.shop.shoppify.dto.UserDTO;
import com.shop.shoppify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody  UserDTO userDto) {
        UserDTO user = userService.register(userDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/verify/{token}")
    public ResponseEntity<String> verify(@PathVariable("token") String token) {
        userService.verify(token);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PutMapping("/change-pass")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO, Principal principal) {
        userService.changePassword(changePasswordDTO, principal.getName());
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UserDTO>> findAllUsers() {
        List<UserDTO> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/ban/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> banUser(@PathVariable("id") int id) {
        userService.banUser(id);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PutMapping("/unban/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> unbanUser(@PathVariable("id") int id) {
        userService.unbanUser(id);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PutMapping("/edit")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> editProfile(@RequestBody UserDTO userDTO , Principal principal) {
        UserDTO user = userService.editProfile(userDTO, principal.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") int id, Principal principal) {
        UserDTO user = userService.getById(id, principal.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}