package com.urbandelivery.promociones.controller;

import com.urbandelivery.promociones.entity.Promocion;
import com.urbandelivery.promociones.service.PromocionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0/promociones")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PromocionController {
    
    private final PromocionService promocionService;
    
    @GetMapping
    public ResponseEntity<List<Promocion>> getAllPromociones() {
        return ResponseEntity.ok(promocionService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Promocion> getPromocionById(@PathVariable Long id) {
        return promocionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/activas/{activa}")
    public ResponseEntity<List<Promocion>> getPromocionesByActiva(@PathVariable Boolean activa) {
        return ResponseEntity.ok(promocionService.findByActiva(activa));
    }
    
    @PostMapping
    public ResponseEntity<Promocion> createPromocion(@RequestBody Promocion promocion) {
        Promocion savedPromocion = promocionService.save(promocion);
        return ResponseEntity.ok(savedPromocion);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Promocion> updatePromocion(@PathVariable Long id, @RequestBody Promocion promocion) {
        try {
            Promocion updatedPromocion = promocionService.update(id, promocion);
            return ResponseEntity.ok(updatedPromocion);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePromocion(@PathVariable Long id) {
        try {
            promocionService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}