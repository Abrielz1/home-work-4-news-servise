package ru.skillbox.homework4.user.service;

import org.springframework.data.domain.Pageable;
import ru.skillbox.homework4.user.dto.UserDto;
import ru.skillbox.homework4.user.model.RoleType;
import ru.skillbox.homework4.user.model.User;

import java.util.List;

public interface UserService {

    List<UserDto> findAll(Pageable page);

    UserDto getById(Long id);

    UserDto create(UserDto userDto, RoleType type);

    UserDto update(Long id, UserDto userDto);

    UserDto delete(Long id);

    User findByName(String userName);
}
