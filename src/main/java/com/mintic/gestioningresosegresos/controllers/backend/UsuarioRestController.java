package com.mintic.gestioningresosegresos.controllers.backend;

import com.mintic.gestioningresosegresos.models.entities.Usuario;
import com.mintic.gestioningresosegresos.services.IUsuarioService;
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
@RequestMapping("api/usuarios")
public class UsuarioRestController {
    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping
    public List<Usuario> listAll() {
        return usuarioService.findAll();
    }

    @GetMapping("page/{page}")
    public Page<Usuario> listAll(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return usuarioService.findAll(pageable);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> listById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Usuario usuario = usuarioService.findById(id);
            if (usuario == null) {
                response.put("mensaje", "El usuario con id ".concat(id.toString()).concat(" no existe en base de datos"));
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en base de datos");
            response.put("error", Objects.requireNonNull(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Usuario usuario) {
        Map<String, Object> response = new HashMap<>();
        try {
            usuario = usuarioService.save(usuario);
            response.put("mensaje", "El usuario ha sido creada con éxito!");
            response.put("usuario", usuario);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en base de datos");
            response.put("error", Objects.requireNonNull(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> update(@RequestBody Usuario usuario, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            usuario = usuarioService.update(id, usuario);
            if (usuario == null) {
                response.put("mensaje", "El usuario que desea editar, con id ".concat(id.toString().concat(" no existe en base de datos!")));
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            response.put("mensaje", "El usuario ha sido actualizado con éxito!");
            response.put("cliente", usuario);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actaulizar usuario en base de datos");
            response.put("error", Objects.requireNonNull(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (!usuarioService.delete(id)) {
                response.put("mensaje", "El usuario que desea eliminar, con id ".concat(id.toString().concat(" no existe en base de datos!")));
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            response.put("mensaje", "El usuario ha sido eliminado con éxito!");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar usuario en base de datos");
            response.put("error", Objects.requireNonNull(e.getMessage()).concat(": ".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
