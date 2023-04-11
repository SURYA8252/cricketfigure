package com.cricketlive.configuration;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cricketlive.entity.Admin;
import com.cricketlive.entity.Role;


public class CustomAdminDetail implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
	private Admin admin;
    
	public CustomAdminDetail(Admin admin) {
		super();
		this.admin = admin;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = this.admin.getRoles().stream().map((role)-> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.admin.getPassword();
	}

	@Override
	public String getUsername() {
		return this.admin.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	public int getId() {
		return this.admin.getId();
	}
    public String getFirstName() {
    	return this.admin.getFirstName();
    }
    public String getLastName() {
    	return this.admin.getLastName();
    }
    public String getEmail() {
    	return this.admin.getEmail();
    }
    public Set<Role> getRoles() {
    	return this.admin.getRoles();
    }
    public String getImage() {
    	return this.admin.getImage();
    }
}
