package com.JAFCL.Tasks.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.JAFCL.Tasks.dto.HttpGlobalResponse;
import com.JAFCL.Tasks.dto.TareaResponseDTO;
import com.JAFCL.Tasks.repository.TareasRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TareasService {
    
    private final TareasRepository repository;

    public HttpGlobalResponse<List<TareaResponseDTO>> listarTareas() {
        HttpGlobalResponse<List<TareaResponseDTO>> response = new HttpGlobalResponse<List<TareaResponseDTO>>();
        
        return response;
    }
}
