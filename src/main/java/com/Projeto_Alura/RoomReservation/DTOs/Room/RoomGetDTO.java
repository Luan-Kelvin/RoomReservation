package com.Projeto_Alura.RoomReservation.DTOs.Room;

import com.Projeto_Alura.RoomReservation.ENUM.StatusRoom;

import java.time.LocalDate;

public record RoomGetDTO(
        Long id,
        Integer number,
        LocalDate creationDate,
        StatusRoom status
) {
}
