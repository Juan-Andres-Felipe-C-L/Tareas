package com.JAFCL.Tasks.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.JAFCL.Tasks.dto.EstadisticasDTO;
import com.JAFCL.Tasks.dto.HttpGlobalResponse;
import com.JAFCL.Tasks.dto.RegisterRequestDTO;
import com.JAFCL.Tasks.dto.TareaResponseDTO;
import com.JAFCL.Tasks.model.entity.Tarea;
import com.JAFCL.Tasks.model.enums.EstadoTarea;
import com.JAFCL.Tasks.model.enums.PrioridadTarea;
import com.JAFCL.Tasks.repository.TareasRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TareasService {
    
    private final TareasRepository repository;
    /**
     * Con este método, el objeto "service" muestra todas las tareas que han sido añadidas a la base de datos.
     * @return = Retorna una lista de tareas en formato JSON.
     */
    public HttpGlobalResponse<List<TareaResponseDTO>> listarTareas() {
        HttpGlobalResponse<List<TareaResponseDTO>> response = new HttpGlobalResponse<List<TareaResponseDTO>>();
        List<TareaResponseDTO> listaFinal = new ArrayList<TareaResponseDTO>();
        List<Tarea> listaEncontrada = repository.findAll();
        if (listaEncontrada.isEmpty()) {
            response.setSuccess(true);
            response.setMensaje("Usuario, aún no hay tareas registradas para mostrar.");
            response.setData(null);
        } else {
            for (Tarea tarea : listaEncontrada) {
                TareaResponseDTO tareafinal = new TareaResponseDTO();
                tareafinal.setId(tarea.getId());
                tareafinal.setTitulo(tarea.getTitulo());
                tareafinal.setDescripcion(tarea.getDescripcion());
                tareafinal.setEstado(tarea.getEstado());
                tareafinal.setPrioridad(tarea.getPrioridad());
                tareafinal.setFechaCreacion(tarea.getFechaCreacion());
                tareafinal.setFechaActualizacion(tarea.getFechaActualizacion());
                listaFinal.add(tareafinal);
            }
            response.setSuccess(true);
            response.setMensaje("Consulta acerca de todas las tareas creadas exitosa.");
            response.setData(listaFinal);
        }
        return response;
    }
    /**
     * En esta parte, el objeto "Service" hace la consulta de la tarea que coincida con el I.D. digitado para mostrarla.
     * @param id = I.D. brindado por el uaurio, a través del cliente y por medio de URL.
     * @return = Retorna la tarea que coincida, envuelta en el DTO global de la aplicación y en formato JSON.
     */
    public HttpGlobalResponse<TareaResponseDTO> obtenerTareaId(Long id) {
        HttpGlobalResponse<TareaResponseDTO> response = new HttpGlobalResponse<TareaResponseDTO>();
        Optional<Tarea> tareaEncontrada = repository.findById(id);
        if (tareaEncontrada.isEmpty()) {
            response.setSuccess(true);
            response.setMensaje("Usuario, el I.D. escrito no coincide con alguna tarea.");
            response.setData(null);
        } else {
            Tarea tarea = tareaEncontrada.get();
            TareaResponseDTO tareaFinal = new TareaResponseDTO();
            tareaFinal.setId(tarea.getId());
            tareaFinal.setTitulo(tarea.getTitulo());
            tareaFinal.setDescripcion(tarea.getDescripcion());
            tareaFinal.setEstado(tarea.getEstado());
            tareaFinal.setPrioridad(tarea.getPrioridad());
            tareaFinal.setFechaCreacion(tarea.getFechaCreacion());
            tareaFinal.setFechaActualizacion(tarea.getFechaActualizacion());
            
            response.setSuccess(true);
            response.setMensaje("Usuario, la tarea consultada es la siguiente:");
            response.setData(tareaFinal);
        }
        return response;
    }
    /***
     * La siguiente función crear una nueva tarea.
     * @param request = Se utiliza un DTO para registrar, con formato JSON, una tarea.
     * @return = Se retorne el DTO global para informar que la operación fue correcta.
     */
    public HttpGlobalResponse<Void> crearTarea(RegisterRequestDTO request) {
        HttpGlobalResponse<Void> response = new HttpGlobalResponse<Void>();
        Tarea tarea = new Tarea();
        tarea.setTitulo(request.getTitulo());
        tarea.setDescripcion(request.getDescripcion());
        tarea.setEstado(request.getEstado());
        tarea.setPrioridad(request.getPrioridad());
        repository.save(tarea);

        response.setSuccess(true);
        response.setMensaje("La tarea de título: '" + request.getTitulo() + "' ha sido almacenada satisfactoriamente.");
        response.setData(null);
        return response;
    }
    /**
     * Con el objeto del "Service" se actualiza una tarea que ya está archivada.
     * @param id = Se pasa un I.D. para identificar la tarea que se quiere cambiar.
     * @param request = A través del DTO para hacer registros de tareas, se le utiliza para hacer la actualización.
     * @return = Se brinda una respuesta, acerca de la operación, envuelta en el DTO global de la aplicación.
     */
    public HttpGlobalResponse<TareaResponseDTO> actualizarTarea(Long id, RegisterRequestDTO request) {
        HttpGlobalResponse<TareaResponseDTO> response = new HttpGlobalResponse<TareaResponseDTO>();
        TareaResponseDTO tareaFinal = new TareaResponseDTO();
        Optional<Tarea> tareaEncontrada = repository.findById(id);
        if (tareaEncontrada.isEmpty()) {
            response.setSuccess(true);
            response.setMensaje("Usuario, el I.D. escrito no coincide con alguna tarea.");
            response.setData(null);
        } else {
            Tarea tarea = tareaEncontrada.get();
            if (tarea.getEstado() == EstadoTarea.CANCELADA) {
                response.setSuccess(true);
                response.setMensaje("Usuario, una tarea con estado 'CANCELADA' no puede ser modificada.");
                response.setData(null);
            } else if (tarea.getEstado() == EstadoTarea.COMPLETADA) {
                if (request.getEstado() == EstadoTarea.PENDIENTE) {
                    response.setSuccess(true);
                    response.setMensaje("Usuario, una tarea con estado 'COMPLETADA' no puede pasar a 'PENDIENTE' o 'EN_PROGRESO.");
                    response.setData(null);
                } else if (request.getEstado() == EstadoTarea.EN_PROGRESO) {
                    response.setSuccess(true);
                    response.setMensaje("Usuario, una tarea con estado 'COMPLETADA' no puede pasar a 'PENDIENTE' o 'EN_PROGRESO.");
                    response.setData(null);
                }
            }
            tarea.setTitulo(request.getTitulo());
            tarea.setDescripcion(request.getDescripcion());
            tarea.setEstado(request.getEstado());
            tarea.setPrioridad(request.getPrioridad());
            repository.save(tarea);

            tareaFinal.setId(tarea.getId());
            tareaFinal.setTitulo(tarea.getTitulo());
            tareaFinal.setDescripcion(tarea.getDescripcion());
            tareaFinal.setEstado(tarea.getEstado());
            tareaFinal.setPrioridad(tarea.getPrioridad());
            tareaFinal.setFechaCreacion(tarea.getFechaCreacion());
            tareaFinal.setFechaActualizacion(tarea.getFechaActualizacion());

            response.setSuccess(true);
            response.setMensaje("Usuario, se le muestra la tarea actualizada: ");
            response.setData(tareaFinal);
        }

        return response;
    }
    /**
     * Se utiliza el objeto del "Service" para modidficar un estado de una tarea.
     * @param id = Se pasa como parámetro el I.D. de la tarea que se quiere modificar.
     * @param estado = Se pasa como parámetro el estado que quiere poner a la tarea.
     * @return = Retorna información de la solicitud hecha al objeto "Service".
     */
    public HttpGlobalResponse<TareaResponseDTO> cambioEstadoTarea(Long id, EstadoTarea estado) {
    HttpGlobalResponse<TareaResponseDTO> response = new HttpGlobalResponse<TareaResponseDTO>();
        TareaResponseDTO tareaFinal = new TareaResponseDTO();
        Optional<Tarea> tareaEncontrada = repository.findById(id);
        if (tareaEncontrada.isEmpty()) {
            response.setSuccess(true);
            response.setMensaje("Usuario, el I.D. escrito no coincide con alguna tarea.");
            response.setData(null);
        } else {
            Tarea tarea = tareaEncontrada.get();
            if (tarea.getEstado() == EstadoTarea.CANCELADA) {
                response.setSuccess(true);
                response.setMensaje("Usuario, una tarea con estado 'CANCELADA' no puede ser modificada.");
                response.setData(null);
            } else if (tarea.getEstado() == EstadoTarea.COMPLETADA) {
                if (estado == EstadoTarea.PENDIENTE) {
                    response.setSuccess(true);
                    response.setMensaje("Usuario, una tarea con estado 'COMPLETADA' no puede pasar a 'PENDIENTE' o 'EN_PROGRESO.");
                    response.setData(null);
                } else if (estado == EstadoTarea.EN_PROGRESO) {
                    response.setSuccess(true);
                    response.setMensaje("Usuario, una tarea con estado 'COMPLETADA' no puede pasar a 'PENDIENTE' o 'EN_PROGRESO.");
                    response.setData(null);
                }
            }
            tarea.setEstado(estado);
            repository.save(tarea);

            tareaFinal.setId(tarea.getId());
            tareaFinal.setTitulo(tarea.getTitulo());
            tareaFinal.setDescripcion(tarea.getDescripcion());
            tareaFinal.setEstado(tarea.getEstado());
            tareaFinal.setPrioridad(tarea.getPrioridad());
            tareaFinal.setFechaCreacion(tarea.getFechaCreacion());
            tareaFinal.setFechaActualizacion(tarea.getFechaActualizacion());

            response.setSuccess(true);
            response.setMensaje("Usuario, se le muestra la tarea con su estado actualizado: ");
            response.setData(tareaFinal);
        }

        return response;
    }
    /***
     * Aquí,el "Service" borra un registro de una tarea.
     * @param id = Es el parámetro con el que se indica qué tarea borrar.
     * @return = Retorna una confirmación de la operación, envuelta en le DTO global de la API.
     */
    public HttpGlobalResponse<Void> eliminarTarea(@PathVariable Long id) {
        HttpGlobalResponse<Void> response = new HttpGlobalResponse<Void>();
        Optional<Tarea> tareaEncontrada = repository.findById(id);
        if (tareaEncontrada.isEmpty()) {
            response.setSuccess(true);
            response.setMensaje("Usuario, no se encontró una tarea identificada con el I.D. registrado.");
            response.setData(null);
        } else{
            repository.deleteById(id);
            response.setSuccess(true);
            response.setMensaje("Usuario, se eliminó la tarea con el I.D. número" + id + " exitosamente.");
            response.setData(null);
        }
        return response;
    }
    /**
     * La siguinte función hace que el objeto "Service" filtre las tareas que tienen el estado que se le está suministrando.
     * @param estado = Parámetro para filtrar la consulta.
     * @return = Listado de tareas envuelto en el DTO global de la API y en formato JSON.
     */
    public HttpGlobalResponse<List<TareaResponseDTO>> filtrarTareasEstado(@RequestParam EstadoTarea estado) {
        HttpGlobalResponse<List<TareaResponseDTO>> response = new HttpGlobalResponse<List<TareaResponseDTO>>();
        List<TareaResponseDTO> listaFinal = new ArrayList<TareaResponseDTO>();
        List<Tarea> listaEncontrada = repository.findByEstado(estado);
        if (listaEncontrada.isEmpty()) {
            response.setSuccess(true);
            response.setMensaje("Usuario, no hay tareas registradas con el estado '" + estado + "'.");
            response.setData(null);
        } else {
            for (Tarea tarea : listaEncontrada) {
                TareaResponseDTO tareaFinal = new TareaResponseDTO();
                tareaFinal.setId(tarea.getId());
                tareaFinal.setTitulo(tarea.getTitulo());
                tareaFinal.setDescripcion(tarea.getDescripcion());
                tareaFinal.setEstado(tarea.getEstado());
                tareaFinal.setPrioridad(tarea.getPrioridad());
                tareaFinal.setFechaCreacion(tarea.getFechaCreacion());
                tareaFinal.setFechaActualizacion(tarea.getFechaActualizacion());
                listaFinal.add(tareaFinal);
            }
            response.setSuccess(true);
            response.setMensaje("Usuario, las tareas registradas con el estado '" + estado + "', son:");
            response.setData(null);
        }
        return response;
    }
    /**
     * Se acude al "Service" para poder filtrar las tareas por su "prioridad".
     * @param prioridad = Parámetro que se pasa para su consulta.
     * @return = Lista de tareas con la prioridad dada como parámetro, en formato JSON.
     */
    public HttpGlobalResponse<List<TareaResponseDTO>> filtrarTareasPrioridad(@RequestParam PrioridadTarea prioridad) {
        HttpGlobalResponse<List<TareaResponseDTO>> response = new HttpGlobalResponse<List<TareaResponseDTO>>();
        List<TareaResponseDTO> listaFinal = new ArrayList<TareaResponseDTO>();
        List<Tarea> listaEncontrada = repository.findByPrioridad(prioridad);
        if (listaEncontrada.isEmpty()) {
            response.setSuccess(true);
            response.setMensaje("Usuario, no hay tareas registradas con la prioridad '" + prioridad + "'.");
            response.setData(null);
        } else {
            for (Tarea tarea : listaEncontrada) {
                TareaResponseDTO tareaFinal = new TareaResponseDTO();
                tareaFinal.setId(tarea.getId());
                tareaFinal.setTitulo(tarea.getTitulo());
                tareaFinal.setDescripcion(tarea.getDescripcion());
                tareaFinal.setEstado(tarea.getEstado());
                tareaFinal.setPrioridad(tarea.getPrioridad());
                tareaFinal.setFechaCreacion(tarea.getFechaCreacion());
                tareaFinal.setFechaActualizacion(tarea.getFechaActualizacion());
                listaFinal.add(tareaFinal);
            }
            response.setSuccess(true);
            response.setMensaje("Usuario, las tareas registradas con la prioridad '" + prioridad + "', son:");
            response.setData(null);
        }
        return response;
    }
    /**
     * Aquí, el "Service" se usa para filtrar la tareas por su título.
     * @param titulo = Son las palabras que se pasan como parámetro de la consulta.
     * @return = Retorna los resultados en una lista tipo JSON.
     */
    public HttpGlobalResponse<List<TareaResponseDTO>> buscarTareasTitulo(@RequestParam String titulo) {
        HttpGlobalResponse<List<TareaResponseDTO>> response = new HttpGlobalResponse<List<TareaResponseDTO>>();
        List<TareaResponseDTO> listaFinal = new ArrayList<TareaResponseDTO>();
        List<Tarea> listaEncontrada = repository.findByTitulo(titulo);
        if (listaEncontrada.isEmpty()) {
            response.setSuccess(true);
            response.setMensaje("Usuario, no hay tareas que coincidan con el título: '" + titulo + "'.");
            response.setData(null);
        } else {
            for (Tarea tarea : listaEncontrada) {
                TareaResponseDTO tareaFinal = new TareaResponseDTO();
                tareaFinal.setId(tarea.getId());
                tareaFinal.setTitulo(tarea.getTitulo());
                tareaFinal.setDescripcion(tarea.getDescripcion());
                tareaFinal.setEstado(tarea.getEstado());
                tareaFinal.setPrioridad(tarea.getPrioridad());
                tareaFinal.setFechaCreacion(tarea.getFechaCreacion());
                tareaFinal.setFechaActualizacion(tarea.getFechaActualizacion());
                listaFinal.add(tareaFinal);
            }
            response.setSuccess(true);
            response.setMensaje("Usuario, las tareas que coinciden con el título: '" + titulo + "', son:");
            response.setData(null);
        }
        return response;
    }
    /**
     * Por medio de esta función, el "Service" brinda estadísticas de las tareas almacenadas.
     * @return = Retorna la cantidad de tareas por cada estado y el total de todas ellas.
     */
    @GetMapping("/tareas/estadisticas")
    public HttpGlobalResponse<List<EstadisticasDTO>> mostrarEstadisticas() {
        HttpGlobalResponse<List<EstadisticasDTO>> response = new HttpGlobalResponse<List<EstadisticasDTO>>();
        List<EstadisticasDTO> listaFinal = new ArrayList<EstadisticasDTO>();
        List<Tarea> listaPend = repository.findByEstado(EstadoTarea.PENDIENTE);
        List<Tarea> listaEn_Pro = repository.findByEstado(EstadoTarea.EN_PROGRESO);
        List<Tarea> listaComple = repository.findByEstado(EstadoTarea.COMPLETADA);
        List<Tarea> listaCancel = repository.findByEstado(EstadoTarea.CANCELADA);
        int resultadoTotal = 0;
        int resultadoPend = 0;
        int resultadoEn_pro = 0;
        int resultadoComple = 0;
        int resultadoCancel = 0;
        if (listaPend.isEmpty()) {
            EstadisticasDTO estaPend = new EstadisticasDTO();
            estaPend.setGrupoEstados("PENDIENTE");
            estaPend.setCantidadTareas(0);
            listaFinal.add(estaPend);
        } else {
            EstadisticasDTO estaPend = new EstadisticasDTO();
            estaPend.setGrupoEstados("PENDIENTE");
            for (Tarea tarea : listaPend) {
                resultadoPend++;
            }
            estaPend.setCantidadTareas(resultadoPend);
            listaFinal.add(estaPend);
        }
        if (listaEn_Pro.isEmpty()) {
            EstadisticasDTO estaEn_pro = new EstadisticasDTO();
            estaEn_pro.setGrupoEstados("EN_PROGRESO");
            estaEn_pro.setCantidadTareas(0);
            listaFinal.add(estaEn_pro);
        } else {
            EstadisticasDTO estaEn_pro = new EstadisticasDTO();
            estaEn_pro.setGrupoEstados("EN_PROGRESO");
            for (Tarea tarea : listaEn_Pro) {
                resultadoEn_pro++;
            }
            estaEn_pro.setCantidadTareas(resultadoEn_pro);
            listaFinal.add(estaEn_pro);
        }
        if (listaComple.isEmpty()) {
            EstadisticasDTO estaComple = new EstadisticasDTO();
            estaComple.setGrupoEstados("COMPLETADA");
            estaComple.setCantidadTareas(0);
            listaFinal.add(estaComple);
        } else {
            EstadisticasDTO estaComple = new EstadisticasDTO();
            estaComple.setGrupoEstados("COMPLETADA");
            for (Tarea tarea : listaComple) {
                resultadoComple++;
            }
            estaComple.setCantidadTareas(resultadoComple);
            listaFinal.add(estaComple);
        }
        if (listaCancel.isEmpty()) {
            EstadisticasDTO estaCancel = new EstadisticasDTO();
            estaCancel.setGrupoEstados("CANCELADA");
            estaCancel.setCantidadTareas(0);
            listaFinal.add(estaCancel);
        } else {
            EstadisticasDTO estaCancel = new EstadisticasDTO();
            estaCancel.setGrupoEstados("CANCELADA");
            for (Tarea tarea : listaCancel) {
                resultadoCancel++;
            }
            estaCancel.setCantidadTareas(resultadoCancel);
            listaFinal.add(estaCancel);
        }
        
        resultadoTotal = listaPend.size() + listaEn_Pro.size() + listaComple.size() + listaCancel.size();
        response.setSuccess(true);
        response.setMensaje("Usuario, en total hay " + resultadoTotal + " tareas registradas.");
        response.setData(listaFinal);
        return response;
    }
}
