package com.Projeto_Alura.RoomReservation.Controllers.User;

import com.Projeto_Alura.RoomReservation.Controller.User.UserPostController;
import com.Projeto_Alura.RoomReservation.DTOs.User.UserGetDTO;
import com.Projeto_Alura.RoomReservation.DTOs.User.UserPostDTO;
import com.Projeto_Alura.RoomReservation.Service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserPostController.class)
public class UserPostControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private UserService userService;

    @Test
    @DisplayName("Deve retornar Status 201 - CREATED Quando novo usuário for criado.")
    void deveRteirnarStatus201() throws Exception {
        String json = """
                    {
                        "name": "Joaquim Ferreira",
                        "age": 25,
                        "email": "Lulu@gmail.com",
                        "cpf": "123.456.789-25"
                    }
                """;

        UserGetDTO dto = new UserGetDTO(1L, "Joaquim Ferreira", 25, "Lulu@gmail.com");

        when(userService.createUser(any(UserPostDTO.class))).thenReturn(dto);

        mvc.perform(
                post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        verify(userService).createUser(any(UserPostDTO.class));

    }
}
