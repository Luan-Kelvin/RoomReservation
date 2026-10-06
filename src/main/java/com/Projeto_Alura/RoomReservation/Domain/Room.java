package com.Projeto_Alura.RoomReservation.Domain;

import com.Projeto_Alura.RoomReservation.ENUM.StatusRoom;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "roons")
@Getter
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private Integer number;

    private LocalDate creationDate;

    @Enumerated(EnumType.STRING)
    private StatusRoom status;

    @OneToOne
    private Reserve reserve;

    public Room(Integer number) {
        this.number = number;
        this.creationDate = LocalDate.now();
        this.status = StatusRoom.AVAILABLE;
    }

    public void setStatusRoom(StatusRoom status){
        this.status = status;
    }

    public void addReserve(Reserve reserve){
        if (reserve != null){
            this.reserve = reserve;
        }
    }

    public void removeReserve(){
        this.reserve = null;
    }
}
