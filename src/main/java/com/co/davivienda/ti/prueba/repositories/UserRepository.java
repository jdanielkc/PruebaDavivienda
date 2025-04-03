package com.co.davivienda.ti.prueba.repositories;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.co.davivienda.ti.prueba.entities.User;

/**
 * Repository interface for User entity.
 * This interface extends JpaRepository to provide CRUD operations for User.
 * It is annotated with @Repository to indicate that it is a Spring Data repository.
 * 
 * @author Jose Daniel Garcia Arias
 * @version 1.0.0
 * @since 2025/04/03
 */
@Repository
public interface UserRepository extends JpaRepository<User, BigInteger> {

}
