package com.Projeto_Alura.RoomReservation.Domain;

import com.Projeto_Alura.RoomReservation.ENUM.StatusReserve;
import com.Projeto_Alura.RoomReservation.Exceptions.InvalidReservationsDateException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "reserves")
@Getter
@NoArgsConstructor
public class Reserve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private Integer number;

    private LocalDate startReservation;

    private LocalDate endReservation;

    @Enumerated(EnumType.STRING)
    private StatusReserve status;

    @OneToOne
    private Room room;

    @ManyToOne
    @JoinColumn(name = "id_User")
    private User user;

    public Reserve(Integer number, Room room, User user, LocalDate startReservation, LocalDate endReservation) {
        this.number = number;
        this.room = room;
        this.user = user;
        this.status = StatusReserve.ACTIVE;

        verifiyStartReservation(startReservation);
        this.startReservation = startReservation;

        verifiyEndReservation(endReservation);
        this.endReservation = endReservation;
    }

    private void verifiyStartReservation(LocalDate startReservation){
        LocalDate today = LocalDate.now();
        LocalDate limit = today.plusYears(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (startReservation.isBefore(today)){
            throw new InvalidReservationsDateException("ERRO! A data de início da reserva deve ser após a data atual.");
        }

        if (startReservation.isAfter(limit)){
            throw new InvalidReservationsDateException(
                    String.format("ERRO! O limite para reserv de sala é ate a data: ", limit.format(formatter)
            ));
        }
    }

    private void verifiyEndReservation(LocalDate endReservation){
        LocalDate limit = this.startReservation.plusMonths(1);

        if (endReservation.isBefore(this.startReservation)){
            throw new InvalidReservationsDateException("ERRO! A data de encerramento de reserva deve ser após a data de início de reserva.");
        }

        if (endReservation.isAfter(limit)){
            throw new InvalidReservationsDateException("ERRO! A data de encerramento deve ser no máximo 1 mês após a data de início.");
        }
    }
}
