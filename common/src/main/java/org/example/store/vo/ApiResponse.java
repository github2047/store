package org.example.store.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ApiResponse<T> {
    private int code=0;
    private String message;
    private T data;
}