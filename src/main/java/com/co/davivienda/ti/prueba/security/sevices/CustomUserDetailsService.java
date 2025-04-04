package com.co.davivienda.ti.prueba.security.sevices;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.co.davivienda.ti.prueba.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * CustomUserDetailsService is a service that implements UserDetailsService to
 * load user-specific data.
 * It retrieves user information from the UserRepository based on the email
 * provided.
 * This class is annotated with @Service to indicate that it is a Spring service
 * component.
 * 
 * @author Jose Daniel Garcia Arias
 * @version 1.0.0
 * @since 2025/04/03
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Loads user details by email.
     * 
     * @param email the email of the user to be loaded
     * @return UserDetails object containing user information
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return new User(userEntity.getEmail(), userEntity.getPassword(), new ArrayList<>());
    }
}