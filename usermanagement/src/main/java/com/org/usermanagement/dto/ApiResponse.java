package com.org.usermanagement.dto;


public class ApiResponse<T> {
    private Boolean success;
    private String message;
    private T response;

    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message; 
    }
    
    public ApiResponse(Boolean success,T response, String message) {
        this.success = success;
        this.message = message;
        this.response = response;
    }

    public T getResponse() {
		return response;
	}

	public void setResponse(T response) {
		this.response = response;
	}

	public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
