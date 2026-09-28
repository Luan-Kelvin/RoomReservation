package com.Projeto_Alura.RoomReservation.DTOs.Reserve;

import com.Projeto_Alura.RoomReservation.ENUM.StatusReserve;

import java.time.LocalDate;

public record ReservePostDTO(
        Long idRoom,
        Long idUser,
        StatusReserve statusReserve,
        LocalDate startReservation,
        LocalDate endReservation
) {
}
