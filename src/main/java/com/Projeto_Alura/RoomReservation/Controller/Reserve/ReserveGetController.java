package com.Projeto_Alura.RoomReservation.Controller.Reserve;

import com.Projeto_Alura.RoomReservation.DTOs.Reserve.ReserveGetDTO;
import com.Projeto_Alura.RoomReservation.Service.ReserveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/reserve")
@RequiredArgsConstructor
public class ReserveGetController {

    private final ReserveService reserveService;

    @GetMapping("/number/{number}")
    public ResponseEntity<ReserveGetDTO> searchReserveByNumber(@PathVariable("number") Integer number){
        return ResponseEntity.ok().body(reserveService.findByNumber(number));
    }

    @GetMapping("/start_reservation")
    public ResponseEntity<List<ReserveGetDTO>> findByStartReservation(@RequestParam String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return ResponseEntity.ok().body(reserveService.findByStartReservation(LocalDate.parse(date, formatter)));
    }

    @GetMapping("/status")
    public ResponseEntity<List<ReserveGetDTO>> findByStatus(@RequestParam String status){
        return ResponseEntity.ok().body(reserveService.findByStatus(status));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ReserveGetDTO> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(reserveService.findById(id));
    }
}
