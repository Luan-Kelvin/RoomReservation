package com.Projeto_Alura.RoomReservation.Controller.User;

import com.Projeto_Alura.RoomReservation.DTOs.User.UserGetDTO;
import com.Projeto_Alura.RoomReservation.DTOs.User.UserPostDTO;
import com.Projeto_Alura.RoomReservation.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserPostController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserGetDTO> createUser(@RequestBody UserPostDTO dto){
        UserGetDTO user = userService.createUser(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.id())
                .toUri();

        return ResponseEntity.created(location).body(user);
    }
}
