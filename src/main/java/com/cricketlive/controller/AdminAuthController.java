package com.cricketlive.controller;

import java.security.Principal;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cricketlive.configuration.CustomAdminDetail;
import com.cricketlive.configuration.JwtAuthRequest;
import com.cricketlive.configuration.JwtAuthResponse;
import com.cricketlive.configuration.JwtTokenHelper;
import com.cricketlive.exception.ApiException;
import com.cricketlive.payload.AdminDto;
import com.cricketlive.payload.ApiResponse;
import com.cricketlive.service.AdminService;

@EnableAutoConfiguration
@RestController
@RequestMapping("/api/auth/")
public class AdminAuthController {
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private AdminService adminService;
	@Autowired
	private ModelMapper modelMapper;
	//@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest jwtAuthRequest) throws Exception {
    	this.authenticate(jwtAuthRequest.getEmail(),jwtAuthRequest.getPassword());
    	UserDetails loadUserByUsername = this.userDetailsService.loadUserByUsername(jwtAuthRequest.getEmail());
    	String generateToken = this.jwtTokenHelper.generateToken(loadUserByUsername);
    	JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
    	jwtAuthResponse.setToken(generateToken);
    	jwtAuthResponse.setUser(this.modelMapper.map((CustomAdminDetail)loadUserByUsername, AdminDto.class));
    	return new ResponseEntity<JwtAuthResponse>(jwtAuthResponse,HttpStatus.OK);
    }
	private void authenticate(String email, String password) throws Exception {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email,password);
		try {
			this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		} catch (BadCredentialsException e) {
			System.out.println("Invalid Details !!");
			throw new ApiException("Invalid Username & password !!");
		}
	}
	//return the details of current user
//    @GetMapping("/current-user")
//    public CustomAdminDetail getCurrentUser(Principal principal)
//    {
//    	return (CustomAdminDetail) this.customAdminDetailService.loadUserByUsername(principal.getName());
//    }
	//Create SubAdmin
	@PostMapping("/sub_admin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<AdminDto> createSubAdmin(@RequestBody AdminDto adminDto) {
		AdminDto registerNewSubAdmin = this.adminService.registerNewSubAdmin(adminDto);
		return new ResponseEntity<AdminDto>(registerNewSubAdmin,HttpStatus.CREATED);
	}
	//Update SubAdmin
	@PutMapping("/sub_admin/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<AdminDto> updateSubAdmin(@PathVariable int id,@RequestBody AdminDto adminDto) {
		AdminDto updateSubAdmin = this.adminService.updateSubAdmin(id, adminDto);
		return new ResponseEntity<AdminDto>(updateSubAdmin,HttpStatus.OK);
	}
	//Get All SubAdmin
	@GetMapping("/sub_admin/")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<AdminDto>> getAllSubAdmin() {
		List<AdminDto> allSubAdmin = this.adminService.getAllSubAdmin();
		return new ResponseEntity<List<AdminDto>>(allSubAdmin,HttpStatus.OK);
	}
	//Get Single SubAdmin
	@GetMapping("/sub_admin/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<AdminDto> getSingleSubAdmin(@PathVariable int id) {
		AdminDto singleSubAdmin = this.adminService.getSingleSubAdmin(id);
		return new ResponseEntity<AdminDto>(singleSubAdmin,HttpStatus.OK);
	}
	//Delete SubAdmin
	@DeleteMapping("/sub_admin/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> deleteSubAdmin(@PathVariable int id) {
		this.adminService.deleteSubAdmin(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Sub Admin Deleted Successfully !!", true),HttpStatus.OK);
	}
	//Update Profile
	@PutMapping("/profile")
	public ResponseEntity<AdminDto> updateProfile(@RequestBody AdminDto adminDto,Principal principal) {
		AdminDto updateProfile = this.adminService.updateProfile(adminDto, principal);
		return new ResponseEntity<AdminDto>(updateProfile,HttpStatus.OK);
	}
}
