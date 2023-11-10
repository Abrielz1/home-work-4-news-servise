package ru.skillbox.homework4.user.controller;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.homework4.common.Create;
import ru.skillbox.homework4.common.Update;
import ru.skillbox.homework4.user.dto.UserDto;
import ru.skillbox.homework4.user.service.UserServiceImpl;
import java.util.List;

@Validated
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping
    public List<UserDto> getAllUsers(@PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                     @Positive @RequestParam(defaultValue = "10") Integer size) {
        PageRequest page = PageRequest.of(from / size, size);
        return userService.findAll(page);
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable long id) {
        return userService.getById(id);
    }

      @PostMapping
    public UserDto createUser(@Validated(Create.class) @RequestBody UserDto userDto) {
        return userService.create(userDto);
    }

       @PutMapping("/{id}")
    public UserDto updateUserById(@PathVariable long id, @Validated(Update.class) @RequestBody UserDto userDto) {
        return userService.update(id, userDto);
    }

     @DeleteMapping("/{id}")
    public UserDto delete(@PathVariable long id) {
        return userService.delete(id);
    }

}
