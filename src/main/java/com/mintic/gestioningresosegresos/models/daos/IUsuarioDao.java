package com.mintic.gestioningresosegresos.models.daos;

import com.mintic.gestioningresosegresos.models.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioDao extends JpaRepository<Usuario, Long> {
}
