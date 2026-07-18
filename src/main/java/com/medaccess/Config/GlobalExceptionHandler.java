package com.medaccess.Config;

import com.medaccess.Exception.*;
import com.medaccess.Exception.OrderException.EmptyCartException;
import com.medaccess.Exception.OrderException.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiError> handleUsernameNotfoundException(UsernameNotFoundException exception){
        ApiError apiError=new ApiError("username not found with username"+ exception.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiError,apiError.getStatus());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError>handleResourceNotFoundException(ResourceNotFoundException exception){
        ApiError apiError=new ApiError("Resource not found"+exception.getMessage(),HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiError,apiError.getStatus());
    }

    @ExceptionHandler(CartItemNotFoundException.class)
    public ResponseEntity<ApiError>handleCartItemNotFoundException(CartItemNotFoundException exception){
        ApiError apiError=new ApiError("Resource not found"+exception.getMessage(),HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiError,apiError.getStatus());
    }
    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ApiError>handleCartNotFoundException(CartNotFoundException exception){
        ApiError apiError=new ApiError("Resource not found"+exception.getMessage(),HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiError,apiError.getStatus());
    }
    @ExceptionHandler(InvalidQuantityException.class)
    public ResponseEntity<ApiError>handleInvalidQuantityException(InvalidQuantityException exception){
        ApiError apiError=new ApiError("Resource not found"+exception.getMessage(),HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiError,apiError.getStatus());
    }
    @ExceptionHandler(EmptyCartException.class)
    public ResponseEntity<ApiError>handleEmptyCartException(EmptyCartException exception){
        ApiError apiError=new ApiError("Resource not found"+exception.getMessage(),HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiError,apiError.getStatus());
    }
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ApiError>handleOrderNotFoundException(OrderNotFoundException exception){
        ApiError apiError=new ApiError("Resource not found"+exception.getMessage(),HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiError,apiError.getStatus());
    }


    @ExceptionHandler(MedicineNotFoundException.class)
    public ResponseEntity<ApiError>handleMedicineNotFoundException(MedicineNotFoundException exception){
        ApiError apiError=new ApiError("Resource not found"+exception.getMessage(),HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiError,apiError.getStatus());
    }

    @ExceptionHandler(InvalidFileException.class)
    public ResponseEntity<ApiError>handleInvalidFileException(InvalidFileException exception){
        ApiError apiError=new ApiError("Resource not found"+exception.getMessage(),HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiError,apiError.getStatus());
    }
}
