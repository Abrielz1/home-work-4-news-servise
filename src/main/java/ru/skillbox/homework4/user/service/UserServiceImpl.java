package ru.skillbox.homework4.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.homework4.exception.exceptions.ObjectNotFoundException;
import ru.skillbox.homework4.user.dto.UserDto;
import ru.skillbox.homework4.user.mapper.UserMapper;
import ru.skillbox.homework4.user.model.User;
import ru.skillbox.homework4.user.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDto> findAll(Pageable page) {

        log.info("All users sent");

       return userRepository.findAll(page)
               .stream().map(UserMapper.USER_MAPPER::toUserDto)
               .collect(Collectors.toList());
    }

    @Override
    public UserDto getById(long id) {

       return UserMapper.USER_MAPPER.toUserDto(userRepository.findById(id).orElseThrow( () -> {
           log.warn("User with id {} not found", id);

           throw new ObjectNotFoundException("User not found");
       }));
    }


    @Override
    @Transactional
    public UserDto create(UserDto userDto) {

        User user = UserMapper.USER_MAPPER.toUser(userDto);
        userRepository.save(user);
        log.info("User created");

        return UserMapper.USER_MAPPER.toUserDto(user);
    }

    @Override
    @Transactional
    public UserDto update(long id, UserDto userDto) {

        User user = userRepository.findById(id).orElseThrow(() -> {
            log.warn("Пользователь с id {} не найден", id);
            throw new ObjectNotFoundException("Пользователь не найден");
        });

        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }

        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }

        log.info("User updated");
        userRepository.save(user);

        return UserMapper.USER_MAPPER.toUserDto(user);
    }

    @Override
    @Transactional
    public UserDto delete(long id) {

        User user = userRepository.findById(id).orElseThrow(() -> {
            log.warn("User with id {} not found", id);
            throw new ObjectNotFoundException("User not found");
        });

        userRepository.delete(user);
        log.info("User with id {} deleted", id);

        return UserMapper.USER_MAPPER.toUserDto(user);
    }

    // todo: приывиный метод для провекри наличия и выброса исключения в случае отсутствия
}
