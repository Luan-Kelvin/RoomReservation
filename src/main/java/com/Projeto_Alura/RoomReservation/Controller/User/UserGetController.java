package com.Projeto_Alura.RoomReservation.Controller.User;

import com.Projeto_Alura.RoomReservation.DTOs.User.UserGetDTO;
import com.Projeto_Alura.RoomReservation.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserGetController {

    private final UserService userService;

    // LISTAR TODOS OS USUÁRIOS CADASTRADOS.
    @GetMapping
    public ResponseEntity<List<UserGetDTO>> listUser(){
        return ResponseEntity.ok().body(userService.listUser());
    }

    // BUSCAR USUÁRIO PELO CPF
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<UserGetDTO> findByCpf(@PathVariable("cpf") String cpf){
        return ResponseEntity.ok().body(userService.findByCpf(cpf));
    }

    // BUSCAR USUÁRIO PELO ID
    @GetMapping("/id/{id}")
    public ResponseEntity<UserGetDTO> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(userService.findByIdController(id));
    }
}
