package br.transversa.backend.controller.error;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        //logger.info(ex.getClass().getName());
        //
        //final List<String> errors = new ArrayList<String>();
        final Map<String, String> errors2 = new HashMap<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            //errors.add(error.getField() + ": " + error.getDefaultMessage());
            errors2.put(error.getField(), error.getDefaultMessage());
        }
//        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
//            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
//        }
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, errors2);


        return new ResponseEntity<Object>(
        	      apiError, new HttpHeaders(), apiError.getStatus());
        		//handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }
}