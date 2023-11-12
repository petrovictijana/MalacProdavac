package server.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import server.server.dtos.response.PibAlreadyExistsResponse;
import server.server.errors.ErrorResponse;
import server.server.exceptions.*;

import java.nio.file.NoSuchFileException;

@ControllerAdvice
public class CustomErrorController {
    @ExceptionHandler(EmailUsernameAlreadyTakenException.class)
    public ResponseEntity<ErrorResponse> handleUsernameAlreadyTakenException(Exception e){
        EmailUsernameAlreadyTakenException emailUsernameAlreadyTakenException = (EmailUsernameAlreadyTakenException) e;
        HttpStatus status = HttpStatus.CONFLICT;

        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .code(status.value())
                        .status(status.name())
                        .message(e.getMessage())
                        .data(emailUsernameAlreadyTakenException.getResponse()).build(), status
        );
    }

    @ExceptionHandler(PibAlreadyTakenException.class)
    public ResponseEntity<ErrorResponse> handlePibAlreadyTakenException(Exception e){
        HttpStatus status = HttpStatus.CONFLICT;

        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .code(status.value())
                        .status(status.name())
                        .message(e.getMessage())
                        .data(new PibAlreadyExistsResponse(true))
                        .build(), status
        );
    }

    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRoleException(Exception e){
        HttpStatus status = HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .code(status.value())
                        .status(status.name())
                        .message(e.getMessage())
                        .data(null)
                        .build(), status
        );
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(Exception e){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .code(status.value())
                        .status(status.name())
                        .message(e.getMessage())
                        .data(null)
                        .build(), status
        );
    }


    @ExceptionHandler(InvalidLoginCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidLoginCredentialsException(Exception e){
        InvalidLoginCredentialsException invalidLoginCredentialsException = (InvalidLoginCredentialsException) e;
        HttpStatus status = HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .code(status.value())
                        .status(status.name())
                        .message(e.getMessage())
                        .data(invalidLoginCredentialsException.getLoginFailedResponse())
                        .build(), status
        );
    }

    @ExceptionHandler(InvalidProductIdException.class)
    public ResponseEntity<ErrorResponse> handleInvalidProductIdException(Exception e){
        InvalidProductIdException invalidProductIdException = (InvalidProductIdException) e;
        HttpStatus status = HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>( ErrorResponse.builder()
                .code(status.value())
                .status(status.name())
                .message(e.getMessage())
                .data(null)
                .build(), status);
    }

    @ExceptionHandler(InvalidSellerIdException.class)
    public ResponseEntity<ErrorResponse> handleInvalidSellerIdException(Exception e){
        InvalidSellerIdException invalidSellerIdException = (InvalidSellerIdException) e;
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>( ErrorResponse.builder()
                .code(status.value())
                .status(status.name())
                .message(e.getMessage())
                .data(null)
                .build(), status);
    }

    @ExceptionHandler(InvalidSearchException.class)
    public ResponseEntity<ErrorResponse> handleInvalidSearchException(Exception e){
        InvalidSearchException invalidSearchException = (InvalidSearchException) e;
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>( ErrorResponse.builder()
                .code(status.value())
                .status(status.name())
                .message(e.getMessage())
                .data(null)
                .build(), status);
    }

    @ExceptionHandler(NoSuchFileException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchFileException(Exception e){
        HttpStatus status = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(ErrorResponse.builder()
                .code(status.value())
                .status(status.name())
                .message("Ne postoji fajl za dati username")
                .data(null)
                .build(), status);
    }
}
