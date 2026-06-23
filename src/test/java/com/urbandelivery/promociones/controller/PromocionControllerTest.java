package com.urbandelivery.promociones.controller;

import com.urbandelivery.promociones.entity.Promocion;
import com.urbandelivery.promociones.service.PromocionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.EntityModel;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PromocionControllerTest {

    @Mock
    private PromocionService promocionService;

    @InjectMocks
    private PromocionController promocionController;

    private Promocion promocion;

    @BeforeEach
    void setUp() {
        promocion = new Promocion();
        promocion.setId(1L);
        promocion.setNombre("Descuento Navidad");
        promocion.setPorcentajeDescuento(20.0);
        promocion.setActiva(true);
    }

    @Test
    void testGetAllPromociones() {
        List<Promocion> promociones = Arrays.asList(promocion);
        when(promocionService.findAll()).thenReturn(promociones);

        List<EntityModel<Promocion>> result = promocionController.getAllPromociones();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Descuento Navidad", result.get(0).getContent().getNombre());
        verify(promocionService, times(1)).findAll();
    }

    @Test
    void testGetPromocionById_Existe() {
        when(promocionService.findById(1L)).thenReturn(Optional.of(promocion));

        EntityModel<Promocion> result = promocionController.getPromocionById(1L);

        assertNotNull(result);
        assertEquals(20.0, result.getContent().getPorcentajeDescuento());
        verify(promocionService, times(1)).findById(1L);
    }

    @Test
    void testGetPromocionById_NoExiste() {
        when(promocionService.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            promocionController.getPromocionById(99L);
        });
        verify(promocionService, times(1)).findById(99L);
    }

    @Test
    void testCreatePromocion() {
        when(promocionService.save(any(Promocion.class))).thenReturn(promocion);

        EntityModel<Promocion> result = promocionController.createPromocion(promocion);

        assertNotNull(result);
        assertEquals("Descuento Navidad", result.getContent().getNombre());
        verify(promocionService, times(1)).save(promocion);
    }

    @Test
    void testUpdatePromocion() {
        when(promocionService.update(eq(1L), any(Promocion.class))).thenReturn(promocion);

        EntityModel<Promocion> result = promocionController.updatePromocion(1L, promocion);

        assertNotNull(result);
        assertEquals("Descuento Navidad", result.getContent().getNombre());
        verify(promocionService, times(1)).update(1L, promocion);
    }

    @Test
    void testDeletePromocion() {
        doNothing().when(promocionService).deleteById(1L);

        promocionController.deletePromocion(1L);

        verify(promocionService, times(1)).deleteById(1L);
    }
}