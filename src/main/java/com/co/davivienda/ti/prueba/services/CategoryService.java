package com.co.davivienda.ti.prueba.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.co.davivienda.ti.prueba.entities.Category;
import com.co.davivienda.ti.prueba.models.dto.CategoryDTO;
import com.co.davivienda.ti.prueba.models.response.AllCategoriesResponse;
import com.co.davivienda.ti.prueba.repositories.CategoryRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class CategoryService implements ICategoryService {

    private static final String ERROR_GETTING_CATEGORIES = "Error al obtener categorías";

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<AllCategoriesResponse> getAllCategories() {
        try {
            // Obtener todas las categorías
            List<Category> categories = categoryRepository.findAll();

            // Convertir a DTOs
            List<CategoryDTO> categoryDTOs = categories.stream()
                    .map(this::convertToDTO).toList();

            // Construir respuesta
            return ResponseEntity.ok(AllCategoriesResponse.builder()
                    .categories(categoryDTOs)
                    .showMessage(false)
                    .build());

        } catch (Exception e) {
            log.error(ERROR_GETTING_CATEGORIES, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(AllCategoriesResponse.builder()
                            .showMessage(true)
                            .message(ERROR_GETTING_CATEGORIES)
                            .build());
        }
    }

    /**
     * Convierte una entidad Category a CategoryDTO.
     */
    private CategoryDTO convertToDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}