package com.mintic.gestioningresosegresos.repositories;

import com.mintic.gestioningresosegresos.models.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
