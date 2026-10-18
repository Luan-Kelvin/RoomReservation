package com.Projeto_Alura.RoomReservation.Controllers.Reserve;

import com.Projeto_Alura.RoomReservation.Controller.Reserve.ReserveGetController;
import com.Projeto_Alura.RoomReservation.DTOs.Reserve.ReserveGetDTO;
import com.Projeto_Alura.RoomReservation.ENUM.StatusReserve;
import com.Projeto_Alura.RoomReservation.Service.ReserveService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReserveGetController.class)
public class ReserveGetControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private ReserveService reserveService;

    @Test
    @DisplayName("Deve retornar Sttaus 200 -ok quando reserva for encontrada pelo número.")
    void deveRetornarStatus200ComreservaEncontradaPeloNumero() throws Exception {
        Integer number = 123456;

        ReserveGetDTO dto = new ReserveGetDTO(
                1L, 123456, LocalDate.parse("2026-10-16"),
                LocalDate.parse("2026-10-20"), StatusReserve.ACTIVE, 1L, 2L
        );

        when(reserveService.findByNumber(number)).thenReturn(dto);

        mvc.perform(get("/reserve/number/"+number)).andExpect(status().isOk());

        verify(reserveService).findByNumber(number);
    }

    @Test
    @DisplayName("Deve retornar Sttaus 200 -ok quando reserva for encontrada pelo ID.")
    void deveRetornarStatus200ComreservaEncontradaPeloID() throws Exception {
        Long id = 1L;

        ReserveGetDTO dto = new ReserveGetDTO(
                1L, 123456, LocalDate.parse("2026-10-16"),
                LocalDate.parse("2026-10-20"), StatusReserve.ACTIVE, 1L, 2L
        );

        when(reserveService.findById(id)).thenReturn(dto);

        mvc.perform(get("/reserve/id/"+id)).andExpect(status().isOk());

        verify(reserveService).findById(id);
    }

    @Test
    @DisplayName("Deve retornar Status 200 Quando retornar Lista de reservas pela data de criação.")
    void deveRetornar200QuandoListaDeDataDeCriacaoForRetornada() throws Exception {
        LocalDate date = LocalDate.of(2026, 10, 5);

        ReserveGetDTO dto1 = new ReserveGetDTO(
                1L, 123456, LocalDate.parse("2026-10-05"),
                LocalDate.parse("2026-10-20"), StatusReserve.ACTIVE, 1L, 2L
        );

        ReserveGetDTO dto2 = new ReserveGetDTO(
                2L, 654321, LocalDate.parse("2026-10-05"),
                LocalDate.parse("2026-10-05"), StatusReserve.ACTIVE, 1L, 2L
        );

        List<ReserveGetDTO> list = List.of(dto1, dto2);

        Pageable pageable = PageRequest.of(0, 10);
        Page<ReserveGetDTO> page = new PageImpl<>(list);

        when(reserveService.findByStartReservation(date, pageable)).thenReturn(page);

        mvc.perform(get("/reserve/start_reservation?page=0&size=10&date=05/10/2026")).andExpect(status().isOk());

        verify(reserveService).findByStartReservation(date, pageable);
    }

    @Test
    @DisplayName("Deve retornar Status 200 Quando retornar Lista de reservas pelo Status.")
    void deveRetornar200QuandoListaDeStatusForRetornada() throws Exception {
        ReserveGetDTO dto1 = new ReserveGetDTO(
                1L, 123456, LocalDate.parse("2026-10-05"),
                LocalDate.parse("2026-10-20"), StatusReserve.ACTIVE, 1L, 2L
        );

        ReserveGetDTO dto2 = new ReserveGetDTO(
                2L, 654321, LocalDate.parse("2026-10-05"),
                LocalDate.parse("2026-10-05"), StatusReserve.ACTIVE, 1L, 2L
        );

        List<ReserveGetDTO> list = List.of(dto1, dto2);

        Pageable pageable = PageRequest.of(0, 10);
        Page<ReserveGetDTO> page = new PageImpl<>(list);

        when(reserveService.findByStatus("ACTIVE", pageable)).thenReturn(page);

        mvc.perform(get("/reserve/status?page=0&size=10&status=ACTIVE")).andExpect(status().isOk());

        verify(reserveService).findByStatus("ACTIVE", pageable);
    }
}
