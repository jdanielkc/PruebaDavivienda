package com.co.davivienda.ti.prueba.models.response;

import java.util.List;

import com.co.davivienda.ti.prueba.models.dto.CategoryDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * AllCategoriesResponse is a Data Transfer Object (DTO) used for returning a list of all categories.
 * It extends the BaseResponse class and contains a list of CategoryDTO objects.
 * This class is annotated with Lombok annotations to generate boilerplate code like getters, setters, and constructors.
 * 
 * @author Jose Daniel Garcia Arias
 * @version 1.0.0
 * @since 2025/04/03
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AllCategoriesResponse extends BaseResponse {
    private List<CategoryDTO> categories;
}