package ru.skillbox.homework4.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.homework4.common.Create;
import ru.skillbox.homework4.user.dto.UserDto;
import ru.skillbox.homework4.user.model.RoleType;
import ru.skillbox.homework4.user.service.UserServiceImpl;

@Validated
@RestController
@RequestMapping(path = "/register")
@RequiredArgsConstructor
public class PublicController {

    private final UserServiceImpl userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@Validated(Create.class) @RequestBody UserDto userDto,
                              @RequestParam RoleType type) {

        return userService.create(userDto, type);
    }
}
