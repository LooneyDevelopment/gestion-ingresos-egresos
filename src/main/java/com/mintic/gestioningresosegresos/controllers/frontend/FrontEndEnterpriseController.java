package com.mintic.gestioningresosegresos.controllers.frontend;

import com.mintic.gestioningresosegresos.models.entities.Enterprise;
import com.mintic.gestioningresosegresos.services.IEnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestMapping("empresas")
@Controller
public class FrontEndEnterpriseController {

    public String TITULO_EMPRESAS = "Sistemas de gestión de empresas";

    @Autowired
    IEnterpriseService enterpriseService;

    @GetMapping
    public String viewHome(Model model) {
        model.addAttribute("enterprises", enterpriseService.findAll());
        return "empresas";
    }

    @GetMapping("{document}")
    public String viewDetalleEmpresa(@PathVariable String document, Model model) {
        model.addAttribute("enterprise", enterpriseService.findByDocument(document));
        return "detalle-empresa";
    }

    @GetMapping("nueva")
    public String getEnterpriseNueva(Model model, @ModelAttribute("empresa") Enterprise enterprise){
        model.addAttribute("title", "Nueva empresa");
        model.addAttribute("tituloPrincipal", TITULO_EMPRESAS);
        model.addAttribute("accion", "Crear empresa");
        model.addAttribute("method", "POST");
        return "new-empresa";
    }

    @PostMapping("nueva")
    public String postUsuario(@ModelAttribute("empresa") Enterprise enterprise, Model model){
        enterprise.setCreatedAt(new Date());
        enterprise.setUpdatedAt(new Date());
        enterprise.setUsuarios(new ArrayList<>());
        try {
            enterpriseService.save(enterprise);
            return "redirect:/empresas";
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
        enterpriseService.delete(id);
        return "redirect:/empresas";
    }

    @GetMapping("editar/{id}")
    public String update(Model model, @PathVariable Long id){
        try {
            model.addAttribute("empresa", enterpriseService.findById(id));
            model.addAttribute("title", "Editar empresa");
            model.addAttribute("tituloPrincipal", TITULO_EMPRESAS);
            model.addAttribute("accion", "Actualizar empresa");
            model.addAttribute("method", "PATCH");
            return "editar-empresa";
        } catch (Exception e) {
            return "redirect:/error";
        }
    }

    @PatchMapping("editar/{id}")
    public String update(@ModelAttribute("updateEmpresa") Enterprise enterprise, @PathVariable Long id){
        try {
            enterpriseService.update(id, enterprise);
            return "redirect:/empresas";
        } catch (Exception e) {
            return "redirect:/error";
        }

    }
 /*   @DeleteMapping("/usuario/delete/front/{id}")
    public String deleteUsuario(@PathVariable String id){
        usuarioService.delete(id);
        return "redirect:/welcome";
    }

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
