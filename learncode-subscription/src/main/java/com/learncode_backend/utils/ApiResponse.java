package com.learncode_backend.utils;

//import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
	private boolean success;
	//private LocalDateTime fecha;
	private String mensaje;
	private T data;

	
	public static <T> ApiResponse<T> ok(T data, String mensaje) {
        return new ApiResponse<>(true, mensaje, data);
    }

    public static <T> ApiResponse<T> fail(String mensaje) {
        return new ApiResponse<>(false, mensaje, null);
    }
	
}
