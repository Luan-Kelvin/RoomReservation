package com.Projeto_Alura.RoomReservation.Controllers.Reserve;

import com.Projeto_Alura.RoomReservation.Controller.Reserve.ReservePostController;
import com.Projeto_Alura.RoomReservation.DTOs.Reserve.ReserveGetDTO;
import com.Projeto_Alura.RoomReservation.DTOs.Reserve.ReservePostDTO;
import com.Projeto_Alura.RoomReservation.ENUM.StatusReserve;
import com.Projeto_Alura.RoomReservation.Service.ReserveService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservePostController.class)
public class ReservePostControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private ReserveService reserveService;

    @Test
    @DisplayName("Deve retornar Status 201 - CREATED Quando uma reserva for criada.")
    void deveRetornarStatus201Created() throws Exception {
        String json = """
                {
                       "idRoom": 1,
                       "idUser": 2,
                       "statusReserve": "ACTIVE",
                       "startReservation": "2026-10-16",
                       "endReservation": "2026-10-20"
                }
                """;

        ReserveGetDTO dto = new ReserveGetDTO(
                1L, 12345, LocalDate.parse("2026-10-16"),
                LocalDate.parse("2026-10-20"), StatusReserve.ACTIVE, 1L, 2L
        );

        when(reserveService.createReserve(any(ReservePostDTO.class))).thenReturn(dto);

        mvc.perform(
                post("/reserve/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isCreated());

        verify(reserveService).createReserve(any(ReservePostDTO.class));
    }
}
