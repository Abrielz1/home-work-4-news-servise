package ru.skillbox.homework4.commentary.controller;

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
import ru.skillbox.homework4.commentary.dto.CommentariesDto;
import ru.skillbox.homework4.commentary.service.CommentaryService;
import ru.skillbox.homework4.common.Create;
import ru.skillbox.homework4.common.Update;
import ru.skillbox.homework4.news.service.NewsServiceImpl;
import ru.skillbox.homework4.user.service.UserServiceImpl;
import java.util.List;

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
                @PathVariable(name = "newsId") Long newsId,
                @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                @Positive @RequestParam(defaultValue = "10") Integer size) {

        PageRequest page = PageRequest.of(from / size, size);

        return commentaryService.findAllCommentary(newsId, page);
    }

    @GetMapping("/commentaries/{commentaryId}")
    public CommentariesDto findCommentaryById(
            @PathVariable(name = "newsId") Long newsId,
            @PathVariable Long commentaryId) {

       return commentaryService.findCommentaryById(newsId, commentaryId);
    }

    @PostMapping("/commentaries")
    public CommentariesDto createCommentary(
            @PathVariable(name = "newsId") Long newsId,
            @RequestParam(name = "userId") Long userId,
            @Validated(Create.class) @RequestBody CommentariesDto commentariesDto) {

        return commentaryService.createCommentary(userId, newsId, commentariesDto);
    }

    @PutMapping("/commentaries/{commentaryId}")
    public CommentariesDto updateCommentaryById(
            @PathVariable(name = "newsId") Long newsId,
            @RequestParam(name = "userId") Long userId,
            @PathVariable Long commentaryId,
            @Validated(Update.class) @RequestBody CommentariesDto commentariesDto) {

        return commentaryService.updateCommentaryById(newsId, commentaryId, userId, commentariesDto);
    }

    @DeleteMapping("/commentaries/{commentaryId}")
    public CommentariesDto deleteCommentaryById(
//            @RequestParam(name = "userId") Long userId, userId, newsId,
            @PathVariable(name = "newsId") Long newsId,
            @PathVariable Long commentaryId) {

        return commentaryService.deleteCommentaryById(commentaryId);
    }
}


