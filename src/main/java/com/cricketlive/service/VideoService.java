package com.cricketlive.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cricketlive.payload.VideoDto;

public interface VideoService {
	//Get All Video
    List<VideoDto> getAllVideo();
    //Get Single Video By Id
    VideoDto getSingleVideo(Long id);
    //Save Video By Category ID
    VideoDto createVideo(VideoDto videoDto,String path,MultipartFile multipartFile) throws IOException;
    //Delete Video By Id
    void deleteById(Long id);
}
