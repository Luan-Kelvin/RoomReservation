package com.Projeto_Alura.RoomReservation.DTOs.User;

public record UserGetDTO(
        Long id,
        String name,
        Integer age,
        String email
) {
}
