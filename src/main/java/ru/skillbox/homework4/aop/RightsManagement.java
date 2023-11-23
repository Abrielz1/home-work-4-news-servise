package ru.skillbox.homework4.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.skillbox.homework4.commentary.service.CommentaryService;
import ru.skillbox.homework4.exception.exceptions.UnsupportedStateException;
import ru.skillbox.homework4.news.service.NewsService;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RightsManagement {

    private final NewsService newsService;

    private final CommentaryService commentaryService;

    @Before("execution(* ru.skillbox.homework4.news.controller.NewsController.updateNewsById(..))" +
            "&& args(newsId, ..)" +
            " && args(userId, ..)")
    public void userRightsManagementOnUpdate(@PathVariable(name = "newsId") Long newsId,
                                             @RequestParam("userId") Long userId) {
        if (!newsService.checkNewsOwner(newsId, userId)) {
            throw new UnsupportedStateException("You not owner!");
        }
    }

    @Before("execution(* ru.skillbox.homework4.news.controller.NewsController.deleteNewsById(..))" +
            "&& args(newsId, ..)" +
            " && args(userId, ..)")
    public void userRightsManagementOnDelete(@PathVariable(name = "newsId") Long newsId,
                                             @RequestParam("userId") Long userId) {
        if (!newsService.checkNewsOwner(newsId, userId)) {
            throw new UnsupportedStateException("You not owner!");
        }
    }

    @Before("execution(* ru.skillbox.homework4.commentary.controller.CommentaryController.updateCommentaryById(..))" +
            "&& args(newsId, ..)" +
            " && args(userId, ..)" +
            " && args(commentaryId, ..)")
    public void userRightsManagementOnUpdateCommentary(@PathVariable(name = "newsId") Long newsId,
                                                       @RequestParam("userId") Long userId,
                                                       @PathVariable Long commentaryId) {
        if (!commentaryService.checkCommentaryOwner(newsId, userId, commentaryId)) {
            throw new UnsupportedStateException("You not owner!");
        }
    }

    @Before("execution(* ru.skillbox.homework4.commentary.controller.CommentaryController.deleteCommentaryById(..))" +
            "&& args(newsId, ..)" +
            " && args(userId, ..)" +
            " && args(commentaryId, ..)")
    public void userRightsManagementOnDeleteCommentary(@PathVariable(name = "newsId") Long newsId,
                                                       @RequestParam("userId") Long userId,
                                                       @PathVariable Long commentaryId) {
        if (!commentaryService.checkCommentaryOwner(newsId, userId, commentaryId)) {
            throw new UnsupportedStateException("You not owner!");
        }
    }
}
