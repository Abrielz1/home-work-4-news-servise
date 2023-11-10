package ru.skillbox.homework4.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.homework4.service.CommentaryService;
import ru.skillbox.homework4.service.NewsService;
import ru.skillbox.homework4.service.UserService;

@RestController
@RequestMapping(path = "/news")
@RequiredArgsConstructor
public class NewsController {

    private final UserService userService;

    private final NewsService newsService;

    private final CommentaryService commentaryService;

    /*
     @GetMapping findAll()

     @GetMapping("/{Id}" findNews(@PathVariable Long id ?))

     @PostMapping

     @PutMapping("/{id}")

     @DeleteMapping("/{id}")
    */
}
