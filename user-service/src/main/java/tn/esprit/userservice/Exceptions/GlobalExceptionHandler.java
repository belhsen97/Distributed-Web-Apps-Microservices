package tn.esprit.userservice.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import tn.esprit.userservice.Dtos.MsgReponseStatusDto;
import tn.esprit.userservice.Dtos.ReponseStatus;

import java.time.LocalDate;
import java.time.LocalTime;

//@ControllerAdvice
public class GlobalExceptionHandler {
/*
    // handling specific exception
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<?> resourceNotValidating(MethodArgumentNotValidException exception, WebRequest request){
//
//        ErrorDetails errorDetails = new ErrorDetails(
//                LocalDate.now(),
//                LocalTime.now(),
//                exception.getMessage() ,
//                request.getDescription(false));
//        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
//    }
    //https://salithachathuranga94.medium.com/validation-and-exception-handling-in-spring-boot-51597b580ffd
    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(org.springframework.web.bind.MethodArgumentNotValidException ex, WebRequest request) {
        StringBuilder errorMessage = new StringBuilder("Validation failed: ");
        ex.getBindingResult().getAllErrors().forEach(error -> {
            errorMessage.append(error.getDefaultMessage()).append("; ");
        });
        //return ResponseEntity.badRequest().body(errorMessage.toString());
        MsgReponseStatusDto errorDetails = MsgReponseStatusDto.builder()
                                                              .title("Validation")
                                                              .status(ReponseStatus.ERROR)
                                                              .datestamp(LocalDate.now())
                                                              .timestamp(LocalTime.now())
                                                              .message(errorMessage.toString())
                                                              //.details(request.getDescription(false))
                                                              .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    // handling specific exception
    @ExceptionHandler(RessourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundHandling(RessourceNotFoundException exception, WebRequest request){
        MsgReponseStatusDto errorDetails = MsgReponseStatusDto.builder()
                .title("Not Found")
                .status(ReponseStatus.ERROR)
                .datestamp(LocalDate.now())
                .timestamp(LocalTime.now())
                .message(exception.getMessage())
                //.details(request.getDescription(false))
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    //an I/O error occurs accessing information using streams, files and directories.
    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> ExceptionAccessingInformationFilesAndDirectories(IOException exception, WebRequest request){
        MsgReponseStatusDto errorDetails = MsgReponseStatusDto.builder()
                .title("Accessing Information Files And Directories")
                .status(ReponseStatus.ERROR)
                .datestamp(LocalDate.now())
                .timestamp(LocalTime.now())
                .message(exception.getMessage())
                //.details(request.getDescription(false))
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    // handling global exception

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandling(Exception exception, WebRequest request){
        MsgReponseStatusDto errorDetails = MsgReponseStatusDto.builder()
                .title("Exception")
                .status(ReponseStatus.ERROR)
                .datestamp(LocalDate.now())
                .timestamp(LocalTime.now())
                .message(exception.getMessage())
                //.details(request.getDescription(false))
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }*/
}