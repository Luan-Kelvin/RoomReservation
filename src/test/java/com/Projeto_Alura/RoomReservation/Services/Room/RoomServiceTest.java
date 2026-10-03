package com.Projeto_Alura.RoomReservation.Services.Room;

import com.Projeto_Alura.RoomReservation.Conversor.Conversor;
import com.Projeto_Alura.RoomReservation.DTOs.Room.RoomGetDTO;
import com.Projeto_Alura.RoomReservation.Domain.Room;
import com.Projeto_Alura.RoomReservation.ENUM.StatusRoom;
import com.Projeto_Alura.RoomReservation.Exceptions.Room.RoomNotFoundException;
import com.Projeto_Alura.RoomReservation.Repository.RoomRepository;
import com.Projeto_Alura.RoomReservation.Service.RoomService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private Conversor conversor;

    @InjectMocks
    private RoomService roomService;

    @Test
    @DisplayName("Deve criar uma nova Room e retorna-la como DTO.")
    void deveCriarNovaRoom(){
        Integer number = 1001;
        RoomGetDTO dto = new RoomGetDTO(1L, 1001, LocalDate.now(), StatusRoom.AVAILABLE);

        when(roomRepository.generateNewRoomNumber()).thenReturn(number);
        when(conversor.converterRoom(any(Room.class))).thenReturn(dto);

        ArgumentCaptor<Room> captor = ArgumentCaptor.forClass(Room.class);

        RoomGetDTO roomGetDTO = roomService.createRoom();

        verify(roomRepository).save(captor.capture());

        Room room = captor.getValue();

        assertEquals(number, room.getNumber());
        assertEquals(roomGetDTO.creationDate(), room.getCreationDate());
        assertEquals(roomGetDTO.status(), room.getStatus());

        verify(roomRepository).generateNewRoomNumber();
        verify(conversor).converterRoom(any(Room.class));
    }

    @Test
    @DisplayName("Deve deletar a room cujo id é igual ao passado por parâmetro.")
    void deveDeletarRoom(){
        Room room = new Room(1001);

        when(roomRepository.findByNumber(1001)).thenReturn(Optional.of(room));

        ArgumentCaptor<Room> captor = ArgumentCaptor.forClass(Room.class);

        roomService.deleteRoom(1001);

        verify(roomRepository).delete(captor.capture());

        Room roomDeleted = captor.getValue();

        assertEquals(room.getNumber(), roomDeleted.getNumber());

        verify(roomRepository).findByNumber(1001);
        verify(roomRepository).delete(any(Room.class));
    }

    @Test
    @DisplayName("Deve lançar excecao se Room não for encontrada.")
    void deveLancarExcecaoSeRoomNaoForEncontradaParaDeletacao(){
        when(roomRepository.findByNumber(1001)).thenReturn(Optional.empty());

        assertThrows(RoomNotFoundException.class, () -> roomService.deleteRoom(1001));

        verify(roomRepository, never()).delete(any(Room.class));
    }

    @Test
    @DisplayName("Deve retornar Room cujo ID é igual ao ID passado por parâmetro.")
    void deveRetornarRoomPeloId(){
        Room room = new Room(1001);
        RoomGetDTO dto = new RoomGetDTO(1L, 1001, LocalDate.now(), StatusRoom.AVAILABLE);

        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(conversor.converterRoom(any(Room.class))).thenReturn(dto);

        RoomGetDTO roomGetDTO = roomService.findByIdController(1L);

        assertEquals(roomGetDTO.number(), room.getNumber());

        verify(roomRepository).findById(1L);
        verify(conversor).converterRoom(any(Room.class));
    }

    @Test
    @DisplayName("Deve lançar excecao se Room não for encontrado pelo ID.")
    void deveLancarExcecaoSeRoomNaoForEncontradaPeloId(){
        when(roomRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RoomNotFoundException.class, () -> roomService.findById(1L));

        verify(roomRepository).findById(1L);
        verify(conversor, never()).converterRoom(any(Room.class));
    }

    @Test
    @DisplayName("Deve Retornar lista com todas as Roons cdastradas.")
    void deveRetornarListaDeRoons(){
        Room room1 = new Room(1001);
        Room room2 = new Room(1002);
        Room room3 = new Room(1002);
        List<Room> list = List.of(room1, room2, room3);

        when(roomRepository.findAll()).thenReturn(list);
        when(conversor.converterRoom(any(Room.class)))
                .thenReturn(
                        new RoomGetDTO(1L, room1.getNumber(), LocalDate.now(), StatusRoom.AVAILABLE),
                        new RoomGetDTO(2L, room2.getNumber(), LocalDate.now(), StatusRoom.AVAILABLE),
                        new RoomGetDTO(3L, room3.getNumber(), LocalDate.now(), StatusRoom.AVAILABLE));

        List<RoomGetDTO> listDTO = roomService.listRoons();

        assertEquals(listDTO.size(), list.size());

        assertEquals(listDTO.get(0).number(), list.get(0).getNumber());
        assertEquals(listDTO.get(1).number(), list.get(1).getNumber());
        assertEquals(listDTO.get(2).number(), list.get(2).getNumber());

        verify(roomRepository).findAll();
        verify(conversor, times(3)).converterRoom(any(Room.class));
    }

    @Test
    @DisplayName("Deve retornar Room cujo Numero é igual ao Numero passado por parâmetro.")
    void deveRetornarRoomPeloNumber(){
        Room room = new Room(1001);
        RoomGetDTO dto = new RoomGetDTO(1L, 1001, LocalDate.now(), StatusRoom.AVAILABLE);

        when(roomRepository.findByNumber(1001)).thenReturn(Optional.of(room));
        when(conversor.converterRoom(any(Room.class))).thenReturn(dto);

        RoomGetDTO roomGetDTO = roomService.findByNumber(1001);

        assertEquals(roomGetDTO.number(), room.getNumber());

        verify(roomRepository).findByNumber(1001);
        verify(conversor).converterRoom(any(Room.class));
    }

    @Test
    @DisplayName("Deve Retornar lista com todas as Roons cujo data de criação é igual a passada por parâmetro..")
    void deveRetornarListaDeRoonsComMesmaDataDeCriacao(){
        Room room1 = new Room(1001);
        Room room2 = new Room(1002);
        Room room3 = new Room(1002);
        List<Room> list = List.of(room1, room2, room3);

        when(roomRepository.findByCreationDate(LocalDate.now())).thenReturn(list);
        when(conversor.converterRoom(any(Room.class)))
                .thenReturn(
                        new RoomGetDTO(1L, room1.getNumber(), LocalDate.now(), StatusRoom.AVAILABLE),
                        new RoomGetDTO(2L, room2.getNumber(), LocalDate.now(), StatusRoom.AVAILABLE),
                        new RoomGetDTO(3L, room3.getNumber(), LocalDate.now(), StatusRoom.AVAILABLE));

        List<RoomGetDTO> listDTO = roomService.findByCreationDate(LocalDate.now());

        assertEquals(listDTO.size(), list.size());

        assertEquals(listDTO.get(0).number(), list.get(0).getNumber());
        assertEquals(listDTO.get(1).number(), list.get(1).getNumber());
        assertEquals(listDTO.get(2).number(), list.get(2).getNumber());

        verify(roomRepository).findByCreationDate(LocalDate.now());
        verify(conversor, times(3)).converterRoom(any(Room.class));
    }

    @Test
    @DisplayName("Deve Retornar lista com todas as Roons cdastradas com mesmo status passado por parâmetro.")
    void deveRetornarListaDeRoonsComMesmoStatus(){
        Room room1 = new Room(1001);
        Room room2 = new Room(1002);
        Room room3 = new Room(1002);
        List<Room> list = List.of(room1, room2, room3);

        when(roomRepository.findByStatus(StatusRoom.AVAILABLE)).thenReturn(list);
        when(conversor.converterRoom(any(Room.class)))
                .thenReturn(
                        new RoomGetDTO(1L, room1.getNumber(), LocalDate.now(), StatusRoom.AVAILABLE),
                        new RoomGetDTO(2L, room2.getNumber(), LocalDate.now(), StatusRoom.AVAILABLE),
                        new RoomGetDTO(3L, room3.getNumber(), LocalDate.now(), StatusRoom.AVAILABLE));

        List<RoomGetDTO> listDTO = roomService.findByStatus("available");

        assertEquals(listDTO.size(), list.size());

        assertEquals(listDTO.get(0).number(), list.get(0).getNumber());
        assertEquals(listDTO.get(1).number(), list.get(1).getNumber());
        assertEquals(listDTO.get(2).number(), list.get(2).getNumber());

        verify(roomRepository).findByStatus(StatusRoom.AVAILABLE);
        verify(conversor, times(3)).converterRoom(any(Room.class));
    }
}
