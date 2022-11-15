package com.mintic.gestioningresosegresos.controllers.frontend;

import com.mintic.gestioningresosegresos.models.entities.Usuario;
import com.mintic.gestioningresosegresos.models.enums.EnumRoleName;
import com.mintic.gestioningresosegresos.services.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestMapping("usuarios")
@Controller
public class UsuarioController {

    public String TITULO_USUARIOS = "Sistemas de gestión de usuarios";

    @Autowired
    IUsuarioService userService;

    @GetMapping
    public String viewHome(Model model) {
        model.addAttribute("usuarios", userService.findAll());
        model.addAttribute("title", "Usuarios");
        return "usuarios";
    }

    @GetMapping("{email}")
    public String viewDetalleUsuario(@PathVariable String email, Model model) {
        model.addAttribute("user", userService.findByEmail(email));
        return "detalle-usuario";
    }

    @GetMapping("nuevo")
    public String getUsuarioNuevo(Model model, @ModelAttribute("usuario") Usuario user){
        model.addAttribute("title", "Nuevo usuario");
        model.addAttribute("tituloPrincipal", TITULO_USUARIOS);
        model.addAttribute("accion", "Crear usuario");
        model.addAttribute("roles", EnumRoleName.values());
        model.addAttribute("method", "POST");
        return "new-usuario";
    }

    @PostMapping("nuevo")
    public String postUsuario(@ModelAttribute("usuario") Usuario user, Model model){
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        try {
            System.out.println(user);
            userService.save(user);
            return "redirect:/usuarios";
        } catch (DataAccessException e) {
            List<String> partsOfError = List.of(e.getMostSpecificCause().getLocalizedMessage().split(":"));
            String detail = partsOfError.get(2);
            model.addAttribute("error", detail);
            return "error";
        } catch (Exception e) {
            model.addAttribute("error", e.getLocalizedMessage());
            return "error";
        }
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Long id){
        userService.delete(id);
        return "redirect:/usuarios";
    }

    @GetMapping("editar/{id}")
    public String update(Model model, @PathVariable Long id){
        try {
            model.addAttribute("usuario", userService.findById(id));
            model.addAttribute("title", "Editar usuario");
            model.addAttribute("tituloPrincipal", TITULO_USUARIOS);
            model.addAttribute("accion", "Actualizar usuario");
            model.addAttribute("roles", EnumRoleName.values());
            model.addAttribute("method", "PATCH");
            return "new-usuario";
        } catch (Exception e) {
            return "redirect:/error";
        }
    }

    @PatchMapping("editar/{id}")
    public String update(@ModelAttribute("updateUsuario") Usuario user, @PathVariable Long id){
        try {
            userService.update(id, user);
            return "redirect:/usuarios";
        } catch (Exception e) {
            return "redirect:/error";
        }

    }
 /*

    @GetMapping("/usuario/add")
    public String getAddUsuario(Model model){

        model.addAttribute("newUsuario",new Usuario());
        model.addAttribute("perfiles",perfilService.getPefiles());
        model.addAttribute("roles", Rol.values());

        return "add-user";
    }

    @GetMapping("/usuario/update/front/{id}")
    public String getUsuario(Model model, @PathVariable String id){

        try {
            model.addAttribute("UpdateUsuario",usuarioService.getUsuario(id));
            model.addAttribute("perfiles",perfilService.getPefiles());
            model.addAttribute("roles", Rol.values());
            return "update-user";
        } catch (Exception e) {
            return "redirect:/error";
        }

    }

    @PatchMapping("/usuario/front/{id}")
    public String patchUsuario(@ModelAttribute("UpdateUsuario") Usuario usuario, @PathVariable String id){

        try {
            usuarioService.patchUsuario(usuario,id);
            return "redirect:/welcome";
        } catch (Exception e) {
            return "redirect:/error";
        }

    }

    @PostMapping("/usuario/front")
    public String postUsuario(@ModelAttribute("newUsuario") Usuario usuario){

        usuarioService.saveUsuario(usuario);

        return "redirect:/welcome";
    }

*/
}
