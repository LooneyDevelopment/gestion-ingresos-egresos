package com.mintic.gestioningresosegresos.services;

import com.mintic.gestioningresosegresos.models.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUsuarioService {
    List<Usuario> findAll();

    Page<Usuario> findAll(Pageable pageable);

    Usuario findById(Long id);

    Usuario save(Usuario usuario);

    Usuario update(Long id, Usuario usuario);

    boolean delete(Long id);
}
