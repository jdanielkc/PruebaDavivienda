package com.co.davivienda.ti.prueba.services;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import com.co.davivienda.ti.prueba.models.response.AllCategoriesResponse;

public interface ICategoryService {

    @Transactional(readOnly = true)
    public ResponseEntity<AllCategoriesResponse> getAllCategories();
}