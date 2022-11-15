package com.mintic.gestioningresosegresos.services;

import com.mintic.gestioningresosegresos.models.entities.Enterprise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IEnterpriseService {

    List<Enterprise> findAll();

    Page<Enterprise> findAll(Pageable pageable);

    Enterprise findById(Long id);

    Enterprise findByDocument(String document);

    Enterprise save(Enterprise enterprise);

    Enterprise update(Long id, Enterprise enterprise);

    boolean delete(Long id);
}
