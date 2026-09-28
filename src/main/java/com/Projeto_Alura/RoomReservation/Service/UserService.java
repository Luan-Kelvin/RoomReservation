package com.Projeto_Alura.RoomReservation.Service;

import com.Projeto_Alura.RoomReservation.Conversor.Conversor;
import com.Projeto_Alura.RoomReservation.DTOs.User.UserGetDTO;
import com.Projeto_Alura.RoomReservation.DTOs.User.UserPostDTO;
import com.Projeto_Alura.RoomReservation.Domain.User;
import com.Projeto_Alura.RoomReservation.Exceptions.User.UserAlreadyExistsException;
import com.Projeto_Alura.RoomReservation.Exceptions.User.UserNotFoundException;
import com.Projeto_Alura.RoomReservation.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final Conversor conversor;

    @Transactional
    public UserGetDTO findByCpf(String cpf){
        User user = userRepository.findByCpf(cpf)
                .orElseThrow(() -> new UserNotFoundException(String.format("ERRO! Usuário com CPF %s não encontrado.", cpf)));

        return conversor.converterUser(user);
    }

    @Transactional
    public List<UserGetDTO> listUser(){
        List<User> users = userRepository.findAll();

        if (users.isEmpty()){
            return List.of();
        }

        return users.stream().map(conversor::converterUser).toList();
    }

    @Transactional
    public UserGetDTO findByIdController(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("ERRO! Usuário com ID %s não foi encontrado.", id)));

        return conversor.converterUser(user);
    }

    @Transactional
    public UserGetDTO createUser(UserPostDTO dto){
        Optional<User> user = userRepository.findByCpf(dto.cpf());

        if (user.isPresent()){
            throw new UserAlreadyExistsException("ERRO! usuário com cpf ja existente.");
        }

        User userNew = new User(dto.name(), dto.cpf(), dto.age(), dto.email());
        userRepository.save(userNew);

        return conversor.converterUser(userNew);
    }

    @Transactional
    public void deleteUser(String cpf){
        User user = userRepository.findByCpf(cpf)
                .orElseThrow(() -> new UserNotFoundException(String.format("ERRO! Usuário com CPF %s não encontrado.", cpf)));

        userRepository.delete(user);
        logger.info("Usuário deletado com sucesso!");
    }

    @Transactional
    public void save(User user){
        userRepository.save(user);
    }

    @Transactional
    public User findById(Long id){
        return  userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("ERRO! Usuário com ID %s não foi encontrado.", id)));
    }

}
