package com.mintic.gestioningresosegresos.repositories;

import com.mintic.gestioningresosegresos.models.entities.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    Empleado findByEmail(String email);
}
