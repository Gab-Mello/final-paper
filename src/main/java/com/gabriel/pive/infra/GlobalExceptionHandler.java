package com.gabriel.pive.infra;

import com.gabriel.pive.animals.entities.DonorCattle;
import com.gabriel.pive.animals.exceptions.BullNotFoundException;
import com.gabriel.pive.animals.exceptions.DonorCattleNotFoundException;
import com.gabriel.pive.animals.exceptions.ReceiverCattleNotFoundException;
import com.gabriel.pive.animals.exceptions.RegistrationNumberAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RegistrationNumberAlreadyExistsException.class)
    private ResponseEntity<String> registrationNumberAlreadyExists(RegistrationNumberAlreadyExistsException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler(ReceiverCattleNotFoundException.class)
    private ResponseEntity<String> receiverCattleNotFound(ReceiverCattleNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(BullNotFoundException.class)
    private ResponseEntity<String> bullNotFound(BullNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(DonorCattleNotFoundException.class)
    private ResponseEntity<String> donorNotFound(DonorCattleNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}