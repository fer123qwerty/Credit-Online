package com.system.creditOnline.service.impl;

import com.system.creditOnline.entity.User;
import com.system.creditOnline.repository.UserRepository;
import com.system.creditOnline.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

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

        when(userRepository.save(user)).thenReturn(user);

        // Act
        User savedUser = userService.registerUser(user);

        // Assert
        assertNotNull(savedUser);
        assertEquals(1L, savedUser.getId());
        assertEquals(8000, savedUser.getCreditLine());
    }

    @Test
    public void testRegisterUserInvalidAge() {
        // Arrange
        User user = new User();
        user.setBirthdate(LocalDate.of(2010, 1, 1));  // Edad no válida

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(user));
    }
}
