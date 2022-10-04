package com.mintic.gestioningresosegresos.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "movements")
public class Movement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Float amount;

    @Column(nullable = false)
    private String concept;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"employees", "hibernateLazyInitializer", "handler"}, allowSetters = true)
    private Usuario usuario;

    @Column(name = "create_at", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @Column(name = "update_at")
    @Temporal(TemporalType.DATE)
    private Date updatedAt;

 /*   @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"enterprises", "hibernateLazyInitializer", "handler"}, allowSetters = true)
    private Enterprise enterprise; */
}
