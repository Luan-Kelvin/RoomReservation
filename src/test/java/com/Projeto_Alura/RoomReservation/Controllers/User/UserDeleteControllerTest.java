package com.Projeto_Alura.RoomReservation.Controllers.User;

import com.Projeto_Alura.RoomReservation.Controller.User.UserDeleteController;
import com.Projeto_Alura.RoomReservation.Service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserDeleteController.class)
public class UserDeleteControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private UserService userService;

    @Test
    @DisplayName("Deve retornar status 200 - OK Quand Usuario for deletado.")
    void deveRetornarStaus200() throws Exception {
        String cpf = "123.456.789-10";

        mvc.perform(delete("/user/delete/cpf/"+cpf)).andExpect(status().isOk());

        verify(userService).deleteUser(cpf);
    }
}
