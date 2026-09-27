package com.Projeto_Alura.RoomReservation.Repository;

import com.Projeto_Alura.RoomReservation.Domain.Reserve;
import com.Projeto_Alura.RoomReservation.ENUM.StatusReserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReserveRepository extends JpaRepository<Reserve, Long> {

    @Query(value = "SELECT nextval('number_reserve_seq')", nativeQuery = true)
    Integer generateNewReserveNumber();

    Optional<Reserve> findByNumber(Integer number);

    List<Reserve> findByStartReservation(LocalDate date);

    List<Reserve> findByStatus(StatusReserve status);



}
