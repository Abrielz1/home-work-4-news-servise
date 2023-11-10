package ru.skillbox.homework4.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.skillbox.homework4.user.dto.UserDTO;
import ru.skillbox.homework4.user.mapper.UserMapper;
import ru.skillbox.homework4.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserDTO> findAll(Pageable page) {
       return userRepository.findAll(page)
               .stream().map(UserMapper.USER_MAPPER::toUserDto)
               .collect(Collectors.toList());
    }


}
