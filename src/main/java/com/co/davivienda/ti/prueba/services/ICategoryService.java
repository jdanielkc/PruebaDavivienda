package com.co.davivienda.ti.prueba.services;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import com.co.davivienda.ti.prueba.models.dto.CategoryCreateDTO;
import com.co.davivienda.ti.prueba.models.response.AllCategoriesResponse;
import com.co.davivienda.ti.prueba.models.response.CategoryResponse;

public interface ICategoryService {

    @Transactional(readOnly = true)
    public ResponseEntity<AllCategoriesResponse> getAllCategories();
    
    @Transactional
    public ResponseEntity<CategoryResponse> createCategory(CategoryCreateDTO categoryCreateDTO);
}