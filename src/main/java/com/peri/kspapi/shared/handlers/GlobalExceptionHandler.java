package com.peri.kspapi.shared.handlers;

import com.peri.kspapi.shared.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse error = new ErrorResponse(ex.getMessage(), 500);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }
    
    /*
    @ExceptionHandler(ConexaoKRPCException.class)
    public ResponseEntity<ErrorResponse> handleKrpcError(ConexaoKRPCException ex) {
        ErrorResponse error = new ErrorResponse(ex.getMessage(), 503);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }
    */
}
