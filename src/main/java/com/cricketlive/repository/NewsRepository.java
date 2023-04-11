package com.cricketlive.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cricketlive.entity.Category;
import com.cricketlive.entity.News;

public interface NewsRepository extends JpaRepository<News, Long>{
	//Custom method to get all category
    List<News> findByCategory(Category category);
}
