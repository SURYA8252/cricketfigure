package com.cricketlive.service;

import java.security.Principal;
import java.util.List;

import com.cricketlive.payload.CategoryDto;

public interface CategoryService {
    //Get All Category
	List<CategoryDto> getAllCategory();
    //Get Single Category
	CategoryDto getSingleCategory(Long id);
	//Save Category
	CategoryDto createCategory(CategoryDto categoryDto,Principal principal);
	//Update Category
	CategoryDto updateCategory(Long id,CategoryDto categoryDto,Principal principal);
	//Delete Category
	void deleteCategory(Long id,Principal principal);
	//Get All List to login user
	List<CategoryDto> getAllCategoriesToLoginUser(Principal principal);
}
  