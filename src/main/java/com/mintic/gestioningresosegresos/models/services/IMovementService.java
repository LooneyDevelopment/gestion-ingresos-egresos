package com.mintic.gestioningresosegresos.models.services;

import com.mintic.gestioningresosegresos.models.entities.Movement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IMovementService {
    List<Movement> findAll();

    Page<Movement> findAll(Pageable pageable);

    Movement findById(Long id);

    Movement save(Movement movement);

    Movement update(Long id, Movement movement);

    boolean delete(Long id);
}
