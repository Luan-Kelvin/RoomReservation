package com.Projeto_Alura.RoomReservation.Controller.Room;

import com.Projeto_Alura.RoomReservation.DTOs.Room.RoomGetDTO;
import com.Projeto_Alura.RoomReservation.Service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomGetController {

    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<List<RoomGetDTO>> listRoons(){
        return ResponseEntity.ok().body(roomService.listRoons());
    }

    @GetMapping("id/{id}")
    public ResponseEntity<RoomGetDTO> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(roomService.findByIdController(id));
    }

    @GetMapping("/numer/{number}")
    public ResponseEntity<RoomGetDTO> findByNumberRoom(@PathVariable("number") Integer number){
        return ResponseEntity.ok().body(roomService.findByNumber(number));
    }

    @GetMapping("/creation-date/{date}")
    public ResponseEntity<List<RoomGetDTO>> findByCreationDate(@PathVariable("date") String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return ResponseEntity.ok().body(roomService.findByCreationDate(LocalDate.parse(date, formatter)));
    }

    @GetMapping("/creation-date-between/{startDate}/{endDate}")
    public ResponseEntity<List<RoomGetDTO>> findByCreationDateBetween(
            @PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate
    ){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        return ResponseEntity.ok().body(roomService.findByCreationDateBetween(start, end));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<RoomGetDTO>> findByStatus(@PathVariable("status") String status){
        return ResponseEntity.ok().body(roomService.findByStatus(status));
    }


}
