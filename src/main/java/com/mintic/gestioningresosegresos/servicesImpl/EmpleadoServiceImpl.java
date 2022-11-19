package com.mintic.gestioningresosegresos.servicesImpl;

import com.mintic.gestioningresosegresos.models.entities.Enterprise;
import com.mintic.gestioningresosegresos.repositories.EmpleadoRepository;
import com.mintic.gestioningresosegresos.models.entities.Empleado;
import com.mintic.gestioningresosegresos.models.enums.EnumRoleName;
import com.mintic.gestioningresosegresos.services.IEmpleadoService;
import com.mintic.gestioningresosegresos.services.IEnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class EmpleadoServiceImpl implements IEmpleadoService {
    @Autowired
    EmpleadoRepository empleadoDao;

    @Autowired
    IEnterpriseService enterpriseService;

    @Override
    public List<Empleado> findAll() {
        return empleadoDao.findAll();
    }

    @Override
    public Page<Empleado> findAll(Pageable pageable) {
        return empleadoDao.findAll(pageable);
    }

    @Override
    public Empleado findById(Long id) {
        return empleadoDao.findById(id).orElse(null);
    }

    @Override
    public Empleado findByEmail(String email) { return empleadoDao.findByEmail(email); }

    @Override
    public Empleado save(Empleado empleado, String nitEmpresa) {
        empleado.setEnterprise(enterpriseService.findByNit(nitEmpresa));
        empleado.setRoles(empleado.getRoles() != null ? empleado.getRoles() : Collections.singletonList(EnumRoleName.OPERARIO));
        return empleadoDao.saveAndFlush(empleado);
    }

    @Override
    public Empleado update(Long id, Empleado empleado, String nitEmpresa) {
        Empleado empleadoFound = findById(id);
        if (empleadoFound != null) {
            empleadoFound.setName(empleado.getName() != null ? empleado.getName() : empleadoFound.getName());
            empleadoFound.setEmail(empleado.getEmail() != null ? empleado.getEmail() : empleadoFound.getEmail());
            empleadoFound.setRoles(empleado.getRoles() != null ? empleado.getRoles() : empleadoFound.getRoles());
            empleadoFound.setUpdatedAt(new Date());
            return save(empleadoFound, nitEmpresa);
        }
        return empleadoFound;
    }

    @Override
    public boolean delete(Long id) {
        Empleado empleadoFound = findById(id);
        boolean deleted = true;
        if (empleadoFound != null) {
            empleadoDao.deleteById(id);
            return deleted;
        }
        return !deleted;
    }
}
