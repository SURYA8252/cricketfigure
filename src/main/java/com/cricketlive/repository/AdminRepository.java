package com.cricketlive.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cricketlive.entity.Admin;

@Transactional
public interface AdminRepository extends JpaRepository<Admin, Integer>{
    public Admin findByEmail(String email);
}
