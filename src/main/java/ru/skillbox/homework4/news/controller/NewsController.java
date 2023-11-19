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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.homework4.commentary.service.CommentaryService;
import ru.skillbox.homework4.common.Create;
import ru.skillbox.homework4.common.Update;
import ru.skillbox.homework4.news.dto.NewsDto;
import ru.skillbox.homework4.news.service.NewsService;
import ru.skillbox.homework4.user.service.UserService;
import java.util.List;
import static ru.skillbox.homework4.common.Header.HEADER;

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewsDto createNews(@RequestHeader(HEADER) Long userId,
                              @Validated(Create.class) @RequestBody NewsDto newsDto) {

        return newsService.createNews(userId, newsDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NewsDto updateNewsById(@RequestHeader(HEADER) Long userId,
                                  @PathVariable(name = "id") Long newsId,
                                  @PathVariable Long categoryId,
                                  @Validated(Update.class) @RequestBody NewsDto newsDto) {

         return newsService.updateNewsById(userId, newsId, categoryId, newsDto);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public NewsDto deleteNewsById(@PathVariable long id) {

        return newsService.deleteNewsById(id);
    }
}
