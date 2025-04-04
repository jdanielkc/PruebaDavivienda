package com.co.davivienda.ti.prueba.models.response;

import java.util.List;

import com.co.davivienda.ti.prueba.models.dto.CategoryDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AllCategoriesResponse extends BaseResponse {
    private List<CategoryDTO> categories;
}