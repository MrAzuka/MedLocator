package com.mrazuka.medlocator.Utils;

public class ApiResponse<T> {
    private String message;
    private T data; // The actual data, e.g., DrugCreateDTO
    private boolean success; // Optional: indicate success/failure

    public ApiResponse(String message, T data, boolean success) {
        this.message = message;
        this.data = data;
        this.success = success;
    }
    public ApiResponse(){}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    // You can add convenience constructors or static methods
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(message, data, true);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(message, null, false);
    }
}
