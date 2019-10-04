package br.transversa.backend.controller.error;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;

public class ApiError {

    private HttpStatus status;
    //private String message;
    private Map<String, String> errors;

    //

    public ApiError() {
        super();
    }

//    public ApiError(final HttpStatus status, final String message, final Map<String, String> errors) {
//        super();
//        this.status = status;
//        //this.message = message;
//        this.errors = errors;
//    }

//    public ApiError(final HttpStatus status, final String message, final Map<String, String> errors) {
//        super();
//        this.status = status;
//        //this.message = message;
//        errors = Arrays.asList(error);
//    }

    //
    
    

    public HttpStatus getStatus() {
        return status;
    }

    public ApiError(HttpStatus status, Map<String, String> errors) {
		super();
		this.status = status;
		this.errors = errors;
	}

	public void setStatus(final HttpStatus status) {
        this.status = status;
    }

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}

//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(final String message) {
//        this.message = message;
//    }

    
    
//    public List<String> getErrors() {
//        return errors;
//    }
//
//    public void setErrors(final List<String> errors) {
//        this.errors = errors;
//    }
//
//    public void setError(final String error) {
//        errors = Arrays.asList(error);
//    }

}