package com.cricketlive.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cricketlive.payload.ApiResponse;
import com.cricketlive.payload.CategoryDto;
import com.cricketlive.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryContoller {
	@Autowired
	private CategoryService categoryService;
	//Get All Category
	@GetMapping("/")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<CategoryDto>> getAllCategory() {
    	List<CategoryDto> allCategory = this.categoryService.getAllCategory();
    	return new ResponseEntity<List<CategoryDto>>(allCategory,HttpStatus.OK);
    }
	//Get Single Category
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable Long id) {
		CategoryDto singleCategory = this.categoryService.getSingleCategory(id);
		return new ResponseEntity<CategoryDto>(singleCategory,HttpStatus.OK);
	}
	//Save Category
	@PostMapping("/")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SUB_ADMIN')")
	public ResponseEntity<CategoryDto> saveCategory(@RequestBody CategoryDto categoryDto,Principal principal) {
		CategoryDto createCategory = this.categoryService.createCategory(categoryDto,principal);
		return new ResponseEntity<CategoryDto>(createCategory,HttpStatus.CREATED);
	}
	//Update Category
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SUB_ADMIN')")
	public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id,@RequestBody CategoryDto categoryDto,Principal principal) {
		CategoryDto updateCategory = this.categoryService.updateCategory(id, categoryDto,principal);
		return new ResponseEntity<CategoryDto>(updateCategory,HttpStatus.OK);
	}
	//Delete Category
	//@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id,Principal principal) {
		this.categoryService.deleteCategory(id,principal);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Delete is Successfully !!", true), HttpStatus.OK);
	}
	//Login User to all list of Categories
	@GetMapping("/list")
	public ResponseEntity<List<CategoryDto>> getAllCategoriesToLoginUser(Principal principal) {
		List<CategoryDto> allCategoriesToLoginUser = this.categoryService.getAllCategoriesToLoginUser(principal);
		return new ResponseEntity<List<CategoryDto>>(allCategoriesToLoginUser,HttpStatus.OK);
	}
}
