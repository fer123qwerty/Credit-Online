package com.system.creditOnline.repository;

import com.system.creditOnline.entity.User;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @Test
    public void testFindUserById() {
        User user = new User(1L, "John Doe", LocalDate.of(1990, 1, 1), 500);

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));

        User foundUser = userRepository.findById(1L).orElse(null);
        assertNotNull(foundUser);
        assertEquals(1L, foundUser.getId());
        assertEquals("John Doe", foundUser.getName());
    }
}
