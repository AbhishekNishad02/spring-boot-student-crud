package com.akn62.crud_basic.Exception;

import com.akn62.crud_basic.dto.ExceptionResponseDto;
import com.akn62.crud_basic.dto.ValidationExceptionResponseDto;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GEHandler {
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ExceptionResponseDto> HandlerRunExceptionDuplicate(DuplicateResourceException ex,
                                                                             HttpServletRequest request){
        ExceptionResponseDto exceptionResponseDto=new ExceptionResponseDto(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(exceptionResponseDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionResponseDto> MethodArgNotvalidException( MethodArgumentNotValidException ex,
                                                                            HttpServletRequest request){
        Map<String ,String > feilderrors=new HashMap<>();
        ex.getBindingResult().getFieldErrors().
        forEach(error ->feilderrors.put(error.getField(), error.getDefaultMessage()));
    ValidationExceptionResponseDto vaildexceptionResponseDto=new ValidationExceptionResponseDto(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Validation is Failed please full fill all required field And check all vaild point",
                request.getRequestURI(),
            feilderrors

        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(vaildexceptionResponseDto);
    }

    @ExceptionHandler(ResorcesNotFound.class)
    public ResponseEntity<ExceptionResponseDto> HandlerRunExceptionNotFound(ResorcesNotFound ex,
                                                                            HttpServletRequest request){
        ExceptionResponseDto exceptionResponseDto=new ExceptionResponseDto(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(exceptionResponseDto);
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponseDto> HandlerRunException(RuntimeException ex,
                                                                    HttpServletRequest request){
        ExceptionResponseDto exceptionResponseDto=new ExceptionResponseDto(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(exceptionResponseDto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDto> HandlerGenException(Exception ex,
                                                                    HttpServletRequest request){
        ExceptionResponseDto exceptionResponseDto=new ExceptionResponseDto(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exceptionResponseDto);
    }
}
