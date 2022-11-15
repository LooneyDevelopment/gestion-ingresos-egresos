package com.mintic.gestioningresosegresos.controllers.backend;

import com.mintic.gestioningresosegresos.models.entities.Movement;
import com.mintic.gestioningresosegresos.services.IMovementService;
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
@RequestMapping("api/movements")
public class MovementRestController {
    @Autowired
    private IMovementService movementService;

    @GetMapping
    public List<Movement> listAll() {
        return movementService.findAll();
    }

    @GetMapping("page/{page}")
    public Page<Movement> listAll(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return movementService.findAll(pageable);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> listById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Movement movement = movementService.findById(id);
            if (movement == null) {
                response.put("mensaje", "El movimiento con id ".concat(id.toString()).concat(" no existe en base de datos"));
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(movement, HttpStatus.OK);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en base de datos");
            response.put("error", Objects.requireNonNull(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Movement movement) {
        Map<String, Object> response = new HashMap<>();
        try {
            movement = movementService.save(movement);
            response.put("mensaje", "El movimiento ha sido creada con éxito!");
            response.put("movement", movement);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en base de datos");
            response.put("error", Objects.requireNonNull(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> update(@RequestBody Movement movement, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            movement = movementService.update(id, movement);
            if (movement == null) {
                response.put("mensaje", "El movimiento que desea editar, con id ".concat(id.toString().concat(" no existe en base de datos!")));
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            response.put("mensaje", "El movimiento ha sido actualizado con éxito!");
            response.put("cliente", movement);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actaulizar movimiento en base de datos");
            response.put("error", Objects.requireNonNull(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (!movementService.delete(id)) {
                response.put("mensaje", "El movimiento que desea eliminar, con id ".concat(id.toString().concat(" no existe en base de datos!")));
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            response.put("mensaje", "El movimiento ha sido eliminado con éxito!");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar movimiento en base de datos");
            response.put("error", Objects.requireNonNull(e.getMessage()).concat(": ".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
