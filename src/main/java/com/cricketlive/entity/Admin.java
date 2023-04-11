package com.cricketlive.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


import lombok.Data;

@Entity
@Table(name = "tbl_admin")
@Data
public class Admin{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    @Email(message = "Please valid email ID !!")
    @NotEmpty(message = "Please enter your email ID !!")
    @Column(length = 100,unique = true)
    private String email;
    private String password;
    private String image;
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "tbl_user_role",joinColumns = @JoinColumn(name = "admin",referencedColumnName = "id"),inverseJoinColumns = @JoinColumn(name = "role",referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<Role>();
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "admin")
    private List<Category> categories = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "admin")
    private List<News> news = new ArrayList<News>();
}
