package com.Projeto_Alura.RoomReservation.Domain;

import com.Projeto_Alura.RoomReservation.Exceptions.MaximumNumberOfReservationsException;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Column(unique = true)
    @NotBlank
    @Pattern(regexp = "^[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}-[0-9]{2}$", message = "ERRO! CPF inválido para cadastro.")
    private String cpf;

    @NotNull
    @Min(18)
    private Integer age;

    @NotBlank
    @Email(message = "ERRO! Formato de email inválido para cadastro.")
    private String email;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Reserve> reserves = new ArrayList<>();

    public User(String name, String cpf, Integer age, String email) {
        this.name = name;
        this.cpf = cpf;
        this.age = age;
        this.email = email;
    }

    // ADICIONAR RESERVA
    public void addReserve(Reserve reserve){
        if (reserves.size() == 3){
            throw new MaximumNumberOfReservationsException("ERRO! Número maximo de reservas atingido.");
        }

        reserves.add(reserve);
    }

    // REMOVER RESERVA
    public void removeReserve(Reserve reserve){
        if (reserves.contains(reserve)) {
            reserves.remove(reserve);
        }
    }
}
