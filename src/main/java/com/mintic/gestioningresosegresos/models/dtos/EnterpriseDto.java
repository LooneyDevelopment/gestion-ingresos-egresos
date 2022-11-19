package com.mintic.gestioningresosegresos.models.dtos;

import com.mintic.gestioningresosegresos.models.entities.Empleado;
import com.mintic.gestioningresosegresos.models.enums.EnumRoleName;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class EnterpriseDto {
    private Long id;
    private String name;
    private String nit;
    private String phone;
    private String address;
    private Date createdAt;
    private Date updatedAt;
    private List<Empleado> empleados;
}
