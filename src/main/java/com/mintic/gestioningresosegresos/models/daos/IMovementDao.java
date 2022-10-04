package com.mintic.gestioningresosegresos.models.daos;

import com.mintic.gestioningresosegresos.models.entities.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMovementDao extends JpaRepository<Movement, Long> {
}
