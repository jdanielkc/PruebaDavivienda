package com.co.davivienda.ti.prueba.entities;

import java.math.BigInteger;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Category entity class representing a category in the system.
 * This class is annotated with JPA annotations to map it to the database table "category".
 * It contains fields for category attributes and relationships with other entities.
 * 
 * @author Jose Daniel Garcia Arias
 * @version 1.0.0
 * @since 2025/04/03
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private BigInteger id;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "category")
    private List<Event> events;
}