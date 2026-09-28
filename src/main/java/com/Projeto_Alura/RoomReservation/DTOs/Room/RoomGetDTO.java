package com.Projeto_Alura.RoomReservation.DTOs.Room;

import com.Projeto_Alura.RoomReservation.ENUM.StatusRoom;

import java.time.LocalDate;

public record RoomGetDTO(
        Integer number,
        LocalDate creationDate,
        StatusRoom status
) {
}
