package com.JAFCL.Tasks.dto;

import lombok.Data;

@Data
public class HttpGlobalResponse<T> {
    
    private boolean success;

    private String mensaje;

    private T data;
}
