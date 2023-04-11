package com.cricketlive.implementation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cricketlive.entity.Video;
import com.cricketlive.exception.ResourceNotFoundException;
import com.cricketlive.payload.VideoDto;
import com.cricketlive.repository.VideoRepository;
import com.cricketlive.service.VideoService;

@Service
public class VideoServiceImpl implements VideoService {
	@Autowired
    private VideoRepository videoRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Override
	public List<VideoDto> getAllVideo() {
		List<Video> findAll = this.videoRepository.findAll();
		List<VideoDto> videoDtos = findAll.stream().map((video)-> this.modelMapper.map(video, VideoDto.class)).collect(Collectors.toList());
		return videoDtos;
	}

	@Override
	public VideoDto getSingleVideo(Long id) {
		Video video = this.videoRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Video", "ID", id));
		return this.modelMapper.map(video, VideoDto.class);
	}

	@Override
	public VideoDto createVideo(VideoDto videoDto,String path, MultipartFile multipartFile) throws IOException {
		Video video = this.modelMapper.map(videoDto, Video.class);
		if(multipartFile.isEmpty()) {
			video.setVideoName("default.mp4");
		}
		else {
			//File Name
			String name = multipartFile.getOriginalFilename();
			String randomID = UUID.randomUUID().toString();
			String fileName = randomID.concat(name.substring(name.lastIndexOf(".")));
			//Full Path
			String filePath=  path + File.separator + fileName;
			//Create Folder if not create
			File file = new File(path);
			if(!file.exists()) {
				file.mkdir();
			}
			//File Copy
			Files.copy(multipartFile.getInputStream(), Paths.get(filePath));
			video.setVideoName(fileName);
		}
		LocalDateTime localDateTime = LocalDateTime.now();
		video.setDate(localDateTime);
		Video savedVideo = this.videoRepository.save(video);
		return this.modelMapper.map(savedVideo, VideoDto.class);
	}

	@Override
	public void deleteById(Long id) {
		Video video = this.videoRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Video", "ID", id));
		this.videoRepository.delete(video);
	}

}
