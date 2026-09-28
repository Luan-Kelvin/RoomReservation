package com.Projeto_Alura.RoomReservation.Exceptions.Reserve;

public class TheRoomAlreadyReservedException extends RuntimeException {
    public TheRoomAlreadyReservedException(String message) {
        super(message);
    }
}
