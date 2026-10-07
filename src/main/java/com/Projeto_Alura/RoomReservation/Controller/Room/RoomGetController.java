package com.Projeto_Alura.RoomReservation.Controller.Room;

import com.Projeto_Alura.RoomReservation.DTOs.Room.RoomGetDTO;
import com.Projeto_Alura.RoomReservation.Service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomGetController {

    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<Page<RoomGetDTO>> listRoons(Pageable pageable){
        return ResponseEntity.ok().body(roomService.listRoons(pageable));
    }

    @GetMapping("id/{id}")
    public ResponseEntity<RoomGetDTO> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(roomService.findByIdController(id));
    }

    @GetMapping("/number/{number}")
    public ResponseEntity<RoomGetDTO> findByNumberRoom(@PathVariable("number") Integer number){
        return ResponseEntity.ok().body(roomService.findByNumber(number));
    }

    @GetMapping("/creation_date")
    public ResponseEntity<Page<RoomGetDTO>> findByCreationDate(@RequestParam String date, Pageable pageable){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return ResponseEntity.ok().body(roomService.findByCreationDate(LocalDate.parse(date, formatter),pageable));
    }

    @GetMapping("/creation_date_between")
    public ResponseEntity<Page<RoomGetDTO>> findByCreationDateBetween(
            @RequestParam String startDate, @RequestParam String endDate, Pageable pageable
    ){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        return ResponseEntity.ok().body(roomService.findByCreationDateBetween(start, end, pageable));
    }

    @GetMapping("/status")
    public ResponseEntity<Page<RoomGetDTO>> findByStatus(@RequestParam String status, Pageable pageable){
        return ResponseEntity.ok().body(roomService.findByStatus(status, pageable));
    }


}
