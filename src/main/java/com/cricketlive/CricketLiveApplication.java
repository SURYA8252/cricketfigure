package com.cricketlive;


import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cricketlive.configuration.AppConstants;
import com.cricketlive.entity.Admin;
import com.cricketlive.entity.Role;
import com.cricketlive.repository.AdminRepository;
import com.cricketlive.repository.RoleRepository;

@SpringBootApplication
public class CricketLiveApplication implements CommandLineRunner{
	// extends SpringBootServletInitializer
	@Autowired
    private AdminRepository adminRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepository roleRepository;
	public static void main(String[] args) {
		SpringApplication.run(CricketLiveApplication.class, args);
	}
	@Bean
    public ModelMapper modelMapper() {
    	return new ModelMapper();
 
	}
	@Override
	public void run(String... args) throws Exception {
//		Admin admin = new Admin();
//		admin.setFirstName("Surya");
//		admin.setLastName("Kumar");
//		admin.setEmail("admin@gmail.com");
//		admin.setPassword(this.passwordEncoder.encode("123"));
//		admin.setImage("default.png");
//		this.adminRepository.save(admin);
		try {
			Role role = new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ROLE_ADMIN");
			
			Role role1 = new Role();
			role1.setId(AppConstants.SUB_ADMIN_USER);
			role1.setName("ROLE_SUB_ADMIN");
			
			Role role2 = new Role();
			role2.setId(AppConstants.NORMAL_USER);
			role2.setName("ROLE_NORMAL");
			List<Role> roles = List.of(role,role1,role2);
			List<Role> saveAll = this.roleRepository.saveAll(roles);
			saveAll.forEach(s->{
				System.out.println(s.getName());
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}