package com.urbandelivery.promociones.service;

import com.urbandelivery.promociones.entity.Promocion;
import com.urbandelivery.promociones.repository.PromocionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PromocionServiceTest {

    @Mock
    private PromocionRepository promocionRepository;

    @InjectMocks
    private PromocionService promocionService;

    private Promocion promocion;

    @BeforeEach
    void setUp() {
        promocion = new Promocion();
        promocion.setId(1L);
        promocion.setNombre("Promo Estudiante");
        promocion.setPorcentajeDescuento(15.0);
        promocion.setActiva(true);
    }

    @Test
    void testFindAll() {
        when(promocionRepository.findAll()).thenReturn(Arrays.asList(promocion));
        List<Promocion> resultado = promocionService.findAll();
        assertEquals(1, resultado.size());
    }

    @Test
    void testUpdate_Exitoso() {
        Promocion nuevosDatos = new Promocion();
        nuevosDatos.setPorcentajeDescuento(50.0);
        
        when(promocionRepository.findById(1L)).thenReturn(Optional.of(promocion));
        when(promocionRepository.save(any(Promocion.class))).thenReturn(promocion);

        Promocion resultado = promocionService.update(1L, nuevosDatos);
        
        assertEquals(50.0, resultado.getPorcentajeDescuento());
        verify(promocionRepository).save(any(Promocion.class));
    }

    @Test
    void testDelete_Exitoso() {
        when(promocionRepository.existsById(1L)).thenReturn(true);
        doNothing().when(promocionRepository).deleteById(1L);

        assertDoesNotThrow(() -> promocionService.deleteById(1L));
        verify(promocionRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDelete_FalloPorNoExistir() {
        when(promocionRepository.existsById(99L)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            promocionService.deleteById(99L);
        });

        assertEquals("Promocion no encontrada con id: 99", exception.getMessage());
    }
}