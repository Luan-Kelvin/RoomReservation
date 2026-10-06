package com.Projeto_Alura.RoomReservation.Services.User;

import com.Projeto_Alura.RoomReservation.Conversor.Conversor;
import com.Projeto_Alura.RoomReservation.DTOs.User.UserGetDTO;
import com.Projeto_Alura.RoomReservation.DTOs.User.UserPostDTO;
import com.Projeto_Alura.RoomReservation.Domain.User;
import com.Projeto_Alura.RoomReservation.Exceptions.User.UserAlreadyExistsException;
import com.Projeto_Alura.RoomReservation.Exceptions.User.UserNotFoundException;
import com.Projeto_Alura.RoomReservation.Repository.UserRepository;
import com.Projeto_Alura.RoomReservation.Service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Conversor conversor;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Deve buscar usuario pelo cpf passado por parâmetro e retornar ele como DTO.")
    void deveBuscarUsuarioPeloCPF(){
        User user = new User();
        UserGetDTO userGetDTO = new UserGetDTO(1L, "João Cancelo", 21, "Jojo@gmail.com");

        when(userRepository.findByCpf("123.456.789-10")).thenReturn(Optional.of(user));
        when(conversor.converterUser(any(User.class))).thenReturn(userGetDTO);

        UserGetDTO dto = userService.findByCpf("123.456.789-10");

        assertEquals(dto, userGetDTO);

        verify(userRepository).findByCpf("123.456.789-10");
        verify(conversor).converterUser(any(User.class));

    }

    @Test
    @DisplayName("Deve Lançar exceção se usuário não for encontrado atraves do CPF passado por parâmetro.")
    void deveLancarExcecaoSeUsuarioNaoEncontrado(){
        when(userRepository.findByCpf("123.456.789-10")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findByCpf("123.456.789-10"));

        verify(userRepository).findByCpf("123.456.789-10");
        verifyNoInteractions(conversor);
    }

    @Test
    @DisplayName("Deve retornar uma lista com todos os usuarios cadastrados.")
    void deveRetornarListaCOmUsuarios(){
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();

        List<User> list = List.of(user1, user2, user3);

        Pageable pageable = PageRequest.of(0, 10);

        Page<User> page = new PageImpl<>(list);

        when(userRepository.findAll(pageable)).thenReturn(page);
        when(conversor.converterUser(any(User.class))).thenReturn(
                new UserGetDTO(1L, "Joaquim", 19, "jojo@gmail.com"),
                new UserGetDTO(2L, "mario", 29, "mario@gmail.com"),
                new UserGetDTO(3L, "Naldo", 29, "naldo@gmail.com"));


        Page<UserGetDTO> pageDTO = userService.listUser(pageable);

        assertEquals(pageDTO.getContent().size(), list.size());

        verify(userRepository).findAll();
        verify(conversor, times(3)).converterUser(any(User.class));
    }

    @Test
    @DisplayName("Deve retornar o usuário que tem o id passado por parâmetro.")
    void deveRetornarusuarioComIdCorrespondente(){
        User user = new User();
        UserGetDTO userGetDTO = new UserGetDTO(1L, "João Cancelo", 21, "Jojo@gmail.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(conversor.converterUser(any(User.class))).thenReturn(userGetDTO);

        UserGetDTO result = userService.findByIdController(1L);

        assertEquals(result.id(), userGetDTO.id());
        assertEquals(result.age(), userGetDTO.age());
        assertEquals(result.name(), userGetDTO.name());
        assertEquals(result.email(), userGetDTO.email());

        verify(userRepository).findById(1L);
        verify(conversor).converterUser(any(User.class));
    }

    @Test
    @DisplayName("Deve lançar exceção se usuário não for encontrado atraves do ID passado por parâmetro.")
    void deveLancarExcecaoSeUsuarioNaoEncontradoPeloId(){
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findById(1L));

        verify(userRepository).findById(1L);
        verifyNoInteractions(conversor);
    }

    @Test
    @DisplayName("Deve criar novo usuário com os atributos do PostDTO.")
    void deveCriarNovoUsuario(){
        UserPostDTO postDTO = new UserPostDTO("Joaquim Freitas", 25, "Jojo@gmail.com", "123.456.789-10");
        UserGetDTO getDto = new UserGetDTO(1L, "Joaquim Freitas", 25, "Jojo@gmail.com");

        when(userRepository.findByCpf("123.456.789-10")).thenReturn(Optional.empty());
        when(conversor.converterUser(any(User.class))).thenReturn(getDto);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        UserGetDTO userDto = userService.createUser(postDTO);

        verify(userRepository).save(captor.capture());

        User userSave = captor.getValue();

        assertEquals(postDTO.name(), userSave.getName());
        assertEquals(postDTO.age(), userSave.getAge());
        assertEquals(postDTO.email(), userSave.getEmail());
        assertEquals(postDTO.cpf(), userSave.getCpf());

        assertEquals(userSave.getName(), userDto.name());
        assertEquals(userSave.getEmail(), userDto.email());

        verify(conversor).converterUser(any(User.class));

    }

    @Test
    @DisplayName("Deve lançar exceção se usuario ja existir no banco")
    void deveLancarExcecaoParaUsuarioExistente(){
        User user = new User();
        UserPostDTO postDTO = new UserPostDTO("Joaquim Freitas", 25, "Jojo@gmail.com", "123.456.789-10");

        when(userRepository.findByCpf(postDTO.cpf())).thenReturn(Optional.of(user));

        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(postDTO));

        verify(userRepository).findByCpf(postDTO.cpf());
        verify(userRepository, never()).save(any(User.class));
        verifyNoInteractions(conversor);

    }

    @Test
    @DisplayName("Deve deletar usuário passado por parametro.")
    void deveDeletarUsuario(){
        User user = new User("Joaquim", "123.456.789-10", 21, "juju@gmail.com");

        when(userRepository.findByCpf(user.getCpf())).thenReturn(Optional.of(user));

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        userService.deleteUser("123.456.789-10");

        verify(userRepository).delete(captor.capture());

        User userDeleted = captor.getValue();

        assertEquals(user.getId(), userDeleted.getId());
        assertEquals(user.getName(), userDeleted.getName());
        assertEquals(user.getCpf(), userDeleted.getCpf());
        assertEquals(user.getEmail(), userDeleted.getEmail());
    }

}
