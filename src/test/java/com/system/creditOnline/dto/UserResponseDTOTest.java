package com.system.creditOnline.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserResponseDTOTest {

    @Test
    public void testUserResponseDTOConstructor() {
        UserResponseDTO userResponseDTO = new UserResponseDTO(1L, 500);
        assertNotNull(userResponseDTO);
        assertEquals(1L, userResponseDTO.getId());
        assertEquals(500, userResponseDTO.getCreditLine());
    }

    @Test
    public void testUserResponseDTOSetters() {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(2L);
        userResponseDTO.setCreditLine(600);
        assertEquals(2L, userResponseDTO.getId());
        assertEquals(600, userResponseDTO.getCreditLine());
    }
}
