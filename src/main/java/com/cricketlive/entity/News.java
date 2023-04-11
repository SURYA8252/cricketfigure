package com.cricketlive.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_news")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class News {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(length = 10000)
    private String content;
    private String author;
    private LocalDate date;
    private String imageName;
    @ManyToOne
    private Category category;
    @ManyToOne
    private Admin admin;
}
