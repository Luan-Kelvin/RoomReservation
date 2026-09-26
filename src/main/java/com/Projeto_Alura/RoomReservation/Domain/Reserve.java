package com.Projeto_Alura.RoomReservation.Domain;

import com.Projeto_Alura.RoomReservation.ENUM.StatusReserve;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Enumerated(EnumType.STRING)
    private StatusReserve status;

    @OneToOne
    private Room room;

    @ManyToOne
    @JoinColumn(name = "id_User")
    private User user;

    public Reserve(Integer number, Room room, User user) {
        this.number = number;
        this.room = room;
        this.user = user;
        this.status = StatusReserve.ACTIVE;
    }
}
