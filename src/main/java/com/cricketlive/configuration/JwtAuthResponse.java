package com.cricketlive.configuration;

import com.cricketlive.payload.AdminDto;

import lombok.Data;

@Data
public class JwtAuthResponse {
	private String token;
	private AdminDto user;
}
