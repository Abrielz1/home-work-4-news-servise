package ru.skillbox.homework4.user.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import org.junit.jupiter.api.AfterEach;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.when;
import static ru.skillbox.homework4.user.mapper.UserMapper.USER_MAPPER;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import java.util.Optional;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ru.skillbox.homework4.exception.exceptions.ObjectNotFoundException;
import ru.skillbox.homework4.user.dto.UserDto;
import ru.skillbox.homework4.user.model.User;
import ru.skillbox.homework4.user.repository.UserRepository;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl service;

    private User user1;

    @BeforeEach
    void beforeEach() {
        user1 = User.builder()
                .id(1L)
                .name("User1 name")
                .email("user1@mail.com")
                .build();
    }

    @AfterEach
    void afterEach() {
        userRepository.deleteAll();
    }

    // В этом блоке тестим стандартное поведение CRUD методов

    @Test
    void findAllTest() {

        PageRequest p = PageRequest.of(0, 20);

        when(userRepository.findAllUsers(p)).thenReturn(List.of(user1));

//        when(userRepository.findAll(p)).thenReturn((Page<User>) List.of(user1)); со стандарным findAll(PageRequest) не пашет

        List<UserDto> userDto = service.findAll(p);

        assertEquals(1L, userDto.size());
        assertEquals(1L, userDto.get(0).getId());
        assertEquals("User1 name", userDto.get(0).getName());
        assertEquals("user1@mail.com", userDto.get(0).getEmail());
    }

    @Test
    void getByIdTest() {

        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(user1));

        UserDto userDto = service.getById(user1.getId());

        assertEquals(1L, userDto.getId());
        assertEquals("User1 name", userDto.getName());
        assertEquals("user1@mail.com", userDto.getEmail());
    }

    @Test
    void getAllUsersWhenUserFoundThenReturnedUser() {

        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(user1));

        when(userRepository.save(any(User.class)))
                .thenReturn(user1);

        UserDto userDto = USER_MAPPER.toUserDto(user1);
        service.update(userDto.getId(), userDto);

        assertEquals(1, userDto.getId());
        assertEquals("User1 name", userDto.getName());
        assertEquals("user1@mail.com", userDto.getEmail());
    }

    @Test
    void createUserTest() {

        when(userRepository.save(any(User.class))).thenReturn(user1);

        UserDto userDto = service.create(
                USER_MAPPER.toUserDto(user1));

        assertEquals(1L, userDto.getId());
        assertEquals("User1 name", userDto.getName());
        assertEquals("user1@mail.com", userDto.getEmail());
    }

    @Test
    void updateUserNameAndEmailFormatTest() {

        when(userRepository.findById(anyLong()))
                .thenReturn(Optional
                        .ofNullable(user1));

        when(userRepository.save(any(User.class)))
                .thenReturn(user1);

        user1.setName("User0 name");
        user1.setEmail("user1@gmail.com");

        UserDto userDto = USER_MAPPER.toUserDto(user1);
        service.update(userDto.getId(), userDto);

        assertEquals(1L, userDto.getId());
        assertEquals("User0 name", userDto.getName());
        assertEquals("user1@gmail.com", userDto.getEmail());
    }

    @Test
    void deleteTest() {

        when(userRepository.findById(anyLong()))
                .thenReturn(Optional
                        .ofNullable(user1));

        UserDto userDto = service.delete(user1.getId());

        assertEquals(1, userDto.getId());
        assertEquals("User1 name", userDto.getName());
        assertEquals("user1@mail.com", userDto.getEmail());
    }

    //в этом блоке будем ломать программу

    @Test
    void updateUserWithNoUser() {

        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        UserDto userDto = USER_MAPPER.toUserDto(user1);
        userDto.setId(100000L);

        ObjectNotFoundException exc = assertThrows(ObjectNotFoundException.class,
                () -> service.update(1L, userDto)
        );

        assertEquals("Пользователь не найден", exc.getMessage());
    }

    @Test
    void getAllUsersWhenUserFoundThenUserNotFoundExceptionThrown() {

        Long userId = 0L;

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());
        assertThrows(ObjectNotFoundException.class, () -> service.getById(userId));
    }

    @Test
    void getUserWrongIdTest() {

        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class,
                () -> service.getById(user1.getId()));

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void deleteUserTestWithNoUser() {

        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        UserDto userDto = USER_MAPPER.toUserDto(user1);
        userDto.setId(10L);

        ObjectNotFoundException exc = assertThrows(ObjectNotFoundException.class,
                () -> service.delete(1L)
        );

        assertEquals("Пользователь не найден", exc.getMessage());
    }
}