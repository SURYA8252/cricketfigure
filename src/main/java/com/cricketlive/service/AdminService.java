package com.cricketlive.service;

import java.security.Principal;
import java.util.List;

import com.cricketlive.payload.AdminDto;

public interface AdminService {
    AdminDto createAdmin(AdminDto adminDto);
    //Create SubAdmin
  	AdminDto registerNewSubAdmin(AdminDto adminDto);
  	//Update Sub_Admin
  	AdminDto updateSubAdmin(int id,AdminDto adminDto);
  	//Get AllSubAdmin
  	List<AdminDto> getAllSubAdmin();
  	//Get Single SubAdmin
  	AdminDto getSingleSubAdmin(int id);
  	//Delete SubAdmin
  	void deleteSubAdmin(int id);
  	//Update Profile
  	AdminDto updateProfile(AdminDto adminDto,Principal principal);
}
