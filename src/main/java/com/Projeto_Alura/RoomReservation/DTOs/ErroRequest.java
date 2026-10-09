package com.Projeto_Alura.RoomReservation.DTOs;

import java.time.LocalDateTime;

public record ErroRequest(
        LocalDateTime timesTamp,
        Integer statusCode,
        String message,
        String path
) {
}
