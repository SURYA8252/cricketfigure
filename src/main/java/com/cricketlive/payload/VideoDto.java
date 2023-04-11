package com.cricketlive.payload;


import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VideoDto {
    private Long id;
    private String title;
    private String content;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    private String videoName;
    private CategoryDto category;
}
