package com.Projeto_Alura.RoomReservation.Infra;

import com.Projeto_Alura.RoomReservation.DTOs.ErroRequest;
import com.Projeto_Alura.RoomReservation.Exceptions.InvalidReservationsDateException;
import com.Projeto_Alura.RoomReservation.Exceptions.MaximumNumberOfReservationsException;
import com.Projeto_Alura.RoomReservation.Exceptions.Reserve.ReserveNotFoundException;
import com.Projeto_Alura.RoomReservation.Exceptions.Reserve.TheRoomAlreadyReservedException;
import com.Projeto_Alura.RoomReservation.Exceptions.Room.RoomNotFoundException;
import com.Projeto_Alura.RoomReservation.Exceptions.Room.StatusInvalidException;
import com.Projeto_Alura.RoomReservation.Exceptions.User.UserAlreadyExistsException;
import com.Projeto_Alura.RoomReservation.Exceptions.User.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class HandlerException {

    @ExceptionHandler(ReserveNotFoundException.class)
    public ResponseEntity<ErroRequest> ReserveNotFound(ReserveNotFoundException e, HttpServletRequest request){

        ErroRequest erro = new ErroRequest(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(TheRoomAlreadyReservedException.class)
    public ResponseEntity<ErroRequest> RoomAlreadyExists(TheRoomAlreadyReservedException e, HttpServletRequest request){

        ErroRequest erro = new ErroRequest(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<ErroRequest> roomNotFound(RoomNotFoundException e, HttpServletRequest request){

        ErroRequest erro = new ErroRequest(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(StatusInvalidException.class)
    public ResponseEntity<ErroRequest> StatusInvalid(StatusInvalidException e, HttpServletRequest request){

        ErroRequest erro = new ErroRequest(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErroRequest> UserAlreadyExists(UserAlreadyExistsException e, HttpServletRequest request){

        ErroRequest erro = new ErroRequest(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErroRequest> UserNotFound(UserNotFoundException e, HttpServletRequest request){

        ErroRequest erro = new ErroRequest(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(InvalidReservationsDateException.class)
    public ResponseEntity<ErroRequest> InvalidReservationDate(InvalidReservationsDateException e, HttpServletRequest request){

        ErroRequest erro = new ErroRequest(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(MaximumNumberOfReservationsException.class)
    public ResponseEntity<ErroRequest> InvalidReservationDate(MaximumNumberOfReservationsException e, HttpServletRequest request){

        ErroRequest erro = new ErroRequest(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }
}
