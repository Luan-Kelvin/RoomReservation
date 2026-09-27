package com.Projeto_Alura.RoomReservation.Repository;

import com.Projeto_Alura.RoomReservation.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByCpf(String cpf);

}
