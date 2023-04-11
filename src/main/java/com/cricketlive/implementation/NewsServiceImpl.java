package com.cricketlive.implementation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cricketlive.entity.Admin;
import com.cricketlive.entity.Category;
import com.cricketlive.entity.News;
import com.cricketlive.exception.ResourceNotFoundException;
import com.cricketlive.payload.NewsDto;
import com.cricketlive.repository.AdminRepository;
import com.cricketlive.repository.CategoryRepository;
import com.cricketlive.repository.NewsRepository;
import com.cricketlive.service.NewsService;

@Service
public class NewsServiceImpl implements NewsService {
	@Autowired
    private NewsRepository newsRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Override
	public List<NewsDto> getAllNews() {
		List<News> findAll = this.newsRepository.findAll();
		List<NewsDto> listNewsDto = findAll.stream().map((news)-> this.modelMapper.map(news, NewsDto.class)).collect(Collectors.toList());
		return listNewsDto;
	}

	@Override
	public List<NewsDto> getAllNewsByCategoryId(Long id) {
		Category category = this.categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", "ID", id));
		List<News> findByCategory = this.newsRepository.findByCategory(category);
		List<NewsDto> newsDtos = findByCategory.stream().map((news)-> this.modelMapper.map(news, NewsDto.class)).collect(Collectors.toList());
		return newsDtos;
	}

	@Override
	public NewsDto getSingleNews(Long id) {
		News news = this.newsRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("News", "ID", id));
		return this.modelMapper.map(news, NewsDto.class);
	}

	@Override
	public NewsDto createNews(Long categoryId, NewsDto newsDto,String path,MultipartFile multipartFile,Principal principal) throws IOException {
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "ID", categoryId));
		News news = this.modelMapper.map(newsDto, News.class);
		if(multipartFile.isEmpty()) {
			news.setImageName("default.png");
		}
		else {
			//File Name
			String name = multipartFile.getOriginalFilename();
			//Random id generate name
			String randomID = UUID.randomUUID().toString();
			String fileName = randomID.concat(name.substring(name.lastIndexOf(".")));
			//Full Path
			String filePath = path + File.separator + fileName;
			//Create folder if not created
			File file = new File(path);
			if(!file.exists()) {
				file.mkdir();
			}
			//File Copy
			Files.copy(multipartFile.getInputStream(), Paths.get(filePath));
			//LocalDate date = LocalDate.now();
			//news.setDate(date);
			news.setImageName(fileName);
		}
		String name = principal.getName();
		Admin admin = this.adminRepository.findByEmail(name);
		admin.getNews().add(news);
		news.setAdmin(admin);
		news.setCategory(category);
		News savedNews = this.newsRepository.save(news);
		return this.modelMapper.map(savedNews, NewsDto.class);
	}

	@Override
	public NewsDto updateNewsById(Long id, NewsDto newsDto,String path,MultipartFile multipartFile) throws IOException {
		News news = this.newsRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("News", "ID", id));
		if(!multipartFile.isEmpty()) {
			//Delete File
			File deleteFile = new File(path).getParentFile();
			File file1 = new File(deleteFile, news.getImageName());
			file1.delete();
			//File Name
			String name = multipartFile.getOriginalFilename();
			//Random id generate name
			String randomID = UUID.randomUUID().toString();
			String fileName = randomID.concat(name.substring(name.lastIndexOf(".")));
			//Full Path
			String filePath = path + File.separator + fileName;
			//Create folder if not created
			File file = new File(path);
			if(!file.exists()) {
				file.mkdir();
			}
			//File Copy
			Files.copy(multipartFile.getInputStream(), Paths.get(filePath));
			news.setImageName(fileName);
		}
		else {
		    news.setImageName(newsDto.getImageName());
		}
		news.setTitle(newsDto.getTitle());
		news.setContent(newsDto.getContent());
		news.setAuthor(newsDto.getAuthor());
		//LocalDate date = LocalDate.now();
		news.setDate(newsDto.getDate());
		News updatedNews = this.newsRepository.save(news);
		return this.modelMapper.map(updatedNews, NewsDto.class);
	}

	@Override
	public void deleteNewsById(Long id,String path,MultipartFile multipartFile) throws IOException {
		News news = this.newsRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("News", "ID", id));
		String name = multipartFile.getOriginalFilename();
		String randomID = UUID.randomUUID().toString();
		String fileName = randomID.concat(name.substring(name.lastIndexOf(".")));
		String filePath = path + File.separator + fileName;
		File file = new File(filePath);
		file.delete();
		this.newsRepository.delete(news);
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullPath = path + File.separator + fileName;
		InputStream inputStream = new FileInputStream(fullPath);
		return inputStream;
	}

//	@Override
//	public NewsDto getTypeChangeById(Long id, NewsDto newsDto) {
//		News news = this.newsRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("News", "ID", id));
//		if(news.isType() != false) {
//			news.setType(newsDto.isType());
//			this.newsRepository.save(news);
//		}
//		else {
//			news.setType(newsDto.isType());
//			this.newsRepository.save(news);
//		}
//		return this.modelMapper.map(news, NewsDto.class);
//	}

}
