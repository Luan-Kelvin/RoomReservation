package com.Projeto_Alura.RoomReservation.Controllers.Room;

import com.Projeto_Alura.RoomReservation.Controller.Room.RoomGetController;
import com.Projeto_Alura.RoomReservation.DTOs.Room.RoomGetDTO;
import com.Projeto_Alura.RoomReservation.ENUM.StatusRoom;
import com.Projeto_Alura.RoomReservation.Service.RoomService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoomGetController.class)
public class RoomGetControllerTest {

    @MockitoBean
    private RoomService roomService;

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Deve retornar status 200 - OK quando lista de room for retornada")
    void deveRetornarStatus200SeListaForRetornada() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);

        RoomGetDTO getDto1 = new RoomGetDTO(1L, 1001, LocalDate.now(), StatusRoom.AVAILABLE);
        RoomGetDTO getDto2 = new RoomGetDTO(2L, 1002, LocalDate.now(), StatusRoom.AVAILABLE);
        RoomGetDTO getDto3 = new RoomGetDTO(3L, 1003, LocalDate.now(), StatusRoom.AVAILABLE);

        List<RoomGetDTO> listRoomGetDto = List.of(getDto1, getDto2, getDto3);

        Page<RoomGetDTO> page = new PageImpl<>(listRoomGetDto);

        when(roomService.listRoons(pageable)).thenReturn(page);

        mvc.perform(get("http://localhost:8080/room?page=0&size=10")).andExpect(status().isOk());

        verify(roomService).listRoons(pageable);
    }

    @Test
    @DisplayName("Deve retornar Status 200 - OK quando encontrar Room pelo ID ")
    void deveRetornar200SeRoomForEncontradaPeloId() throws Exception {
        RoomGetDTO roomGetDTO = new RoomGetDTO(1L, 1001, LocalDate.now(), StatusRoom.AVAILABLE);

        when(roomService.findByIdController(1L)).thenReturn(roomGetDTO);

        mvc.perform(get("/room/id/1")).andExpect(status().isOk());

        verify(roomService).findByIdController(1L);
    }

    @Test
    @DisplayName("Deve retornar Status 200 - OK Quando room for encontrada pelo Número.")
    void deveRetornar200QuandoRoomForEncontradaPeloNumber() throws Exception {
        RoomGetDTO roomGetDTO = new RoomGetDTO(1L, 1001, LocalDate.now(), StatusRoom.AVAILABLE);

        when(roomService.findByNumber(1001)).thenReturn(roomGetDTO);

        mvc.perform(get("/room/number/1001")).andExpect(status().isOk());

        verify(roomService).findByNumber(1001);
    }

    @Test
    @DisplayName("Deve retornar Status 200 - OK quando Page com roons da mesma datade criação forem retornadas")
    void deveRetornarStatus200QuandoRetornarPageAgrupadasPelaDataDeCriacao() throws Exception {
        Pageable pageable = PageRequest.of(0,10);

        RoomGetDTO getDto1 = new RoomGetDTO(1L, 1001, LocalDate.now(), StatusRoom.AVAILABLE);
        RoomGetDTO getDto2 = new RoomGetDTO(2L, 1002, LocalDate.now(), StatusRoom.AVAILABLE);
        RoomGetDTO getDto3 = new RoomGetDTO(3L, 1003, LocalDate.now(), StatusRoom.AVAILABLE);

        List<RoomGetDTO> listRoomGetDto = List.of(getDto1, getDto2, getDto3);

        Page<RoomGetDTO> page = new PageImpl<>(listRoomGetDto);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse("06/10/2026", formatter);

        when(roomService.findByCreationDate(date, pageable)).thenReturn(page);


        mvc.perform(get("/room/creation_date?date=06/10/2026&page=0&size=10")).andExpect(status().isOk());

        verify(roomService).findByCreationDate(date, pageable);
    }

    @Test
    @DisplayName("Deve retornar Status 200 - OK Quando retornar Page de Rons pelas datas de inicio de reserva e fim de reserva")
    void devRetornar200QuandoRetornarPageBetween() throws Exception {
        Pageable pageable = PageRequest.of(0,10);

        RoomGetDTO getDto1 = new RoomGetDTO(1L, 1001, LocalDate.now(), StatusRoom.AVAILABLE);
        RoomGetDTO getDto2 = new RoomGetDTO(2L, 1002, LocalDate.now(), StatusRoom.AVAILABLE);
        RoomGetDTO getDto3 = new RoomGetDTO(3L, 1003, LocalDate.now(), StatusRoom.AVAILABLE);

        List<RoomGetDTO> listRoomGetDto = List.of(getDto1, getDto2, getDto3);

        Page<RoomGetDTO> page = new PageImpl<>(listRoomGetDto);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate startDate = LocalDate.parse("06/10/2026", formatter);
        LocalDate endDate = LocalDate.parse("15/10/2026", formatter);

        when(roomService.findByCreationDateBetween(startDate, endDate, pageable)).thenReturn(page);


        mvc.perform(get(
                        "/room/creation_date_between?startDate=06/10/2026&endDate=15/10/2026&page=0&size=10"))
                .andExpect(status().isOk());

        verify(roomService).findByCreationDateBetween(startDate, endDate, pageable);
    }

    @Test
    @DisplayName("Deve retornar Status 200 - OK quando retornar Page filtrado pelo Status")
    void deveRetornar200QuandoRetornarPageStatus() throws Exception {
        Pageable pageable = PageRequest.of(0,10);

        RoomGetDTO getDto1 = new RoomGetDTO(1L, 1001, LocalDate.now(), StatusRoom.AVAILABLE);
        RoomGetDTO getDto2 = new RoomGetDTO(2L, 1002, LocalDate.now(), StatusRoom.AVAILABLE);
        RoomGetDTO getDto3 = new RoomGetDTO(3L, 1003, LocalDate.now(), StatusRoom.AVAILABLE);

        List<RoomGetDTO> listRoomGetDto = List.of(getDto1, getDto2, getDto3);

        Page<RoomGetDTO> page = new PageImpl<>(listRoomGetDto);

        when(roomService.findByStatus("AVAILABLE", pageable)).thenReturn(page);

        mvc.perform(get("/room/status?status=AVAILABLE&page=0&size=10")).andExpect(status().isOk());

        verify(roomService).findByStatus("AVAILABLE", pageable);
    }
}
