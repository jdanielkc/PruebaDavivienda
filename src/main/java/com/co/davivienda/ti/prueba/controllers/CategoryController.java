package com.co.davivienda.ti.prueba.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.co.davivienda.ti.prueba.models.dto.CategoryCreateDTO;
import com.co.davivienda.ti.prueba.models.response.AllCategoriesResponse;
import com.co.davivienda.ti.prueba.models.response.CategoryResponse;
import com.co.davivienda.ti.prueba.services.ICategoryService;

import lombok.AllArgsConstructor;

/**
 * CategoryController handles category-related operations such as retrieving all categories and creating a new category.
 * It provides endpoints for category management functionalities.
 *
 * @author Jose Daniel Garcia Arias
 * @version 1.0.0
 * @since 2025/04/03
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final ICategoryService categoryService;
    
    /**
     * Endpoint to retrieve all categories.
     * 
     * @return the response entity containing all categories
     */
    @GetMapping
    public ResponseEntity<AllCategoriesResponse> getAllCategories() {
        return categoryService.getAllCategories();
    }
    
    /**
     * Endpoint to create a new category.
     * 
     * @param categoryCreateDTO the category creation request
     * @return the response entity containing the created category
     */
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryCreateDTO categoryCreateDTO) {
        return categoryService.createCategory(categoryCreateDTO);
    }
}