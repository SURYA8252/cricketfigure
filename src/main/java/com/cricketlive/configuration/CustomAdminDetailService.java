package com.cricketlive.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cricketlive.entity.Admin;
import com.cricketlive.repository.AdminRepository;

@Service
public class CustomAdminDetailService implements UserDetailsService{
	@Autowired
    private AdminRepository adminRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Admin admin = adminRepository.findByEmail(username);
		if(admin == null) {
			throw new UsernameNotFoundException("User not Found !!");
		}
		return new CustomAdminDetail(admin);
	}

}
