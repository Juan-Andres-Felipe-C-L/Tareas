package com.JAFCL.Tasks.dto;

import com.JAFCL.Tasks.model.enums.EstadoTarea;
import com.JAFCL.Tasks.model.enums.PrioridadTarea;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequestDTO {
    /**
     * Título de la tarea que debe de tener como mínimo 3 caracteres.
     */
    @NotBlank()
    @Size(min = 3, message = "Usuario, el título es obligatorio y debe tener mínimo 3 caracteres.")
    private String titulo;
    /**
     * Descripción de la tarea.
     */
    private String descripcion;
    /**
     * Estado de la tarea.
     */
    private EstadoTarea estado;
    /**
     * Prioridad de la tarea que es obligatoria.
     */
    @NotNull(message = "Usuario, es obligatorio poner la prioridad a la tarea.")
    private PrioridadTarea prioridad;

}   

