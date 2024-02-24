package ru.skillbox.homework4.user.service;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import ru.skillbox.homework4.user.dto.UserDto;
import ru.skillbox.homework4.user.model.RoleType;
import ru.skillbox.homework4.user.model.User;

import java.security.Principal;
import java.util.List;

public interface UserService {

    List<UserDto> findAll(Pageable page);

    UserDto getById(Long id, Principal principal);

    UserDto create(UserDto userDto, RoleType type);

    UserDto update(Long id, UserDto userDto, Principal principal);

    UserDto delete(Long id, Principal principal);

    User findByName(String userName);
}
