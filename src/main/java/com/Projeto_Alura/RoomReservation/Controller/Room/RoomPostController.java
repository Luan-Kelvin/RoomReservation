package com.Projeto_Alura.RoomReservation.Controller.Room;

import com.Projeto_Alura.RoomReservation.DTOs.Room.RoomGetDTO;
import com.Projeto_Alura.RoomReservation.Domain.Room;
import com.Projeto_Alura.RoomReservation.Service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomPostController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomGetDTO> createRoom(){
        RoomGetDTO roomCreate = roomService.createRoom();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/id/{id}")
                .buildAndExpand(roomCreate.id())
                .toUri();

        return ResponseEntity.created(location).body(roomCreate);
    }
}
