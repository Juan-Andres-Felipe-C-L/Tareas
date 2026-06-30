package com.JAFCL.Tasks.model.entity;

import java.time.LocalDateTime;

import com.JAFCL.Tasks.model.enums.EstadoTarea;
import com.JAFCL.Tasks.model.enums.PrioridadTarea;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tasks")
@Data
public class Tarea {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descripcion")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoTarea estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "prioridad")
    private PrioridadTarea prioridad;

    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @PrePersist
    public void prePersist() {

        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();

    }


    @PreUpdate
    public void preUpdate() {

        fechaActualizacion = LocalDateTime.now();

    }


}
