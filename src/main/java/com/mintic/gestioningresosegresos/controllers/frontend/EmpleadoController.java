package com.mintic.gestioningresosegresos.controllers.frontend;

import com.mintic.gestioningresosegresos.models.dtos.EmpleadoDto;
import com.mintic.gestioningresosegresos.models.entities.Empleado;
import com.mintic.gestioningresosegresos.models.entities.Enterprise;
import com.mintic.gestioningresosegresos.models.enums.EnumRoleName;
import com.mintic.gestioningresosegresos.services.IEmpleadoService;
import com.mintic.gestioningresosegresos.services.IEnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestMapping("empleados")
@Controller
public class EmpleadoController {

    public String TITULO_EMPLEADOS = "Sistemas de gestión de empleados";

    @Autowired
    IEmpleadoService empleadoService;

    @Autowired
    IEnterpriseService enterpriseService;

    @GetMapping
    public String viewHome(Model model) {
        model.addAttribute("empleados", empleadoService.findAll());
        model.addAttribute("title", "Empleados");
        return "empleados";
    }

    @GetMapping("{email}")
    public String viewDetalleEmpleado(@PathVariable String email, Model model) {
        model.addAttribute("empleado", empleadoService.findByEmail(email));
        return "detalle-empleado";
    }

    @GetMapping("nuevo")
    public String getEmpleadoNuevo(Model model, @ModelAttribute("empleado") EmpleadoDto empleado){
        List<Enterprise> enterprises = new ArrayList<>(enterpriseService.findAll());
        model.addAttribute("empresas", enterprises);
        model.addAttribute("title", "Nuevo empleado");
        model.addAttribute("tituloPrincipal", TITULO_EMPLEADOS);
        model.addAttribute("accion", "Crear empleado");
        model.addAttribute("roles", EnumRoleName.values());
        model.addAttribute("method", "POST");
        return "new-empleado";
    }

    @PostMapping("nuevo")
    public String postEmpleado(@ModelAttribute("empleado") EmpleadoDto empleadoDto, Model model){
        try {
            empleadoDto.setCreatedAt(new Date());
            empleadoDto.setUpdatedAt(new Date());
            System.out.println(empleadoDto);
            empleadoService.save(crearEmpleado(empleadoDto), empleadoDto.getNitEmpresa());
            return "redirect:/empleados";
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

    private Empleado crearEmpleado(EmpleadoDto empleadoDto) {
        Empleado empleado = new Empleado();
        empleado.setName(empleadoDto.getName());
        empleado.setRoles(empleadoDto.getRoles());
        empleado.setEmail(empleadoDto.getEmail());
        empleado.setCreatedAt(empleadoDto.getCreatedAt());
        empleado.setUpdatedAt(empleadoDto.getUpdatedAt());
        return empleado;
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Long id){
        empleadoService.delete(id);
        return "redirect:/empleados";
    }

    @GetMapping("editar/{id}")
    public String update(Model model, @PathVariable Long id){
        try {
            model.addAttribute("empresas", enterpriseService.findAll());
            model.addAttribute("empleado", empleadoService.findById(id));
            model.addAttribute("title", "Editar empleado");
            model.addAttribute("tituloPrincipal", TITULO_EMPLEADOS);
            model.addAttribute("accion", "Actualizar empleado");
            model.addAttribute("roles", EnumRoleName.values());
            model.addAttribute("method", "PATCH");
            return "new-empleado";
        } catch (Exception e) {
            return "redirect:/error";
        }
    }

    @PatchMapping("editar/{id}/empresa/{nit}")
    public String update(@ModelAttribute("updateEmpleado") Empleado empleado, @PathVariable Long id, @PathVariable String nit){
        try {
            empleadoService.update(id, empleado, nit);
            return "redirect:/empleados";
        } catch (Exception e) {
            return "redirect:/error";
        }

    }
 /*

    @GetMapping("/empleado/add")
    public String getAddEmpleado(Model model){

        model.addAttribute("newEmpleado",new Empleado());
        model.addAttribute("perfiles",perfilService.getPefiles());
        model.addAttribute("roles", Rol.values());

        return "add-empleado";
    }

    @GetMapping("/empleado/update/front/{id}")
    public String getEmpleado(Model model, @PathVariable String id){

        try {
            model.addAttribute("UpdateEmpleado",empleadoService.getEmpleado(id));
            model.addAttribute("perfiles",perfilService.getPefiles());
            model.addAttribute("roles", Rol.values());
            return "update-empleado";
        } catch (Exception e) {
            return "redirect:/error";
        }

    }

    @PatchMapping("/empleado/front/{id}")
    public String patchEmpleado(@ModelAttribute("UpdateEmpleado") Empleado empleado, @PathVariable String id){

        try {
            empleadoService.patchEmpleado(empleado,id);
            return "redirect:/welcome";
        } catch (Exception e) {
            return "redirect:/error";
        }

    }

    @PostMapping("/empleado/front")
    public String postEmpleado(@ModelAttribute("newEmpleado") Empleado empleado){

        empleadoService.saveEmpleado(empleado);

        return "redirect:/welcome";
    }

*/
}
