package com.mintic.gestioningresosegresos.services;

import com.mintic.gestioningresosegresos.models.entities.Empleado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IEmpleadoService {
    List<Empleado> findAll();

    Page<Empleado> findAll(Pageable pageable);

    Empleado findById(Long id);

    Empleado findByEmail(String email);

    Empleado save(Empleado empleado, String nitEmpresa);

    Empleado update(Long id, Empleado empleado, String nitEmpresa);

    boolean delete(Long id);
}
