package ru.skillbox.homework4.commentary.controller;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.homework4.commentary.dto.CommentariesDto;
import ru.skillbox.homework4.commentary.model.Commentary;
import ru.skillbox.homework4.commentary.service.CommentaryService;
import ru.skillbox.homework4.news.service.NewsServiceImpl;
import ru.skillbox.homework4.user.service.UserServiceImpl;

import java.util.List;

@Validated
@RestController
@RequestMapping(path = "/news/commentaries/")
@RequiredArgsConstructor
public class CommentaryController {

    private final UserServiceImpl userService;

    private final NewsServiceImpl newsService;

    private final CommentaryService commentaryService;

    @GetMapping
    public List<CommentariesDto> findAll(@PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                         @Positive @RequestParam(defaultValue = "10") Integer size) {

        PageRequest page = PageRequest.of(from / size, size);

        return commentaryService.findAllCommentary(page);
    }

    /*

     ) {


     @GetMapping("/{Id}" findCommentary(@PathVariable Long id ?))

     @PostMapping

     @PutMapping("/{id}")

     @DeleteMapping("/{id}")
    */

}
