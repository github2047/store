package org.example.store.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.example.store.util.Timestamp;

import java.text.SimpleDateFormat;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private Integer code;
    private String message;
    private Object data;
    private String timestamp;

    private static final SimpleDateFormat df = new SimpleDateFormat("");

    public static ApiResponse Success() {
        return Success(null);
    }

    public static ApiResponse Success(Object data) {
        return new ApiResponse(0, "success", data, Timestamp.now());
    }

    public static ApiResponse Error(String message) {
        return Error(1, message);
    }

    public static ApiResponse Error(Integer code, String message) {
        return new ApiResponse(code, message, null, Timestamp.now());
    }

    public static ApiResponse Error(Integer code, String message, Object data) {
        return new ApiResponse(code, message, data, Timestamp.now());
    }
}
