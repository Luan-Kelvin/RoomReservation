package com.Projeto_Alura.RoomReservation.Controller.Room;

import com.Projeto_Alura.RoomReservation.Service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomDeleteController {

    private final RoomService roomService;

    @DeleteMapping("/delete/{number}")
    public ResponseEntity<String> deleteRoom(@PathVariable("number") Integer number){
        roomService.deleteRoom(number);

        return ResponseEntity.ok().body("Sala deletada com sucesso!");
    }
}
