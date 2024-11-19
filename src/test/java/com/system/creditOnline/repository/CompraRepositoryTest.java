package com.system.creditOnline.repository;

import com.system.creditOnline.entity.Compra;
import com.system.creditOnline.entity.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class CompraRepositoryTest {

    @Mock
    private CompraRepository compraRepository;

    @Test
    public void testFindByUserId() {
        User user = new User(1L, "John Doe", LocalDate.of(1990, 1, 1), 500);
        Compra compra = new Compra(1L, user, BigDecimal.valueOf(1000), BigDecimal.valueOf(100),
                BigDecimal.valueOf(1100), LocalDate.now(), 5, "Semanal", BigDecimal.valueOf(220),
                LocalDate.of(2024, 12, 15), null);

        when(compraRepository.findByUserId(1L)).thenReturn(List.of(compra));

        List<Compra> compras = compraRepository.findByUserId(1L);
        assertNotNull(compras);
        assertEquals(1, compras.size());
        assertEquals(1L, compras.get(0).getId());
    }
}
