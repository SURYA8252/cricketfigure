package com.cricketlive.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cricketlive.entity.Video;

public interface VideoRepository extends JpaRepository<Video, Long>{

}
