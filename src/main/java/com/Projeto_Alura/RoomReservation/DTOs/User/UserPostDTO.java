package com.Projeto_Alura.RoomReservation.DTOs.User;

import jakarta.validation.constraints.*;

public record UserPostDTO(
        @NotBlank
        String name,

        @NotNull
        @Min(18)
        Integer age,

        @NotBlank
        @Email(message = "ERRO! Insira um email com formato válido.")
        String email,

        @Pattern(regexp = "[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}-[0-9]{2}", message = "ERRO! insira um cpf com formato válido EX: (XXX.XXX.XXX-XX)")
        String cpf
) {
}
