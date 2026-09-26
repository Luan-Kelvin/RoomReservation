package com.Projeto_Alura.RoomReservation.Exceptions;

public class InvalidReservationsDateException extends RuntimeException {
    public InvalidReservationsDateException(String message) {
        super(message);
    }
}
