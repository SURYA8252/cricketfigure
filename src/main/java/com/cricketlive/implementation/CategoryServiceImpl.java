package com.cricketlive.implementation;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cricketlive.entity.Admin;
import com.cricketlive.entity.Category;
import com.cricketlive.exception.ResourceNotFoundException;
import com.cricketlive.payload.CategoryDto;
import com.cricketlive.repository.AdminRepository;
import com.cricketlive.repository.CategoryRepository;
import com.cricketlive.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
    private CategoryRepository categoryRepository;
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> findAll = this.categoryRepository.findAll();
		List<CategoryDto> categoryDtos = findAll.stream().map((category)-> this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
		return categoryDtos;
	}

	@Override
	public CategoryDto getSingleCategory(Long id) {
		Category category = this.categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", "ID", id));
		return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto,Principal principal) {
		Category category = this.modelMapper.map(categoryDto, Category.class);
		String name = principal.getName();
		Admin admin = this.adminRepository.findByEmail(name);
		admin.getCategories().add(category);
		category.setAdmin(admin);
		Category savedCategory = this.categoryRepository.save(category);
		return this.modelMapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(Long id, CategoryDto categoryDto,Principal principal) {
		Admin admin = this.adminRepository.findByEmail(principal.getName());
		Category category = this.categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", "ID", id));
		//admin.getCategories().remove(category);
		admin.getCategories().add(category);
		category.setTitle(categoryDto.getTitle());
		category.setDescription(categoryDto.getDescription());
		Category updatedCategory = this.categoryRepository.save(category);
		return this.modelMapper.map(updatedCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Long id,Principal principal) {
		Category category = this.categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", "ID", id));
		String name = principal.getName();
		Admin admin = this.adminRepository.findByEmail(name);
		admin.getCategories().remove(category);
		this.categoryRepository.delete(category);
	}

	@Override
	public List<CategoryDto> getAllCategoriesToLoginUser(Principal principal) {
		String name = principal.getName();
		Admin admin = this.adminRepository.findByEmail(name);
		List<Category> categories = admin.getCategories();
		List<CategoryDto> listLoginCategory = categories.stream().map((category)->this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
		//List<Category> findAll = this.categoryRepository.findAll();
		return listLoginCategory;
	}

}
