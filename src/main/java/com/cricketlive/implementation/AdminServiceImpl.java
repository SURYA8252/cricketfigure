package com.cricketlive.implementation;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cricketlive.configuration.AppConstants;
import com.cricketlive.entity.Admin;
import com.cricketlive.entity.Role;
import com.cricketlive.exception.ResourceNotFoundException;
import com.cricketlive.payload.AdminDto;
import com.cricketlive.repository.AdminRepository;
import com.cricketlive.repository.RoleRepository;
import com.cricketlive.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
    private AdminRepository adminRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Override
	public AdminDto createAdmin(AdminDto adminDto) {
		Admin admin = this.modelMapper.map(adminDto, Admin.class);
		Admin savedAdmin = this.adminRepository.save(admin);
		return this.modelMapper.map(savedAdmin, AdminDto.class);
	}
	@Override
	public AdminDto registerNewSubAdmin(AdminDto adminDto) {
		Admin admin = this.modelMapper.map(adminDto, Admin.class);
		admin.setPassword(this.passwordEncoder.encode(adminDto.getPassword()));
		admin.setImage("default.png");
		Role role = this.roleRepository.findById(AppConstants.SUB_ADMIN_USER).get();
		admin.getRoles().add(role);
		Admin savedSubAdmin = this.adminRepository.save(admin);
		return this.modelMapper.map(savedSubAdmin, AdminDto.class);
	}
	@Override
	public AdminDto updateSubAdmin(int id, AdminDto adminDto) {
		Admin admin = this.adminRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Sub_Admin", "ID", id));
		admin.setFirstName(adminDto.getFirstName());
		admin.setLastName(adminDto.getLastName());
		admin.setEmail(adminDto.getEmail());
		admin.setPassword(this.passwordEncoder.encode(adminDto.getPassword()));
		admin.setImage(adminDto.getImage());
		Admin updatedSubAdmin = this.adminRepository.save(admin);
		return this.modelMapper.map(updatedSubAdmin, AdminDto.class);
	}
	@Override
	public List<AdminDto> getAllSubAdmin() {
		List<Admin> findAll = this.adminRepository.findAll();
		List<AdminDto> subAdminDtos = findAll.stream().map((subadmin)->this.modelMapper.map(subadmin, AdminDto.class)).collect(Collectors.toList());
		return subAdminDtos;
	}
	@Override
	public AdminDto getSingleSubAdmin(int id) {
		Admin subAdmin = this.adminRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Sub_Admin", "ID", id));
		AdminDto subAdminDtos = this.modelMapper.map(subAdmin, AdminDto.class);
		return subAdminDtos;
	}
	@Override
	public void deleteSubAdmin(int id) {
		Admin subAdmin = this.adminRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Sub_Admin", "ID", id));
		this.adminRepository.delete(subAdmin);
	}
	@Override
	public AdminDto updateProfile(AdminDto adminDto, Principal principal) {
		Admin admin = this.adminRepository.findByEmail(adminDto.getEmail());
		admin.setFirstName(adminDto.getFirstName());
		admin.setLastName(adminDto.getLastName());
		admin.setPassword(this.passwordEncoder.encode(adminDto.getPassword()));
		Admin updateProfile = this.adminRepository.save(admin);
		return this.modelMapper.map(updateProfile, AdminDto.class);
	}

}
