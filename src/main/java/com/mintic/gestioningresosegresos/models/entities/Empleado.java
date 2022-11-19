package com.mintic.gestioningresosegresos.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mintic.gestioningresosegresos.models.enums.EnumRoleName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "empleados")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @Column(name = "update_at")
    @Temporal(TemporalType.DATE)
    private Date updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = new Date();
    }

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = EnumRoleName.class, fetch = FetchType.EAGER)
    private List<EnumRoleName> roles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"empleados", "hibernateLazyInitializer", "handler"}, allowSetters = true)
    private Enterprise enterprise;

 /*   @OneToOne(mappedBy = "employee")
    private Profile profile;

    //De Uno a muchos para Comunicar de employee a transaction
    @JsonIgnoreProperties(value = {"employee", "hibernateLazyInitializer", "handler"}, allowSetters = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Transaction> transactions;
 */
}
