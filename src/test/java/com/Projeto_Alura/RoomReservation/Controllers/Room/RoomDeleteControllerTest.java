package com.Projeto_Alura.RoomReservation.Controllers.Room;

import com.Projeto_Alura.RoomReservation.Controller.Room.RoomDeleteController;
import com.Projeto_Alura.RoomReservation.Service.RoomService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoomDeleteController.class)
public class RoomDeleteControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private RoomService roomService;

    @Test
    @DisplayName("Deve retornar Status 200 - OK quando room for deletada")
    void deveRetornarStatus200() throws Exception {
        Integer number =  1234;

        mvc.perform(
                delete("/room/delete/"+number)
        ).andExpect(status().isOk());

        verify(roomService).deleteRoom(number);
    }
}
