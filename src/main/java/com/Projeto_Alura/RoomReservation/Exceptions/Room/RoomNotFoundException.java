package com.Projeto_Alura.RoomReservation.Exceptions.Room;

public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException(String message) {
        super(message);
    }
}
