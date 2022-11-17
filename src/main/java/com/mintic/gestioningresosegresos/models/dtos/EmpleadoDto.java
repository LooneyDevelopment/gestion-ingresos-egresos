package com.mintic.gestioningresosegresos.models.dtos;

import com.mintic.gestioningresosegresos.models.enums.EnumRoleName;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class EmpleadoDto {
    private Long id;
    private String name;
    private String email;
    private Date createdAt;
    private Date updatedAt;
    private List<EnumRoleName> roles;
    private String nitEmpresa;
}
