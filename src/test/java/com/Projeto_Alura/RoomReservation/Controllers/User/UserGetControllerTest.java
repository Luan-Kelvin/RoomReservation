package com.Projeto_Alura.RoomReservation.Controllers.User;

import com.Projeto_Alura.RoomReservation.Controller.User.UserGetController;
import com.Projeto_Alura.RoomReservation.DTOs.User.UserGetDTO;
import com.Projeto_Alura.RoomReservation.Service.UserService;
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

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserGetController.class)
public class UserGetControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private UserService userService;

    @Test
    @DisplayName("Deve retronar status 200 - OK quando retornar lista de usuarios cadastrados.")
    void deveRetornarStatus200EListaDeUsuariosCadastrados() throws Exception {
        UserGetDTO user1 = new UserGetDTO(1L, "Ronaldo Agelim", 28,  "lulu@gmail.com");
        UserGetDTO user2 = new UserGetDTO(2L, "Frederico Bruno",29,  "lFran@gmail.com");
        List<UserGetDTO> list = List.of(user1, user2);

        Pageable pageable = PageRequest.of(0, 10);

        Page<UserGetDTO> page = new PageImpl<>(list);

        when(userService.listUser(pageable)).thenReturn(page);

        mvc.perform(get("/user")).andExpect(status().isOk());

        verify(userService).listUser(any(Pageable.class));
    }


    @Test
    @DisplayName("Deve retornar Status 200 - OK quando usuario for enontrado pelo CPF")
    void deveRetornar200QuandoUsuarioforEnontradoPorCpf() throws Exception {
        String cpf = "123.456.789-10";

        UserGetDTO dto = new UserGetDTO(1L, "Ronaldo Agelim", 28,  "lulu@gmail.com");

        when(userService.findByCpf(cpf)).thenReturn(dto);

        mvc.perform(get("/user/cpf/"+cpf)).andExpect(status().isOk());

        verify(userService).findByCpf(cpf);
    }

    @Test
    @DisplayName("Deve retornar Status 200 - OK quando usuário for encontrado pelo ID")
    void deveRetornar200QuandoUsuarioForEncontradoPeloId() throws Exception {
        Long id = 1L;

        UserGetDTO dto = new UserGetDTO(1L, "Ronaldo Agelim", 28,  "lulu@gmail.com");

        when(userService.findByIdController(id)).thenReturn(dto);

        mvc.perform(get("/user/id/"+id)).andExpect(status().isOk());

        verify(userService).findByIdController(id);

    }
}
