package com.co.davivienda.ti.prueba.services;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import com.co.davivienda.ti.prueba.models.dto.CategoryCreateDTO;
import com.co.davivienda.ti.prueba.models.response.AllCategoriesResponse;
import com.co.davivienda.ti.prueba.models.response.CategoryResponse;

/**
 * ICategoryService is an interface that defines the contract for category-related operations. It
 * provides methods to retrieve all categories and create a new category. This interface is
 * implemented by the CategoryService class.
 * 
 * @author Jose Daniel Garcia Arias
 * @version 1.0.0
 * @since 2025/04/03
 */
public interface ICategoryService {

    @Transactional(readOnly = true)
    public ResponseEntity<AllCategoriesResponse> getAllCategories();

    @Transactional
    public ResponseEntity<CategoryResponse> createCategory(CategoryCreateDTO categoryCreateDTO);
}
