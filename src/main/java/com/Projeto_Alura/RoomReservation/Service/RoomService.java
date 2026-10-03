package com.Projeto_Alura.RoomReservation.Service;

import com.Projeto_Alura.RoomReservation.Conversor.Conversor;
import com.Projeto_Alura.RoomReservation.DTOs.Room.RoomGetDTO;
import com.Projeto_Alura.RoomReservation.Domain.Room;
import com.Projeto_Alura.RoomReservation.ENUM.StatusRoom;
import com.Projeto_Alura.RoomReservation.Exceptions.Room.RoomNotFoundException;
import com.Projeto_Alura.RoomReservation.Exceptions.Room.StatusInvalidException;
import com.Projeto_Alura.RoomReservation.Repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final Logger logger = LoggerFactory.getLogger(RoomService.class);

    private final RoomRepository roomRepository;
    private final Conversor conversor;

    @Transactional
    public RoomGetDTO createRoom(){
        Integer number = roomRepository.generateNewRoomNumber();

        Room room = new Room(number);
        roomRepository.save(room);

        return conversor.converterRoom(room);
    }

    @Transactional
    public void deleteRoom(Integer number){
        Room room = roomRepository.findByNumber(number)
                .orElseThrow(() -> new RoomNotFoundException(String.format("ERRO! Sala com Nº %s não foi encontrada.", number)));

        roomRepository.delete(room);
        logger.info(String.format("Sala com Nº %s foi deletada com sucesso!", number ));
    }

    public Room findById(Long id){
       return roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(String.format("ERRO! Sala com ID %s não foi encontrada.", id)));

    }

    public RoomGetDTO findByIdController(Long id){
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(String.format("ERRO! Sala com ID %s não foi encontrada.", id)));

        return conversor.converterRoom(room);
    }

    @Transactional
    public List<RoomGetDTO> listRoons(){
        List<Room> roons = roomRepository.findAll();

        if (roons.isEmpty()){
            return List.of();
        }

        return roons.stream().map(conversor::converterRoom).toList();
    }

    @Transactional
    public RoomGetDTO findByNumber(Integer number){
        Room room = roomRepository.findByNumber(number)
                .orElseThrow(() -> new RoomNotFoundException(String.format("ERRO! Sala com Nº %s não foi encontrada.", number)));

        return conversor.converterRoom(room);
    }

    @Transactional
    public List<RoomGetDTO> findByCreationDate(LocalDate date){
        List<Room> roons = roomRepository.findByCreationDate(date);

        if (roons.isEmpty()){
            return List.of();
        }

        return roons.stream().map(conversor::converterRoom).toList();
    }

    @Transactional
    public List<RoomGetDTO> findByCreationDateBetween(LocalDate startDate, LocalDate endDate){
        List<Room> roons = roomRepository.findByCreationDateBetween(startDate, endDate);

        if (roons.isEmpty()){
            return List.of();
        }

        return roons.stream().map(conversor::converterRoom).toList();
    }

    @Transactional
    public List<RoomGetDTO> findByStatus(String s){
        StatusRoom status = null;

        if (s.equalsIgnoreCase("AVAILABLE")){
            status = StatusRoom.AVAILABLE;
        } else if (s.equalsIgnoreCase("RESERVED")){
            status = StatusRoom.RESERVED;
        } else if (s.equalsIgnoreCase("UNAVAILABLE")){
            status = StatusRoom.UNAVAILABLE;
        }else {
            throw new StatusInvalidException("ERRO! Status digitado é inválido.");
        }

        List<Room> roons = roomRepository.findByStatus(status);

        if (roons.isEmpty()){
            return List.of();
        }

        return roons.stream().map(conversor::converterRoom).toList();
    }

    public void save(Room room){
        roomRepository.save(room);
    }

}
