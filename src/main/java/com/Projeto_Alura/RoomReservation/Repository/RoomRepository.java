package com.Projeto_Alura.RoomReservation.Repository;

import com.Projeto_Alura.RoomReservation.Domain.Room;
import com.Projeto_Alura.RoomReservation.ENUM.StatusRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query(value = "SELECT nextval('number_room_seq');", nativeQuery = true)
    Integer generateNewRoomNumber();

    Optional<Room> findByNumber(Integer number);

    Page<Room> findByCreationDate(LocalDate creationDate, Pageable pageable);

    Page<Room> findByCreationDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    Page<Room> findByStatus(StatusRoom status, Pageable pageable);

}
