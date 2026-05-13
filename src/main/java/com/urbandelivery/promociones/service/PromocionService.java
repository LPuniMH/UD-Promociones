package com.urbandelivery.promociones.service;

import com.urbandelivery.promociones.entity.Promocion;
import com.urbandelivery.promociones.repository.PromocionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PromocionService {
    
    private final PromocionRepository promocionRepository;
    
    public List<Promocion> findAll() {
        return promocionRepository.findAll();
    }
    
    public Optional<Promocion> findById(Long id) {
        return promocionRepository.findById(id);
    }
    
    public List<Promocion> findByActiva(Boolean activa) {
        return promocionRepository.findByActiva(activa);
    }
    
    public Promocion save(Promocion promocion) {
        return promocionRepository.save(promocion);
    }
    
    public Promocion update(Long id, Promocion promocionDetails) {
        return promocionRepository.findById(id)
            .map(promocion -> {
                promocion.setNombre(promocionDetails.getNombre());
                promocion.setPorcentajeDescuento(promocionDetails.getPorcentajeDescuento());
                promocion.setActiva(promocionDetails.getActiva());
                return promocionRepository.save(promocion);
            })
            .orElseThrow(() -> new RuntimeException("Promocion no encontrada con id: " + id));
    }
    
    public void deleteById(Long id) {
        if (!promocionRepository.existsById(id)) {
            throw new RuntimeException("Promocion no encontrada con id: " + id);
        }
        promocionRepository.deleteById(id);
    }
}