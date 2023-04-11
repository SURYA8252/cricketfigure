package com.cricketlive.payload;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private Long id;
    private String title;
    private String description;
    @NotFound(action = NotFoundAction.IGNORE)
    private AdminDto admin;
}
