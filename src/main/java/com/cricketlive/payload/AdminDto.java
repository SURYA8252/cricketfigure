package com.cricketlive.payload;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;



import lombok.Data;

@Data
public class AdminDto {
    private int id;
    private String firstName;
    private String lastName;
    @Email(message = "Please valid email ID !!")
    @NotEmpty(message = "Please enter your email ID !!")
    @Column(length = 100,unique = true)
    private String email;
    private String password;
    private String image;
    private Set<RoleDto> roles = new HashSet<RoleDto>();
}
