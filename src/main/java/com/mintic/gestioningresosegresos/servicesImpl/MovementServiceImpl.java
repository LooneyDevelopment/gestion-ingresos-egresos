package com.mintic.gestioningresosegresos.servicesImpl;

import com.mintic.gestioningresosegresos.repositories.MovementRepository;
import com.mintic.gestioningresosegresos.models.entities.Movement;
import com.mintic.gestioningresosegresos.services.IMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MovementServiceImpl implements IMovementService {
    @Autowired
    MovementRepository movementDao;

    @Override
    public List<Movement> findAll() {
        return movementDao.findAll();
    }

    @Override
    public Page<Movement> findAll(Pageable pageable) {
        return movementDao.findAll(pageable);
    }

    @Override
    public Movement findById(Long id) {
        return movementDao.findById(id).orElse(null);
    }

    @Override
    public Movement save(Movement movement) {
        return movementDao.saveAndFlush(movement);
    }

    @Override
    public Movement update(Long id, Movement movement) {
        Movement movementFound = findById(id);
        if (movementFound != null) {
            movementFound.setAmount(movement.getAmount() != null ? movement.getAmount() : movementFound.getAmount());
            movementFound.setConcept(movement.getConcept() != null ? movement.getConcept() : movementFound.getConcept());
            movementFound.setUpdatedAt(new Date());
            return save(movementFound);
        }
        return movementFound;
    }

    @Override
    public boolean delete(Long id) {
        Movement movementFound = findById(id);
        boolean deleted = true;
        if (movementFound != null) {
            movementDao.deleteById(id);
            return deleted;
        }
        return !deleted;
    }
}
