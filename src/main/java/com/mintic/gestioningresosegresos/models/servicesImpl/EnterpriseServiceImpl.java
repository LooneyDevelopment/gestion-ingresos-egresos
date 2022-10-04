package com.mintic.gestioningresosegresos.models.servicesImpl;

import com.mintic.gestioningresosegresos.models.daos.IEnterpriseDao;
import com.mintic.gestioningresosegresos.models.entities.Enterprise;
import com.mintic.gestioningresosegresos.models.services.IEnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EnterpriseServiceImpl implements IEnterpriseService {
    @Autowired
    IEnterpriseDao enterpriseDao;

    @Override
    public List<Enterprise> findAll() {
        return enterpriseDao.findAll();
    }

    @Override
    public Page<Enterprise> findAll(Pageable pageable) {
        return enterpriseDao.findAll(pageable);
    }

    @Override
    public Enterprise findById(Long id) {
        return enterpriseDao.findById(id).orElse(null);
    }

    @Override
    public Enterprise findByDocument(String document) {
        return enterpriseDao.findByDocument(document);
    }

    @Override
    public Enterprise save(Enterprise enterprise) {
        return enterpriseDao.save(enterprise);
    }

    @Override
    public Enterprise update(Long id, Enterprise enterprise) {
        Enterprise enterpriseFound = findById(id);
        if (enterpriseFound != null) {
            enterpriseFound.setDocument(enterprise.getDocument() != null ? enterprise.getDocument() : enterpriseFound.getDocument());
            enterpriseFound.setAddress(enterprise.getAddress() != null ? enterprise.getAddress() : enterpriseFound.getAddress());
            enterpriseFound.setName(enterprise.getName() != null ? enterprise.getName() : enterpriseFound.getName());
            enterpriseFound.setPhone(enterprise.getPhone() != null ? enterprise.getPhone() : enterpriseFound.getPhone());
            enterpriseFound.setUpdatedAt(new Date());
            return save(enterpriseFound);
        }
        return enterpriseFound;
    }

    @Override
    public boolean delete(Long id) {
        Enterprise enterpriseFound = findById(id);
        boolean deleted = true;
        if (enterpriseFound != null) {
             enterpriseDao.deleteById(id);
             return deleted;
        }
        return !deleted;
    }
}
