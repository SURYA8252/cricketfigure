package com.cricketlive.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_video")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Video {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String title;
	@Column(length = 5000)
	private String content;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime date;
    private String videoName;
    @ManyToOne
    private Category category;
}
