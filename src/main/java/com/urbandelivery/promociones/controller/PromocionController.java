package com.urbandelivery.promociones.controller;

import com.urbandelivery.promociones.entity.Promocion;
import com.urbandelivery.promociones.service.PromocionService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/v0/promociones")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Promociones", description = "Endpoints para gestionar los descuentos del sistema")
public class PromocionController {
    
    private final PromocionService promocionService;
    
    @GetMapping
    @Operation(summary = "Obtener todas las promociones", description = "Retorna el listado completo de descuentos registrados")
    public List<EntityModel<Promocion>> getAllPromociones() {
        return promocionService.findAll().stream()
                .map(this::addLinks)
                .toList();
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener promoción por ID", description = "Busca un descuento específico")
    public EntityModel<Promocion> getPromocionById(@Parameter(description = "ID de la promoción") @PathVariable Long id) {
        Promocion promocion = promocionService.findById(id)
                .orElseThrow(() -> new RuntimeException("Promocion no encontrada"));
        return addLinks(promocion);
    }
    
    @GetMapping("/activas/{activa}")
    @Operation(summary = "Filtrar promociones activas", description = "Muestra solo las promociones vigentes o vencidas")
    public List<EntityModel<Promocion>> getPromocionesByActiva(@PathVariable Boolean activa) {
        return promocionService.findByActiva(activa).stream()
                .map(this::addLinks)
                .toList();
    }
    
    @PostMapping
    @Operation(summary = "Crear promoción", description = "Registra una nueva regla de descuento")
    public EntityModel<Promocion> createPromocion(@RequestBody Promocion promocion) {
        Promocion savedPromocion = promocionService.save(promocion);
        return addLinks(savedPromocion);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar promoción", description = "Modifica el porcentaje o nombre de un descuento")
    public EntityModel<Promocion> updatePromocion(@PathVariable Long id, @RequestBody Promocion promocion) {
        Promocion updatedPromocion = promocionService.update(id, promocion);
        return addLinks(updatedPromocion);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar promoción", description = "Borra un descuento del sistema")
    public void deletePromocion(@PathVariable Long id) {
        promocionService.deleteById(id);
    }

    private EntityModel<Promocion> addLinks(Promocion promocion) {
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PromocionController.class)
                .getPromocionById(promocion.getId())).withSelfRel();
        Link allLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PromocionController.class)
                .getAllPromociones()).withRel("promociones");
        return EntityModel.of(promocion, selfLink, allLink);
    }
}