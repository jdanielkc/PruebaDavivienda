package com.co.davivienda.ti.prueba.services;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.OK;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.co.davivienda.ti.prueba.entities.User;
import com.co.davivienda.ti.prueba.exceptions.InvalidRequestException;
import com.co.davivienda.ti.prueba.models.request.UserLoginRequest;
import com.co.davivienda.ti.prueba.models.request.UserRegisterRequest;
import com.co.davivienda.ti.prueba.models.response.UserLoginResponse;
import com.co.davivienda.ti.prueba.models.response.UserRegisterResponse;
import com.co.davivienda.ti.prueba.repositories.UserRepository;
import com.co.davivienda.ti.prueba.security.sevices.JwtService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * UserService is a service class that handles user registration and validation. It interacts with
 * the UserRepository to perform database operations. It also uses PasswordEncoder to encode user
 * passwords before saving them.
 * 
 * @author Jose Daniel Garcia Arias
 * @version 1.0.0
 * @since 2025/04/03
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserService implements IUserService {

    private static final String USER_EXISTS_MESSAGE =
            "El usuario con este correo ya está registrado";
    private static final String USER_NO_EXISTS_MESSAGE =
            "El usuario con este NO correo ya está registrado";
    private static final String USER_CREATED_MESSAGE = "Usuario registrado exitosamente";
    private static final String INVALID_EMAIL_FORMAT =
            "El formato del correo electrónico no es válido";
    private static final String FIELDS_REQUIRED = "Todos los campos son requeridos";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    /**
     * Registers a new user in the system.
     * 
     * @param request The UserRegisterRequest object containing user details.
     * @return UserRegisterResponse indicating success or failure of the registration.
     */
    @Transactional
    public ResponseEntity<UserRegisterResponse> registerUser(UserRegisterRequest request) {
        try {
            validateUserRequest(request);

            if (isUserExists(request.getEmail())) {
                return createErrorResponse(USER_EXISTS_MESSAGE);
            }

            User newUser = createUserFromRequest(request);
            userRepository.save(newUser);

            log.info("Usuario registrado correctamente: {}", request.getEmail());

            return ResponseEntity.status(OK).body(UserRegisterResponse.builder().success(true)
                    .showMessage(true).message(USER_CREATED_MESSAGE).build());

        } catch (InvalidRequestException e) {
            log.warn("Error en solicitud de registro: {}", e.getMessage());
            return createErrorResponse(e.getMessage());
        } catch (Exception e) {
            log.error("Error inesperado al registrar usuario", e);
            return createErrorResponse("Error al procesar la solicitud. Intente nuevamente.");
        }
    }

    /**
     * Validates the user registration request.
     * 
     * @param request The UserRegisterRequest object to validate.
     * @throws InvalidRequestException if the request is invalid.
     */
    private void validateUserRequest(UserRegisterRequest request) throws InvalidRequestException {

        if (request == null || !StringUtils.hasText(request.getFirstName())
                || !StringUtils.hasText(request.getLastName())
                || !StringUtils.hasText(request.getEmail())
                || !StringUtils.hasText(request.getPassword())
                || !StringUtils.hasText(request.getConfirmPassword())) {
            throw new InvalidRequestException(FIELDS_REQUIRED);
        }

        if (!request.getEmail().matches(EMAIL_REGEX)) {
            throw new InvalidRequestException(INVALID_EMAIL_FORMAT);
        }

    }

    /**
     * Creates a new User entity from the registration request.
     * 
     * @param request The UserRegisterRequest object containing user details.
     * @return A new User entity.
     */
    private User createUserFromRequest(UserRegisterRequest request) {
        LocalDate now = LocalDate.now();
        return User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .createDate(now).updateDate(now).build();
    }

    /**
     * Creates an error response for user registration.
     * 
     * @param message The error message to include in the response.
     * @return A ResponseEntity containing UserRegisterResponse object indicating failure.
     */
    private ResponseEntity<UserRegisterResponse> createErrorResponse(String message) {
        return ResponseEntity.status(CONFLICT).body(UserRegisterResponse.builder().success(false)
                .showMessage(true).message(message).build());
    }

    /**
     * Creates an error response for user login.
     * 
     * @param message The error message to include in the response.
     * @return A ResponseEntity containing UserLoginResponse object indicating failure.
     */
    private ResponseEntity<UserLoginResponse> createErrorResponseLogin(String message) {
        return ResponseEntity.status(CONFLICT)
                .body(UserLoginResponse.builder().showMessage(true).message(message).build());
    }

    /**
     * Checks if a user with the given email already exists in the system.
     * 
     * @param email The email address to check.
     * @return true if the user exists, false otherwise.
     */
    private boolean isUserExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<UserLoginResponse> loginUser(UserLoginRequest request) {

        if (!isUserExists(request.getEmail())) {
            return createErrorResponseLogin(USER_NO_EXISTS_MESSAGE);
        }

        try {
            if (request == null || !StringUtils.hasText(request.getEmail())
                    || !StringUtils.hasText(request.getPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(UserLoginResponse.builder().showMessage(true)
                                .message("Email y contraseña son requeridos").build());
            }

            var userOptional = userRepository.findByEmail(request.getEmail());
            if (userOptional.isEmpty() || !passwordEncoder.matches(request.getPassword(),
                    userOptional.get().getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(UserLoginResponse
                        .builder().showMessage(true).message("Credenciales inválidas").build());
            }

            var user = userOptional.get();

            // Generar token JWT
            String token = jwtService.generateToken(user.getEmail());

            // Crear respuesta exitosa
            return ResponseEntity.ok(UserLoginResponse.builder().showMessage(true)
                    .message("Inicio de sesión exitoso").token(token).email(user.getEmail())
                    .firstName(user.getFirstName()).lastName(user.getLastName())
                    .userId(user.getId()).build());

        } catch (Exception e) {
            log.error("Error durante el login del usuario", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(UserLoginResponse.builder().showMessage(true)
                            .message("Error en el servidor. Intente nuevamente más tarde").build());
        }
    }

}
