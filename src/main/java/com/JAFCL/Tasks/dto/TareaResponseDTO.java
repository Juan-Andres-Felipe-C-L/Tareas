package com.JAFCL.Tasks.dto;

import java.time.LocalDateTime;

import com.JAFCL.Tasks.model.enums.EstadoTarea;
import com.JAFCL.Tasks.model.enums.PrioridadTarea;

import lombok.Data;

@Data
public class TareaResponseDTO {
    /**
     * I.D. único de la tarea.
     */
    private Long id;
    /**
     * El título con el que se identifica la tarea.
     */
    private String titulo;
    /**
     * Descripción o detalle de la tarea.
     */
    private String descripcion;
    /**
     * Estado de la tarea.
     */
    private EstadoTarea estado;
    /**
     * La prioridad puesta por el usuario a la tarea.
     */
    private PrioridadTarea prioridad;
    /**
     * Fecha de creación de la tarea.
     */
    private LocalDateTime fechaCreacion;
    /**
     * Fecha de útlima modificación a la tarea.
     */
    private LocalDateTime fechaActualizacion;
}
