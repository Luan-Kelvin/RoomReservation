package com.Projeto_Alura.RoomReservation.Controller.Reserve;


import com.Projeto_Alura.RoomReservation.DTOs.Reserve.ReserveGetDTO;
import com.Projeto_Alura.RoomReservation.DTOs.Reserve.ReservePostDTO;
import com.Projeto_Alura.RoomReservation.Service.ReserveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/reserve")
@RequiredArgsConstructor
public class ReservePostController {

    private final ReserveService reserveService;

    @PostMapping("/create")
    public ResponseEntity<ReserveGetDTO> createReserve(@RequestBody ReservePostDTO dto){

        ReserveGetDTO reserve = reserveService.createReserve(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/id/{id}")
                .buildAndExpand(reserve.id())
                .toUri();

        return ResponseEntity.created(location).body(reserve);
    }

}
