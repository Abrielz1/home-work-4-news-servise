package ru.skillbox.homework4.commentary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.homework4.news.service.NewsService;
import ru.skillbox.homework4.user.service.UserService;

@RestController
@RequestMapping(path = "/news/commentaries/")
@RequiredArgsConstructor
public class CommentaryController {

    private final UserService userService;

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
