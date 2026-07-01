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

import com.JAFCL.Tasks.dto.EstadisticasDTO;
import com.JAFCL.Tasks.service.TareasService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TareasController {

    private final TareasService service;

    /**
     * Método del "Endpoint" que llama al objeto de clase "Service" para que muestre todas las tareas que hay.
     * @return = Lista de tareas en formato JSON.
     */
    @GetMapping("/tareas")
    public HttpGlobalResponse<List<TareaResponseDTO>> listarTareas() {
        HttpGlobalResponse<List<TareaResponseDTO>> response = service.listarTareas();
        return response;
    }
    /**
     * Con este código, el objeto "Controller" llama al objeto "Service" para que traiga la tarea que coincide con el
     * I.D. digitado.
     * @param id = Es el I.D. que se quiere utilizar cómo parámetro para la consulta.
     * @return = Devuelve una tarea en fromato JSON.
     */
    @GetMapping("/tareas/{id}")
    public HttpGlobalResponse<TareaResponseDTO> obtenerTareaId(@PathVariable Long id) {
        HttpGlobalResponse<TareaResponseDTO> response = service.obtenerTareaId(id);
        return response;
    }
    /***
     * Este fragmento de código llama al objeto de "Service" para poder utilizar su función de crear una nueva tarea.
     * @param request = Se utiliza un DTO para registrar, con formato JSON, una tarea.
     * @return = Se retorne el DTO global para informar que la operación fue correcta.
     */
    @PostMapping("/tareas")
    public HttpGlobalResponse<Void> crearTarea(@RequestBody RegisterRequestDTO request) {
        HttpGlobalResponse<Void> response = service.crearTarea(request);
        return response;
    }
    /**
     * Aquí se acude al "Service" para actualizar una tarea existente.
     * @param id = El I.D. de la tarea se pasa como parametro por la URL.
     * @param request = Se utiliza el DTO para hacer registros.
     * @return = Con el DTO global de la API se muestra los resultados del procedimiento.
     */
    @PutMapping("/tareas/{id}")
    public HttpGlobalResponse<TareaResponseDTO> actualizarTarea(@PathVariable Long id, @RequestBody RegisterRequestDTO request) {
        HttpGlobalResponse<TareaResponseDTO> response = service.actualizarTarea(id, request);        
        return response;
    }
    /**
     * Objeto "Controller" llama al "Service" para que se le haga cambio de estado a una tarea en especifico.
     * @param id = Se ingresa el I.D. de la tarea para asegurarse de que es la que se quiere cambiar.
     * @param estado = Se ingrea el nuevo estado que se quiere poner a la tarea.
     * @return = Retorna una respuesta a la petición hecha, dentro del DTO global de la API.
     */
    @PatchMapping("/tareas/{id}/estado")
    public HttpGlobalResponse<TareaResponseDTO> cambioEstadoTarea(@PathVariable Long id, EstadoTarea estado) {
        HttpGlobalResponse<TareaResponseDTO> response = service.cambioEstadoTarea(id, estado);
        return response;
    }
    /***
     * El objeto "Controller" es intermediario para pedirle al objeto del "Service" que borre un registro de una tarea.
     * @param id = Es el parámetro con el que se indica qué tarea borrar.
     * @return = Retorna una confirmación de la operación, envuelta en le DTO global de la API.
     */
    @DeleteMapping("/tareas/{id}")
    public HttpGlobalResponse<Void> eliminarTarea(@PathVariable Long id) {
        HttpGlobalResponse<Void> response = service.eliminarTarea(id);
        return response;
    }
    /**
     * La siguinte función hace que el objeto "Service" filtre las tareas que tienen el estado que se le está suministrando.
     * @param estado = Parámetro para filtrar la consulta.
     * @return = Listado de tareas envuelto en el DTO global de la API y en formato JSON.
     */
    @GetMapping("/tareas/filtrar/estado")
    public HttpGlobalResponse<List<TareaResponseDTO>> filtrarTareasEstado(@RequestParam EstadoTarea estado) {
        HttpGlobalResponse<List<TareaResponseDTO>> response = service.filtrarTareasEstado(estado);
        return response;
    }
    /**
     * Por medio del "Controller", se acude al "Service" y poder filtrar las tareas por su "prioridad".
     * @param prioridad = Parámetro que se pasa para su consulta.
     * @return = Lista de tareas con la prioridad dada como parámetro, en formato JSON.
     */
    @GetMapping("/tareas/filtrar/prioridad")
    public HttpGlobalResponse<List<TareaResponseDTO>> filtrarTareasPrioridad(@RequestParam PrioridadTarea prioridad) {
        HttpGlobalResponse<List<TareaResponseDTO>> response = service.filtrarTareasPrioridad(prioridad);
        return response;
    }
    /**
     * Aquí, el "Controller" acude al "Service" para filtrar la tareas por su título.
     * @param titulo = Son las palabras que se pasan como parámetro de la consulta.
     * @return = Retorna los resultados en una lista tipo JSON.
     */
    @GetMapping("/tareas/buscar")
    public HttpGlobalResponse<List<TareaResponseDTO>> buscarTareasTitulo(@RequestParam String titulo) {
        HttpGlobalResponse<List<TareaResponseDTO>> response = service.buscarTareasTitulo(titulo);
        return response;
    }
    /**
     * Por medio de esta función, se acude al objeto "Service" para que brinde estadísticas de las tareas almacenadas.
     * @return = Retorna la cantidad de tareas por cada estado y el total de todas ellas.
     */
    @GetMapping("/tareas/estadisticas")
    public HttpGlobalResponse<List<EstadisticasDTO>> mostrarEstadisticas() {
        HttpGlobalResponse<List<EstadisticasDTO>> response = service.mostrarEstadisticas();
        return response;
    }
    
}