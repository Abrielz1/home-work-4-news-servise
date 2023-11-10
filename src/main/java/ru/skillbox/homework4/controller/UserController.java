package ru.skillbox.homework4.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.homework4.service.UserService;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /*

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable long id) {
        return userService.getById(id);
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
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
    */
}
