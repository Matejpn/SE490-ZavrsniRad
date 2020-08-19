package com.shop.shoppify.dto;

import com.shop.shoppify.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private int id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String country;

    private String city;

    private String address;

    private String phoneNumber;

    private String postalCode;

    private boolean isActive;

    private boolean isEmailVerified;

    public UserDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.country = user.getCountry();
        this.city = user.getCity();
        this.address = user.getAddress();
        this.phoneNumber = user.getPhoneNumber();
        this.postalCode = user.getPostalCode();
        this.isActive = user.getIsActive();
        this.isEmailVerified = user.getIsVerified();
    }
}
