package com.system.creditOnline.controller;
import com.system.creditOnline.dto.UserResponseDTO;
import com.system.creditOnline.dto.GenericResponse;
import com.system.creditOnline.entity.User;
import com.system.creditOnline.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testRegisterUserSuccess() {
        // Arrange
        User user = new User();
        user.setBirthdate(LocalDate.of(1990, 1, 1));
        user.setId(1L);

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setCreditLine(5000);

        when(userService.registerUser(user)).thenReturn(savedUser);

        // Act
        ResponseEntity<GenericResponse<UserResponseDTO>> response = userController.registerUser(user);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());  // Espera 201 CREATED, no 200 OK
        assertEquals("Usuario registrado exitosamente.", response.getBody().getNotifications().getMessage());
    }

    @Test
    public void testRegisterUserAgeNotValid() {
        // Arrange
        User user = new User();
        user.setBirthdate(LocalDate.of(2020, 1, 1));  // Edad no válida

        when(userService.registerUser(user)).thenThrow(new RuntimeException("Edad no permitida. Debe ser mayor de 18 años y menor de 90 años."));

        // Act
        ResponseEntity<GenericResponse<UserResponseDTO>> response = userController.registerUser(user);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Edad no permitida. Debe ser mayor de 18 años y menor de 90 años.", response.getBody().getNotifications().getMessage());
    }
}
