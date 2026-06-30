package com.JAFCL.Tasks.dto;

import lombok.Data;

@Data
public class EstadisticasDTO {
    /**
     * Grupos por estados.
     */
    private String grupoEstados;
    /**
     * Cantidad de tareas por grupo.
     */
    private int cantidadTareas;
}
