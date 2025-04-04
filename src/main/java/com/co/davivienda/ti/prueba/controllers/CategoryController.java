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

@RestController
@AllArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final ICategoryService categoryService;
    
    @GetMapping
    public ResponseEntity<AllCategoriesResponse> getAllCategories() {
        return categoryService.getAllCategories();
    }
    
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryCreateDTO categoryCreateDTO) {
        return categoryService.createCategory(categoryCreateDTO);
    }
}