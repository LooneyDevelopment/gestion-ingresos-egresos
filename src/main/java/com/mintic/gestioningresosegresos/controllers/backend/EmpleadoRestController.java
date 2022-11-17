package com.mintic.gestioningresosegresos.controllers.backend;

import com.mintic.gestioningresosegresos.models.entities.Empleado;
import com.mintic.gestioningresosegresos.services.IEmpleadoService;
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
@RequestMapping("api/empleados")
public class EmpleadoRestController {
    @Autowired
    private IEmpleadoService empleadoService;

    @GetMapping
    public List<Empleado> listAll() {
        return empleadoService.findAll();
    }

    @GetMapping("page/{page}")
    public Page<Empleado> listAll(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return empleadoService.findAll(pageable);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> listById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Empleado empleado = empleadoService.findById(id);
            if (empleado == null) {
                response.put("mensaje", "El empleado con id ".concat(id.toString()).concat(" no existe en base de datos"));
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(empleado, HttpStatus.OK);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en base de datos");
            response.put("error", Objects.requireNonNull(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Empleado empleado) {
        Map<String, Object> response = new HashMap<>();
        try {
            empleado = empleadoService.save(empleado, empleado.getEnterprise().getNit());
            response.put("mensaje", "El empleado ha sido creada con éxito!");
            response.put("empleado", empleado);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en base de datos");
            response.put("error", Objects.requireNonNull(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> update(@RequestBody Empleado empleado, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            empleado = empleadoService.update(id, empleado, empleado.getEnterprise().getNit());
            if (empleado == null) {
                response.put("mensaje", "El empleado que desea editar, con id ".concat(id.toString().concat(" no existe en base de datos!")));
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            response.put("mensaje", "El empleado ha sido actualizado con éxito!");
            response.put("cliente", empleado);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actaulizar empleado en base de datos");
            response.put("error", Objects.requireNonNull(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (!empleadoService.delete(id)) {
                response.put("mensaje", "El empleado que desea eliminar, con id ".concat(id.toString().concat(" no existe en base de datos!")));
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            response.put("mensaje", "El empleado ha sido eliminado con éxito!");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar empleado en base de datos");
            response.put("error", Objects.requireNonNull(e.getMessage()).concat(": ".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
