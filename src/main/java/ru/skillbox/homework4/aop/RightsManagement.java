package ru.skillbox.homework4.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.skillbox.homework4.commentary.repository.CommentaryRepository;
import ru.skillbox.homework4.exception.exceptions.UnsupportedStateException;
import ru.skillbox.homework4.news.repository.NewsRepository;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RightsManagement {

    private final CommentaryRepository commentaryRepository;

    private final NewsRepository newsRepository;

    @Before(value = "execution(* ru.skillbox.homework4.news.controller.NewsController.updateNewsById(..))" +
            "&& args(newsId, ..)" +
            " && args(userId, ..)", argNames = "newsId,userId")
    public void userRightsManagementOnUpdate(@PathVariable(name = "newsId") Long newsId,
                                             @RequestParam("userId") Long userId) {

        if (newsRepository.existsByIdAndUserId(newsId, userId)) {

            log.error("User with id: {} trying to reach someone else property with id: {}", userId, newsId);
            throw new UnsupportedStateException("You not owner!");
        }
    }

    @Before(value = "execution(* ru.skillbox.homework4.news.controller.NewsController.deleteNewsById(..))" +
            "&& args(newsId, ..)" +
            " && args(userId, ..)", argNames = "newsId,userId")
    public void userRightsManagementOnDelete(@PathVariable(name = "newsId") Long newsId,
                                             @RequestParam("userId") Long userId) {


        if (newsRepository.existsByIdAndUserId(newsId, userId)) {

            log.error("User with id: {} trying to reach someone else property with id: {}", userId, newsId);
            throw new UnsupportedStateException("You not owner!");
        }
    }

    @Before(value = "execution(* ru.skillbox.homework4.commentary.controller.CommentaryController.updateCommentaryById(..))" +
            "&& args(newsId, ..)" +
            " && args(userId, ..)" +
            " && args(commentaryId, ..)", argNames = "newsId,userId,commentaryId")
    public void userRightsManagementOnUpdateCommentary(@PathVariable(name = "newsId") Long newsId,
                                                       @RequestParam("userId") Long userId,
                                                       @PathVariable Long commentaryId) {

        if (commentaryRepository.existsByIdAndUserId(commentaryId, userId)) {

            log.error("User with id: {} trying to reach someone else property with id: {}", userId, commentaryId);
            throw new UnsupportedStateException("You not owner!");
        }
    }

    @Before(value = "execution(* ru.skillbox.homework4.commentary.controller.CommentaryController.deleteCommentaryById(..))" +
            "&& args(newsId, ..)" +
            " && args(userId, ..)" +
            " && args(commentaryId, ..)", argNames = "newsId,userId,commentaryId")
    public void userRightsManagementOnDeleteCommentary(@PathVariable(name = "newsId") Long newsId,
                                                       @RequestParam("userId") Long userId,
                                                       @PathVariable Long commentaryId) {

        if (commentaryRepository.existsByIdAndUserId(commentaryId, userId)) {

            log.error("User with id: {} trying to reach someone else property with id: {}", userId, commentaryId);
            throw new UnsupportedStateException("You not owner!");
        }
    }
}
