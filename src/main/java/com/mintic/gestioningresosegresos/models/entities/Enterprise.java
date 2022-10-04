package com.mintic.gestioningresosegresos.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "enterprises")
public class Enterprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false, unique = true)
    private String document;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private String address;

    @JsonIgnoreProperties(value = {"enterprise", "hibernateLazyInitializer", "handler"}, allowSetters = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "enterprise", cascade = CascadeType.ALL)
    private List<Usuario> usuarios;

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

 //   @JsonIgnoreProperties(value = {"enterprise", "hibernateLazyInitializer", "handler"}, allowSetters = true)
 //   @OneToMany(fetch = FetchType.LAZY, mappedBy = "enterprise", cascade = CascadeType.ALL)
 //   private List<Transaction> transactions;
}

