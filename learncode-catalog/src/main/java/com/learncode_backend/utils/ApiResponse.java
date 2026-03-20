package com.learncode_backend.utils;

public class ApiResponse<T> {
    private boolean success;
    private String mensaje;
    private T data;

    public ApiResponse() {}

    public ApiResponse(boolean success, String mensaje, T data) {
        this.success = success;
        this.mensaje = mensaje;
        this.data = data;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
}