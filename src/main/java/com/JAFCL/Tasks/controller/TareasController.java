package com.JAFCL.Tasks.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.JAFCL.Tasks.dto.HttpGlobalResponse;
import com.JAFCL.Tasks.dto.RegisterRequestDTO;
import com.JAFCL.Tasks.dto.TareaResponseDTO;
import com.JAFCL.Tasks.model.enums.EstadoTarea;
import com.JAFCL.Tasks.model.enums.PrioridadTarea;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TareasController {

    private final TareasService service;

    @GetMapping("/tareas")
    public HttpGlobalResponse<List<TareaResponseDTO>> listarTareas() {
        HttpGlobalResponse<List<TareaResponseDTO>> response = service.listarTareas();
        return response;
    }

    @GetMapping("/tareas/{id}")
    public HttpGlobalResponse<TareaResponseDTO> obtenerTareaId(@PathVariable Long id) {
        HttpGlobalResponse<TareaResponseDTO> response = service.obtenerTareaId(id);
        return response;
    }
    
    @PostMapping("/tareas")
    public HttpGlobalResponse<TareaResponseDTO> crearTarea(@RequestBody RegisterRequestDTO request) {
        HttpGlobalResponse<TareaResponseDTO> response = service.crearTarea(request);
        return response;
    }

    @PutMapping("/tareas/{id}")
    public HttpGlobalResponse<TareaResponseDTO> actualizarTarea(@PathVariable Long id, @RequestBody RegisterRequestDTO request) {
        HttpGlobalResponse<TareaResponseDTO> response = service.actualizarTarea(id, request);        
        return response;
    }
    
    @PatchMapping("/tareas/{id}/estado")
    public HttpGlobalResponse<TareaResponseDTO> cambioEstadoTarea(@PathVariable Long id, EstadoTarea estado) {
        HttpGlobalResponse<TareaResponseDTO> response = service.cambioEstadoTarea(id, estado);
        return response;
    }

    @DeleteMapping("/tareas/{id}")
    public HttpGlobalResponse<Void> eliminarTarea(@PathVariable Long id) {
        HttpGlobalResponse<Void> response = service.eliminarTarea(id);
        return response;
    }

    @GetMapping("/tareas/filtrar/estado")
    public HttpGlobalResponse<List<TareaResponseDTO>> filtrarTareasEstado(@RequestParam EstadoTarea estado) {
        HttpGlobalResponse<List<TareaResponseDTO>> response = service.filtrarTareasEstado(estado);
        return response;
    }
    
    @GetMapping("/tareas/filtrar/prioridad")
    public HttpGlobalResponse<List<TareaResponseDTO>> filtrarTareasPrioridad(@RequestParam PrioridadTarea prioridad) {
        HttpGlobalResponse<List<TareaResponseDTO>> response = service.filtrarTareasPrioridad(prioridad);
        return response;
    }

    @GetMapping("/tareas/buscar")
    public HttpGlobalResponse<List<TareaResponseDTO>> buscarTareasTitulo(@RequestParam String titulo) {
        HttpGlobalResponse<List<TareaResponseDTO>> response = service.buscarTareasTitulo(titulo);
        return response;
    }

    
}
