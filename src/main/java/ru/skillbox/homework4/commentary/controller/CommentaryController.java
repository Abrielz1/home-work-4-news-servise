package ru.skillbox.homework4.commentary.controller;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.homework4.commentary.dto.CommentariesDto;
import ru.skillbox.homework4.commentary.model.Commentary;
import ru.skillbox.homework4.commentary.service.CommentaryService;
import ru.skillbox.homework4.common.Create;
import ru.skillbox.homework4.common.Update;
import ru.skillbox.homework4.news.service.NewsServiceImpl;
import ru.skillbox.homework4.user.service.UserServiceImpl;

import java.util.List;

import static ru.skillbox.homework4.common.Header.HEADER;

@Validated
@RestController
@RequestMapping(path = "/news/{newsId}")
@RequiredArgsConstructor
public class CommentaryController {

    private final UserServiceImpl userService;

    private final NewsServiceImpl newsService;

    private final CommentaryService commentaryService;

    @GetMapping("/commentaries")
    public List<CommentariesDto> findAll(
                @PathVariable Long newsId,
                @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                @Positive @RequestParam(defaultValue = "10") Integer size) {

        PageRequest page = PageRequest.of(from / size, size);

        return commentaryService.findAllCommentary(newsId, page);
    }

    @GetMapping("/commentaries/{commentaryId}")
    public CommentariesDto findCommentaryById(
            @PathVariable Long newsId,
            @PathVariable Long commentaryId) {

       return commentaryService.findCommentaryById(newsId, commentaryId);
    }

    @PostMapping
    public CommentariesDto createCommentary(
            @RequestHeader(HEADER)  Long userId,
            @PathVariable Long newsId,
            @Validated(Create.class) @RequestBody CommentariesDto commentariesDto) {

        return commentaryService.createCommentary(userId, newsId, commentariesDto);
    }

    @PutMapping("/commentaries/{commentaryId}")
    public CommentariesDto updateCommentaryById(
            @RequestHeader(HEADER)  Long userId,
            @PathVariable Long newsId,
            @PathVariable Long commentaryId,
            @Validated(Update.class) @RequestBody CommentariesDto commentariesDto) {

        return commentaryService.updateCommentaryById(userId, newsId, commentaryId, commentariesDto);
    }

    /*


     @DeleteMapping("/{id}")
    */

}
