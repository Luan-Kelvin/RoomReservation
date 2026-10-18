package com.Projeto_Alura.RoomReservation.Controllers.Reserve;

import com.Projeto_Alura.RoomReservation.Controller.Reserve.ReserveDeleteController;
import com.Projeto_Alura.RoomReservation.Service.ReserveService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReserveDeleteController.class)
public class ReserveDeleteControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private ReserveService reserveService;

    @Test
    @DisplayName("Deve retornar status 200 - Ok Quando usuário for deletado.")
    void deveRteirnarStatus200() throws Exception {
        Integer number = 12345;

        mvc.perform(delete("/reserve/number/"+number)).andExpect(status().isOk());

        verify(reserveService).deleteReserve(number);
    }
}
