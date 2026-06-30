package com.JAFCL.Tasks.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JAFCL.Tasks.model.entity.Tarea;
import com.JAFCL.Tasks.model.enums.EstadoTarea;
import com.JAFCL.Tasks.model.enums.PrioridadTarea;



public interface TareasRepository extends JpaRepository<Tarea, Long>{
    
    List<Tarea> findByEstado(EstadoTarea estado);
    List<Tarea> findByPrioridad(PrioridadTarea prioridad);
    List<Tarea> findByTitulo(String titulo);
}
