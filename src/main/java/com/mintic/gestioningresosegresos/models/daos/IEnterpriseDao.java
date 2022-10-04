package com.mintic.gestioningresosegresos.models.daos;

import com.mintic.gestioningresosegresos.models.entities.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEnterpriseDao extends JpaRepository<Enterprise, Long> {
    Enterprise findByDocument(String document);
}
