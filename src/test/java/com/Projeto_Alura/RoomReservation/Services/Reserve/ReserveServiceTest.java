package com.Projeto_Alura.RoomReservation.Services.Reserve;

import com.Projeto_Alura.RoomReservation.Conversor.Conversor;
import com.Projeto_Alura.RoomReservation.DTOs.Reserve.ReserveGetDTO;
import com.Projeto_Alura.RoomReservation.DTOs.Reserve.ReservePostDTO;
import com.Projeto_Alura.RoomReservation.Domain.Reserve;
import com.Projeto_Alura.RoomReservation.Domain.Room;
import com.Projeto_Alura.RoomReservation.Domain.User;
import com.Projeto_Alura.RoomReservation.ENUM.StatusReserve;
import com.Projeto_Alura.RoomReservation.Repository.ReserveRepository;
import com.Projeto_Alura.RoomReservation.Service.ReserveService;
import com.Projeto_Alura.RoomReservation.Service.RoomService;
import com.Projeto_Alura.RoomReservation.Service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReserveServiceTest {

    @Mock
    private ReserveRepository reserveRepository;

    @Mock
    private Conversor conversor;

    @Mock
    private UserService userService;

    @Mock
    private RoomService roomService;

    @InjectMocks
    private ReserveService reserveService;

    @Test
    @DisplayName("Deve criar uma nova reserva e retorna-la como DTO.")
    void deveCriarNovaReserva(){
        ReservePostDTO reservePostDTO = new ReservePostDTO(
                1L, 1L, StatusReserve.ACTIVE, LocalDate.parse("2026-12-10"), LocalDate.parse("2026-12-15")
        );

        ReserveGetDTO getDto = new ReserveGetDTO(
                1L, 10001, LocalDate.parse("2026-12-10"), LocalDate.parse("2026-12-15"), StatusReserve.ACTIVE, 1L, 1L
        );

        User user = new User("Joaquim", "123.456.789-10", 26, "jojo@gmail.com");
        Room room = new Room(1001);

        when(userService.findById(1L)).thenReturn(user);
        when(roomService.findById(1L)).thenReturn(room);
        when(reserveRepository.generateNewReserveNumber()).thenReturn(10001);
        when(conversor.converterReserve(any(Reserve.class))).thenReturn(getDto);

        ReserveGetDTO reserveGetDTO = reserveService.createReserve(reservePostDTO);

        ArgumentCaptor<Reserve> captor = ArgumentCaptor.forClass(Reserve.class);

        verify(reserveRepository).save(captor.capture());

        Reserve reserveSave = captor.getValue();


        assertEquals(room, reserveSave.getRoom());
        assertEquals(user, reserveSave.getUser());
        assertEquals(reservePostDTO.statusReserve(), reserveSave.getStatus());
        assertEquals(reservePostDTO.startReservation(), reserveSave.getStartReservation());
        assertEquals(reservePostDTO.endReservation(), reserveSave.getEndReservation());

        assertEquals(reserveSave.getNumber(), reserveGetDTO.number());
        assertEquals(reserveSave.getStatus(), reserveGetDTO.status());
        assertEquals(reserveSave.getStartReservation(), reserveGetDTO.startReservation());
        assertEquals(reserveSave.getEndReservation(), reserveGetDTO.edReservation());

        verify(userService).findById(1L);
        verify(roomService).findById(1L);
        verify(userService).save(any(User.class));
        verify(roomService).save(any(Room.class));
        verify(reserveRepository).generateNewReserveNumber();
    }

    @Test
    @DisplayName("Deve deletar uma reserva")
    void deveDeletarReserva() throws Exception{
        User user = new User("Joaquim", "123.456.789-10", 26, "jojo@gmail.com");
        Room room = new Room(1001);

        Field idFieldUser = User.class.getDeclaredField("id");
        idFieldUser.setAccessible(true);
        idFieldUser.set(user, 1L);

        Field idFieldRoom = Room.class.getDeclaredField("id");
        idFieldRoom.setAccessible(true);
        idFieldRoom.set(room, 1L);

        Reserve reserve = new Reserve(
                1001, room, user, LocalDate.parse("2026-11-10"), LocalDate.parse("2026-11-21")
        );

        when(reserveRepository.findByNumber(reserve.getNumber())).thenReturn(Optional.of(reserve));
        when(userService.findById(1L)).thenReturn(user);
        when(roomService.findById(1L)).thenReturn(room);

        ArgumentCaptor<Reserve> captor = ArgumentCaptor.forClass(Reserve.class);


        reserveService.deleteReserve(1001);

        verify(reserveRepository).delete(captor.capture());

        Reserve reserveDeleted = captor.getValue();

        assertEquals(reserve.getNumber(), reserveDeleted.getNumber());
        assertEquals(reserve.getRoom(), reserveDeleted.getRoom());
        assertEquals(reserve.getUser(), reserveDeleted.getUser());
        assertEquals(reserve.getStatus(), reserveDeleted.getStatus());
        assertEquals(reserve.getStartReservation(), reserveDeleted.getStartReservation());
        assertEquals(reserve.getEndReservation(), reserveDeleted.getEndReservation());

        verify(reserveRepository).findByNumber(reserve.getNumber());
        verify(userService).findById(1L);
        verify(roomService).findById(1L);
        verify(userService).save(any(User.class));
        verify(roomService).save(any(Room.class));
    }
}
