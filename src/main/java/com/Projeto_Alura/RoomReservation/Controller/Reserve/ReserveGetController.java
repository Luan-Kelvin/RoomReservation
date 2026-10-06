package com.Projeto_Alura.RoomReservation.Controller.Reserve;

import com.Projeto_Alura.RoomReservation.DTOs.Reserve.ReserveGetDTO;
import com.Projeto_Alura.RoomReservation.Service.ReserveService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    public ResponseEntity<Page<ReserveGetDTO>> findByStartReservation(@RequestParam String date, Pageable pageable){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return ResponseEntity.ok().body(reserveService.findByStartReservation(LocalDate.parse(date, formatter), pageable));
    }

    @GetMapping("/status")
    public ResponseEntity<Page<ReserveGetDTO>> findByStatus(@RequestParam String status, Pageable pageable){
        return ResponseEntity.ok().body(reserveService.findByStatus(status, pageable));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ReserveGetDTO> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(reserveService.findById(id));
    }
}
