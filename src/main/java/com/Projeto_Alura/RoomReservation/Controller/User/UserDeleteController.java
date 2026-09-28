package com.Projeto_Alura.RoomReservation.Controller.User;

import com.Projeto_Alura.RoomReservation.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserDeleteController {

    private final UserService userService;

    @DeleteMapping("/delete/cpf/{cpf}")
    public ResponseEntity<String> deleteUser(@PathVariable("cpf") String cpf){
        userService.deleteUser(cpf);

        return ResponseEntity.ok().body(String.format("Usuário com CPF: %s, foi deletado com sucesso!", cpf));
    }
}
