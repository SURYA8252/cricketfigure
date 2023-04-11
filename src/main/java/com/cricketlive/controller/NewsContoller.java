package com.cricketlive.controller;

import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cricketlive.payload.ApiResponse;
import com.cricketlive.payload.NewsDto;
import com.cricketlive.service.NewsService;

@RestController
@RequestMapping("/api/")
public class NewsContoller {
	@Value("${project.image}")
	private String path;
	@Autowired
	private NewsService newsService;
	//Get All News
	@GetMapping("/news/")
    public ResponseEntity<List<NewsDto>> getAllNews() {
    	List<NewsDto> allNews = this.newsService.getAllNews();
    	return new ResponseEntity<List<NewsDto>>(allNews,HttpStatus.OK);
    }
	//Get All News By Category Id
	@GetMapping("/category/{id}/news/")
	public ResponseEntity<List<NewsDto>> getAllNewsByCategoryId(@PathVariable Long id) {
		List<NewsDto> allNewsByCategoryId = this.newsService.getAllNewsByCategoryId(id);
		return new ResponseEntity<List<NewsDto>>(allNewsByCategoryId,HttpStatus.OK);
	}
	//Get Single News By Id
	@GetMapping("/news/{id}")
	public ResponseEntity<NewsDto> getSingleNewsById(@PathVariable Long id) {
		NewsDto singleNews = this.newsService.getSingleNews(id);
		return new ResponseEntity<NewsDto>(singleNews,HttpStatus.OK);
	}
	//Create News By Category Id
	@PostMapping("/category/{categoryId}/news/")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> createNewsByCategoryId(@PathVariable Long categoryId,NewsDto newsDto,@RequestParam("image") MultipartFile multipartFile,Principal principal) throws IOException {
		
		if(multipartFile.isEmpty()) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("Please Select Jpeg & PNG File", false),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(!multipartFile.getContentType().equals("image/jpeg") && !multipartFile.getContentType().equals("image/png")) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("Only Jpeg & PNG file supported !!", true),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		try {
			//CategoryDto singleCategory = this.categoryService.getSingleCategory(categoryId);
			//newsDto.setCategory(singleCategory);
			NewsDto createNews = this.newsService.createNews(categoryId, newsDto, path, multipartFile,principal);
			//NewsDto createNews = this.newsService.createNews(newsDto,path,multipartFile);
			return new ResponseEntity<NewsDto>(createNews,HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ApiResponse>(new ApiResponse("This file is not uploaded due to server issue !!", true),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	//Update News By Id
	@PutMapping("/news/{id}")
	public ResponseEntity<NewsDto> updateNewsById(@PathVariable Long id,NewsDto newsDto,@RequestParam("image") MultipartFile multipartFile) throws IOException {
		NewsDto updateNewsById = this.newsService.updateNewsById(id, newsDto, path, multipartFile);
		return new ResponseEntity<NewsDto>(updateNewsById,HttpStatus.OK);
	}
	//Delete News By Id
	@DeleteMapping("/news/{id}")
	public ResponseEntity<ApiResponse> deleteNewsById(@PathVariable Long id,MultipartFile multipartFile) throws IOException {
		this.newsService.deleteNewsById(id, path, multipartFile);
		return new ResponseEntity<ApiResponse>(new ApiResponse("News is deleted sucessfully !!", true),HttpStatus.OK);
	}
	//Image Serve
	@GetMapping(value = "/news/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void imageServe(@PathVariable("imageName") String imageName,HttpServletResponse response) throws IOException {
        InputStream inputStream = this.newsService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(inputStream, response.getOutputStream());
	}
}
