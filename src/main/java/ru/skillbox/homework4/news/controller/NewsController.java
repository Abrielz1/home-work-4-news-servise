package ru.skillbox.homework4.news.controller;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.homework4.commentary.service.CommentaryService;
import ru.skillbox.homework4.news.dto.NewsDto;
import ru.skillbox.homework4.news.model.News;
import ru.skillbox.homework4.news.service.NewsService;
import ru.skillbox.homework4.user.dto.UserDto;
import ru.skillbox.homework4.user.service.UserService;
import java.util.List;

@Validated
@RestController
@RequestMapping(path = "/news")
@RequiredArgsConstructor
public class NewsController {

    private final UserService userService;

    private final NewsService newsService;

    private final CommentaryService commentaryService;


     @GetMapping
    public List<NewsDto> findAll(@PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                 @Positive @RequestParam(defaultValue = "10") Integer size) {

         PageRequest page = PageRequest.of(from / size, size);

         return newsService.findAll(page);
     }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NewsDto findNewsById(@PathVariable long id) {
        return newsService.findNewsById(id);
    }

/*
     @GetMapping("/{Id}" findNewsById(@PathVariable Long id ?))

     @PostMapping

     @PutMapping("/{id}")

     @DeleteMapping("/{id}")
    */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public NewsDto deleteNewsById(@PathVariable long id) {
        return newsService.deleteNewsById(id);
    }
}
