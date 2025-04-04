package com.co.davivienda.ti.prueba.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.co.davivienda.ti.prueba.entities.Category;
import com.co.davivienda.ti.prueba.models.dto.CategoryCreateDTO;
import com.co.davivienda.ti.prueba.models.dto.CategoryDTO;
import com.co.davivienda.ti.prueba.models.response.AllCategoriesResponse;
import com.co.davivienda.ti.prueba.models.response.CategoryResponse;
import com.co.davivienda.ti.prueba.repositories.CategoryRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * CategoryService is a service class that handles category-related operations. It provides methods
 * to retrieve all categories and create a new category. This class is annotated with @Service to
 * indicate that it is a Spring service component. It uses the CategoryRepository to interact with
 * the database.
 * 
 * @author Jose Daniel Garcia Arias
 * @version 1.0.0
 * @since 2025/04/03
 */
@Slf4j
@Service
@AllArgsConstructor
public class CategoryService implements ICategoryService {

    private static final String ERROR_GETTING_CATEGORIES = "Error al obtener categorías";

    private final CategoryRepository categoryRepository;

    /**
     * Retrieves all categories from the database and converts them to DTOs.
     * 
     * @return a ResponseEntity containing the list of all categories
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<AllCategoriesResponse> getAllCategories() {
        try {
            // Obtain all categories from the repository
            List<Category> categories = categoryRepository.findAll();

            // Convert categories to DTOs
            List<CategoryDTO> categoryDTOs = categories.stream().map(this::convertToDTO).toList();

            // Build response
            return ResponseEntity.ok(AllCategoriesResponse.builder().categories(categoryDTOs)
                    .showMessage(false).build());

        } catch (Exception e) {
            log.error(ERROR_GETTING_CATEGORIES, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(AllCategoriesResponse.builder().showMessage(true)
                            .message(ERROR_GETTING_CATEGORIES).build());
        }
    }

    /**
     * Converts a Category entity to a CategoryDTO.
     * 
     * @param category the Category entity to convert
     * @return the converted CategoryDTO
     */
    private CategoryDTO convertToDTO(Category category) {
        return CategoryDTO.builder().id(category.getId()).name(category.getName())
                .description(category.getDescription()).build();
    }

    /**
     * Creates a new category based on the provided CategoryCreateDTO.
     * 
     * @param categoryCreateDTO the DTO containing category creation data
     * @return a ResponseEntity containing the created category or an error message
     */
    @Override
    @Transactional
    public ResponseEntity<CategoryResponse> createCategory(CategoryCreateDTO categoryCreateDTO) {
        try {
            if (categoryCreateDTO.getName() == null
                    || categoryCreateDTO.getName().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(CategoryResponse.builder().showMessage(true)
                                .message("El nombre de la categoría es obligatorio").build());
            }

            Category newCategory = Category.builder().name(categoryCreateDTO.getName())
                    .description(categoryCreateDTO.getDescription()).build();

            Category savedCategory = categoryRepository.save(newCategory);
            CategoryDTO categoryDTO = convertToDTO(savedCategory);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(CategoryResponse.builder().category(categoryDTO).showMessage(true)
                            .message("Categoría creada exitosamente").build());

        } catch (Exception e) {
            log.error("Error al crear la categoría", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(CategoryResponse
                    .builder().showMessage(true).message("Error al crear la categoría").build());
        }
    }
}
