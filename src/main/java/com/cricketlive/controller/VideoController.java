package com.cricketlive.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cricketlive.payload.ApiResponse;
import com.cricketlive.payload.CategoryDto;
import com.cricketlive.payload.VideoDto;
import com.cricketlive.service.CategoryService;
import com.cricketlive.service.VideoService;

@RestController
@RequestMapping("/api/")
public class VideoController {
	@Autowired
	private VideoService videoService;
	@Autowired
	private CategoryService categoryService;
	@Value("${project.video}")
	private String path;
	//Get All Video
	@GetMapping("/video/")
	public ResponseEntity<List<VideoDto>> getAllVideo() {
		List<VideoDto> allVideo = this.videoService.getAllVideo();
		return new ResponseEntity<List<VideoDto>>(allVideo,HttpStatus.OK);
	}
	//Get Video By Id
	@GetMapping("/video/{id}")
	public ResponseEntity<VideoDto> getSingleVideo(@PathVariable Long id) {
		VideoDto singleVideo = this.videoService.getSingleVideo(id);
		return new ResponseEntity<VideoDto>(singleVideo,HttpStatus.OK);
	}
	//Save Video URI
	@PostMapping("/category/{categoryId}/video/")
    public ResponseEntity<?> createVideoSave(VideoDto videoDto,@RequestParam("video") MultipartFile multipartFile,@PathVariable Long categoryId) throws IOException {
        
		if(multipartFile.isEmpty()) {
        	return new ResponseEntity<ApiResponse>(new ApiResponse("Please Select MP4 MP4v MPG4", false),HttpStatus.INTERNAL_SERVER_ERROR);
        }
		if(!multipartFile.getContentType().equals("video/mp4")) {
        	return new ResponseEntity<ApiResponse>(new ApiResponse("This file supported only mp4 mp4v mpg4", true),HttpStatus.INTERNAL_SERVER_ERROR);
        }
		try {
        	CategoryDto singleCategory = this.categoryService.getSingleCategory(categoryId);
            videoDto.setCategory(singleCategory);
    		VideoDto createVideo = this.videoService.createVideo(videoDto, path, multipartFile);
    		return new ResponseEntity<VideoDto>(createVideo,HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ApiResponse>(new ApiResponse("This file is not uploaded due to server issue !!", true),HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	//Delete Video By Id
	@DeleteMapping("/video/{id}")
	public ResponseEntity<ApiResponse> deleteVideoById(@PathVariable Long id) {
		this.videoService.deleteById(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Video Deleted is Successfully !!", true),HttpStatus.OK);
	}
}
