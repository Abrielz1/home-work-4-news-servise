package ru.skillbox.homework4.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.homework4.exception.exceptions.ObjectNotFoundException;
import ru.skillbox.homework4.exception.exceptions.UnsupportedStateException;
import ru.skillbox.homework4.user.dto.UserDto;
import ru.skillbox.homework4.user.mapper.UserMapper;
import ru.skillbox.homework4.user.model.Role;
import ru.skillbox.homework4.user.model.RoleType;
import ru.skillbox.homework4.user.model.User;
import ru.skillbox.homework4.user.repository.UserRepository;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    @Override
    public List<UserDto> findAll(Pageable page) {

        log.info("All users were sent");

        return userRepository.findAll(page).getContent()
                .stream().map(UserMapper.USER_MAPPER::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getById(Long id, Principal principal) {

        User userCheck = userRepository.findById(id).orElseThrow(() -> {
            log.warn("User with id {} was not found", id);

            throw new ObjectNotFoundException("User not found");
        });

        User userByName = getMyId(principal);

        for (Role role: userCheck.getRole()) {
            if (role.toString().equals(RoleType.ROLE_USER.toString())) {
                if (!(userByName.getId().equals(id))) {
                    throw new UnsupportedStateException("You not owner!");
                }
            }
        }

        return UserMapper.USER_MAPPER.toUserDto(userCheck);
    }


    @Override
    @Transactional
    public UserDto create(UserDto userDto, RoleType type) {

        User user = new User();
        Role role = Role.from(type);

        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        role.setUser(user);
        user.setRole(Collections.singletonList(role));
        user.setPassword(encoder.encode(userDto.getPassword()));

        userRepository.save(user);
        log.info("User was created");

        return UserMapper.USER_MAPPER.toUserDto(user);
    }

    @Override
    @Transactional
    public UserDto update(Long id, UserDto userDto, Principal principal) {

        User userBd = checkUserById(id);

        User userByName = getMyId(principal);

        for (Role role: userBd.getRole()) {
            if (role.toString().equals(RoleType.ROLE_USER.toString())) {
                if (!(userByName.getId().equals(id))) {
                    throw new UnsupportedStateException("You not owner!");
                }
            }
        }

        if (userDto.getEmail() != null) {
            userBd.setEmail(userDto.getEmail());
        }

        if (userDto.getUsername() != null) {
            userBd.setUsername(userDto.getUsername());
        }

        log.info("User updated");
        userRepository.save(userBd);

        return UserMapper.USER_MAPPER.toUserDto(userBd);
    }

    @Override
    @Transactional
    public UserDto delete(Long id, Principal principal) {

        User user = checkByIdInDb(id);

        User userByName = getMyId(principal);

        for (Role role: user.getRole()) {
            if (role.toString().equals(RoleType.ROLE_USER.toString())) {
                if (!(userByName.getId().equals(id))) {
                    throw new UnsupportedStateException("You not owner!");
                }
            }
        }

        userRepository.delete(user);
        log.info("User with id {} was deleted", id);

        return UserMapper.USER_MAPPER.toUserDto(user);
    }

    private User checkUserById(Long userId) {

        return userRepository.findById(userId).orElseThrow(() -> {
            log.warn("User with id {} was not found", userId);

            throw new ObjectNotFoundException("Пользователь не найден");
        });
    }

    public User findByName(String username) {

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Username name was not found"));
    }

        private User checkByIdInDb(Long id) {
        return userRepository.findById(id).orElseThrow(() -> {
            throw new ObjectNotFoundException("User was not found in db");

        });
    }

    private User getMyId(Principal principal) {

        return findByName(principal.getName());
    }
}
