package com.cricketlive.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cricketlive.payload.NewsDto;

public interface NewsService {
	//Get All News
    List<NewsDto> getAllNews();
    //Get All news by Category Id
    List<NewsDto> getAllNewsByCategoryId(Long id);
    //Get Single news
    NewsDto getSingleNews(Long id);
    //Save News
    NewsDto createNews(Long categoryId, NewsDto newsDto,String path,MultipartFile multipartFile,Principal principal) throws IOException;
    //Update news
    NewsDto updateNewsById(Long id,NewsDto newsDto,String path,MultipartFile multipartFile) throws IOException;
    //Delete News
    void deleteNewsById(Long id,String path,MultipartFile multipartFile) throws IOException;
    //Get Type Change By Id Image or Video
    //NewsDto getTypeChangeById(Long id,NewsDto newsDto);
    //Image Serve
    InputStream getResource(String path,String fileName) throws FileNotFoundException;
}
