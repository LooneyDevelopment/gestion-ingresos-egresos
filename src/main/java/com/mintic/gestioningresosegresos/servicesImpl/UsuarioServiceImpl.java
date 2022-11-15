package com.mintic.gestioningresosegresos.servicesImpl;

import com.mintic.gestioningresosegresos.repositories.UsuarioRepository;
import com.mintic.gestioningresosegresos.models.entities.Usuario;
import com.mintic.gestioningresosegresos.models.enums.EnumRoleName;
import com.mintic.gestioningresosegresos.services.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class UsuarioServiceImpl implements IUsuarioService {
    @Autowired
    UsuarioRepository usuarioDao;

    @Override
    public List<Usuario> findAll() {
        return usuarioDao.findAll();
    }

    @Override
    public Page<Usuario> findAll(Pageable pageable) {
        return usuarioDao.findAll(pageable);
    }

    @Override
    public Usuario findById(Long id) {
        return usuarioDao.findById(id).orElse(null);
    }

    @Override
    public Usuario save(Usuario usuario) {
        usuario.setRoles(usuario.getRoles() != null ? usuario.getRoles() : Collections.singletonList(EnumRoleName.OPERARIO));
        return usuarioDao.save(usuario);
    }

    @Override
    public Usuario update(Long id, Usuario usuario) {
        Usuario usuarioFound = findById(id);
        if (usuarioFound != null) {
            usuarioFound.setName(usuario.getName() != null ? usuario.getName() : usuarioFound.getName());
            usuarioFound.setEmail(usuario.getEmail() != null ? usuario.getEmail() : usuarioFound.getEmail());
            usuarioFound.setRoles(usuario.getRoles() != null ? usuario.getRoles() : usuarioFound.getRoles());
            usuarioFound.setUpdatedAt(new Date());
            return save(usuarioFound);
        }
        return usuarioFound;
    }

    @Override
    public boolean delete(Long id) {
        Usuario usuarioFound = findById(id);
        boolean deleted = true;
        if (usuarioFound != null) {
            usuarioDao.deleteById(id);
            return deleted;
        }
        return !deleted;
    }
}
