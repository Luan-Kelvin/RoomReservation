package com.Projeto_Alura.RoomReservation.Controller.Reserve;

import com.Projeto_Alura.RoomReservation.Service.ReserveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("reserve")
@RequiredArgsConstructor
public class ReserveDeleteController {

    private final ReserveService reserveService;

    @DeleteMapping("/number/{number}")
    public ResponseEntity<String> deleteReserve(@PathVariable("number") Integer number){

        reserveService.deleteReserve(number);

        return ResponseEntity.ok().body("Reserva deletada com sucesso!");
    }
}
