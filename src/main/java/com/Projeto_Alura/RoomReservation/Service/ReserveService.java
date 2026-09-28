package com.Projeto_Alura.RoomReservation.Service;

import com.Projeto_Alura.RoomReservation.Conversor.Conversor;
import com.Projeto_Alura.RoomReservation.DTOs.Reserve.ReserveGetDTO;
import com.Projeto_Alura.RoomReservation.DTOs.Reserve.ReservePostDTO;
import com.Projeto_Alura.RoomReservation.Domain.Reserve;
import com.Projeto_Alura.RoomReservation.Domain.Room;
import com.Projeto_Alura.RoomReservation.Domain.User;
import com.Projeto_Alura.RoomReservation.ENUM.StatusReserve;
import com.Projeto_Alura.RoomReservation.Exceptions.Reserve.ReserveNotFoundException;
import com.Projeto_Alura.RoomReservation.Exceptions.Reserve.TheRoomAlreadyReservedException;
import com.Projeto_Alura.RoomReservation.Repository.ReserveRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReserveService {
    private final Logger logger = LoggerFactory.getLogger(ReserveService.class);

    private final Conversor conversor;
    private final ReserveRepository reserveRepository;

    private final UserService userService;
    private final RoomService roomService;

    @Transactional
    public ReserveGetDTO createReserve(ReservePostDTO dto){
        User user = userService.findById(dto.idUser());
        Room room = roomService.findById(dto.idRoom());

        if (room.getReserve() != null){
            throw new TheRoomAlreadyReservedException("ERRO! Sala já está reservada.");
        }

        Integer number = reserveRepository.generateNewReserveNumber();

        Reserve reserve = new Reserve(number, room, user, dto.startReservation(), dto.endReservation());
        user.addReserve(reserve);
        room.addReserve(reserve);

        userService.save(user);
        roomService.save(room);
        reserveRepository.save(reserve);

        logger.info("Nova reserva feita com sucesso!");
        return conversor.converterReserve(reserve);
    }

    @Transactional
    private void deleteReserve(Integer number){
        Reserve reserve = reserveRepository.findByNumber(number)
                .orElseThrow(() -> new ReserveNotFoundException(String.format("ERRO! Reserva com Nº %s não foi encontrada.", number)));

        User user = userService.findById(reserve.getUser().getId());
        Room room = roomService.findById(reserve.getRoom().getId());

        user.removeReserve(reserve);
        room.removeReserve();
        reserveRepository.delete(reserve);

        userService.save(user);
        roomService.save(room);
    }

    @Transactional
    public ReserveGetDTO findByNumber(Integer number){
        Reserve reserve = reserveRepository.findByNumber(number)
                .orElseThrow(() -> new ReserveNotFoundException(String.format("ERRO! Reserva com Nº %s não foi encontrada.", number)));

        return conversor.converterReserve(reserve);
    }

    @Transactional
    public List<ReserveGetDTO> findByStartReservation(LocalDate date){
        List<Reserve> reserves = reserveRepository.findByStartReservation(date);

        if (reserves.isEmpty()){
            return List.of();
        }

        return reserves.stream().map(conversor::converterReserve).toList();
    }

    @Transactional
    public List<ReserveGetDTO> findByStatus(StatusReserve status){
        List<Reserve> reserves = reserveRepository.findByStatus(status);

        if (reserves.isEmpty()){
            return List.of();
        }

        return reserves.stream().map(conversor::converterReserve).toList();
    }
}
