package com.cricketlive.payload;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewsDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    @JsonFormat(pattern="MM-dd-yyyy")
    private LocalDate date;
    private String imageName;
    private CategoryDto category;
}
