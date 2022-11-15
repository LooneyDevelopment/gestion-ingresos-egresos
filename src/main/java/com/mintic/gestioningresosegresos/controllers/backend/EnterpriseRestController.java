package com.mintic.gestioningresosegresos.controllers.backend;

import com.mintic.gestioningresosegresos.models.entities.Enterprise;
import com.mintic.gestioningresosegresos.services.IEnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("api/enterprises")
public class EnterpriseRestController {
    @Autowired
    private IEnterpriseService enterpriseService;

    @GetMapping
    public List<Enterprise> listAll() {
        return enterpriseService.findAll();
    }

    @GetMapping("page/{page}")
    public Page<Enterprise> listAll(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return enterpriseService.findAll(pageable);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> listById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Enterprise enterprise = enterpriseService.findById(id);
            if (enterprise == null) {
                response.put("mensaje", "La empresa con id ".concat(id.toString()).concat(" no existe en base de datos"));
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(enterprise, HttpStatus.OK);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en base de datos");
            response.put("error", Objects.requireNonNull(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Enterprise enterprise) {
        Map<String, Object> response = new HashMap<>();
        try {
            enterprise = enterpriseService.save(enterprise);
            response.put("mensaje", "La empresa ha sido creada con éxito!");
            response.put("empresa", enterprise);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en base de datos");
            response.put("error", Objects.requireNonNull(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> update(@RequestBody Enterprise enterprise, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            enterprise = enterpriseService.update(id, enterprise);
            if (enterprise == null) {
                response.put("mensaje", "La empresa que desea editar, con id ".concat(id.toString().concat(" no existe en base de datos!")));
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            response.put("mensaje", "La empresa ha sido actualizado con éxito!");
            response.put("empresa", enterprise);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actaulizar empresa en base de datos");
            response.put("error", Objects.requireNonNull(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (!enterpriseService.delete(id)) {
                response.put("mensaje", "La empresa que desea eliminar, con id ".concat(id.toString().concat(" no existe en base de datos!")));
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            response.put("mensaje", "La empresa ha sido eliminada con éxito!");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar empresa en base de datos");
            response.put("error", Objects.requireNonNull(e.getMessage()).concat(": ".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
