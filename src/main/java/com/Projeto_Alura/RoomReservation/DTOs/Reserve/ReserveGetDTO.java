package com.Projeto_Alura.RoomReservation.DTOs.Reserve;

import com.Projeto_Alura.RoomReservation.ENUM.StatusReserve;

import java.time.LocalDate;

public record ReserveGetDTO(
        Long id,
        Integer number,
        LocalDate startReservation,
        LocalDate edReservation,
        StatusReserve status,
        Long idRoom,
        Long idUser
) {
}
