package com.shop.shoppify.model;

import com.shop.shoppify.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * The persistent class for the user database table.
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    private String city;

    private String country;

    @Column(name = "postal_code")
    private String postalCode;

    private String address;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "verification_token")
    private String verificationToken;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Purchase> purchases;

    public User(UserDTO userDTO){
        this.lastName = userDTO.getLastName();
        this.firstName = userDTO.getFirstName();
        this.email = userDTO.getEmail();
        this.country = userDTO.getCountry();
        this.city = userDTO.getCity();
        this.postalCode = userDTO.getPostalCode();
        this.address = userDTO.getAddress();
        this.isActive = true;
        this.isVerified = false;
        this.phoneNumber = userDTO.getPhoneNumber();
    }

}