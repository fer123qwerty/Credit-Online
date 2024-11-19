package com.system.creditOnline.controller;

import com.system.creditOnline.dto.GenericResponse;
import com.system.creditOnline.dto.NotificationResource;
import com.system.creditOnline.dto.UserResponseDTO;
import com.system.creditOnline.entity.User;
import com.system.creditOnline.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/register")
    public ResponseEntity<GenericResponse<UserResponseDTO>> registerUser(@RequestBody User user) {
        log.info("Registrando usuario con ID: {}", user.getId());

        try {
            User savedUser = userService.registerUser(user);

            UserResponseDTO responseData = UserResponseDTO.builder()
                    .id(savedUser.getId())
                    .creditLine(savedUser.getCreditLine())
                    .build();

            GenericResponse<UserResponseDTO> response = GenericResponse.<UserResponseDTO>builder()
                    .data(responseData)
                    .notifications(NotificationResource.builder()
                            .code(HttpStatus.OK.toString())
                            .message("Usuario registrado exitosamente.")
                            .timestamp(OffsetDateTime.now())
                            .build())
                    .build();

            log.info("Usuario registrado exitosamente con ID: {}", savedUser.getId());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            log.error("Error al registrar usuario con ID: {}", user.getId(), e);

            GenericResponse<UserResponseDTO> response = GenericResponse.<UserResponseDTO>builder()
                    .notifications(NotificationResource.builder()
                            .code(HttpStatus.BAD_REQUEST.toString())
                            .message(e.getMessage())
                            .timestamp(OffsetDateTime.now())
                            .build())
                    .build();

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

}


