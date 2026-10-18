package com.Projeto_Alura.RoomReservation.Controllers.Room;

import com.Projeto_Alura.RoomReservation.Controller.Room.RoomPostController;
import com.Projeto_Alura.RoomReservation.DTOs.Room.RoomGetDTO;
import com.Projeto_Alura.RoomReservation.ENUM.StatusRoom;
import com.Projeto_Alura.RoomReservation.Service.RoomService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoomPostController.class)
public class RoomPostControllerTest {

    @MockitoBean
    private RoomService roomService;

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Deve retornar Status Code 201 - CREATED quando sala for criada.")
    void devRetornarStatus201() throws Exception {
        RoomGetDTO dto = new RoomGetDTO(1L, 12345, LocalDate.now(), StatusRoom.AVAILABLE);

        when(roomService.createRoom()).thenReturn(dto);

        mvc.perform(
                post("/room")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());

        verify(roomService).createRoom();
    }
}
