package com.mintic.gestioningresosegresos.repositories;

import com.mintic.gestioningresosegresos.models.entities.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovementRepository extends JpaRepository<Movement, Long> {
}
