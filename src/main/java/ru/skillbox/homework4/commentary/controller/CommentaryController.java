package ru.skillbox.homework4.commentary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.homework4.commentary.service.CommentaryService;
import ru.skillbox.homework4.news.service.NewsService;
import ru.skillbox.homework4.user.service.UserServiceImpl;

@Validated
@RestController
@RequestMapping(path = "/news/commentaries/")
@RequiredArgsConstructor
public class CommentaryController {

    private final UserServiceImpl userService;

    private final NewsService newsService;

    private final CommentaryService commentaryService;

    /*
     @GetMapping findAll()

     @GetMapping("/{Id}" findCommentary(@PathVariable Long id ?))

     @PostMapping

     @PutMapping("/{id}")

     @DeleteMapping("/{id}")
    */

}
