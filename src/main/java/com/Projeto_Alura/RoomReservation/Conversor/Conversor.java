package com.Projeto_Alura.RoomReservation.Conversor;

import com.Projeto_Alura.RoomReservation.DTOs.Reserve.ReserveGetDTO;
import com.Projeto_Alura.RoomReservation.DTOs.Room.RoomGetDTO;
import com.Projeto_Alura.RoomReservation.DTOs.User.UserGetDTO;
import com.Projeto_Alura.RoomReservation.Domain.Reserve;
import com.Projeto_Alura.RoomReservation.Domain.Room;
import com.Projeto_Alura.RoomReservation.Domain.User;
import org.springframework.stereotype.Service;

@Service
public class Conversor {

    public RoomGetDTO converterRoom(Room room){
        return new RoomGetDTO(
                room.getNumber(),
                room.getCreationDate(),
                room.getStatus()
        );
    }

    public UserGetDTO converterUser(User user){
        return new UserGetDTO(
                user.getId(),
                user.getName(),
                user.getAge(),
                user.getEmail()
        );
    }

    public ReserveGetDTO converterReserve(Reserve reserve){
        return new ReserveGetDTO(
            reserve.getNumber(),
            reserve.getStartReservation(),
            reserve.getEndReservation(),
            reserve.getStatus(),
            reserve.getRoom().getId(),
            reserve.getUser().getId()
        );
    }
}
