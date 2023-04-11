package com.cricketlive.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_category")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    private Admin admin;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "category")
    private List<News> news = new ArrayList<News>();
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "category")
    private List<Video> videos = new ArrayList<Video>();
}
