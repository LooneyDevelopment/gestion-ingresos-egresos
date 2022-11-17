package com.mintic.gestioningresosegresos.repositories;

import com.mintic.gestioningresosegresos.models.entities.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {
    Enterprise findByNit(String nit);
}
