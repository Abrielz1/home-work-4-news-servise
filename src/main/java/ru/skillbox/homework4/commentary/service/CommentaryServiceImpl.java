package ru.skillbox.homework4.commentary.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.homework4.commentary.dto.CommentariesDto;
import ru.skillbox.homework4.commentary.mapper.CommentaryMapper;
import ru.skillbox.homework4.commentary.model.Commentary;
import ru.skillbox.homework4.commentary.repository.CommentaryRepository;
import ru.skillbox.homework4.exception.exceptions.ObjectNotFoundException;
import ru.skillbox.homework4.news.model.News;
import ru.skillbox.homework4.news.repository.NewsRepository;
import ru.skillbox.homework4.user.model.User;
import ru.skillbox.homework4.user.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentaryServiceImpl implements CommentaryService {


    private final CommentaryRepository commentaryRepository;

    private final UserRepository userRepository;

    private final NewsRepository newsRepository;

    @Override
    public List<CommentariesDto> findAllCommentary(Long newsId, PageRequest page) {

        log.info("All users commentary was sent");

        return commentaryRepository.getListOfCommentariesByNewsId(newsId, page).stream()
                .map(CommentaryMapper.COMMENTARY_MAPPER::CommentaryToCommentariesDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentariesDto findCommentaryById(Long newsId, Long commentaryId) {

        News news = checkNewsById(newsId);
        Commentary commentary = checkCommentaryById(commentaryId);

        return CommentaryMapper.COMMENTARY_MAPPER.CommentaryToCommentariesDto(commentary);
    }

    @Override
    @Transactional
    public CommentariesDto createCommentary(Long newsId,
                                            Long userId,
                                            CommentariesDto commentariesDto) {

        News news = checkNewsById(newsId);
        User user = checkUserById(userId);

        Commentary commentary = new Commentary();
        commentary.setNews(news);
        commentary.setUser(user);

        commentaryRepository.save(commentary);

        return CommentaryMapper.COMMENTARY_MAPPER.CommentaryToCommentariesDto(commentary);
    }

    @Override
    @Transactional
    public CommentariesDto updateCommentaryById(Long newsId,
                                                Long commentaryId,
                                                Long userId,
                                                CommentariesDto commentariesDto) {

        News newsDb = checkNewsById(newsId);
        User userDb = checkUserById(userId);
        Commentary commentaryDb = checkCommentaryById(commentaryId);

        if (newsDb != null) {

        }

        if (userDb != null) {

        }

        if (commentariesDto != null) {

            if (commentariesDto.getCommentaryText() !=null) {
                commentaryDb.setCommentaryText(commentariesDto.getCommentaryText());
            }
        }

        return null;
    }


    private Commentary checkCommentaryById(Long commentaryId) {

        return commentaryRepository.findById(commentaryId)
                .orElseThrow(() -> {
                    log.warn("Commentary with id {} was not found", commentaryId);
                    throw new ObjectNotFoundException("Commentary was not found");
                });
    }

    private User checkUserById(Long userId) {

        return userRepository.findById(userId).orElseThrow(() -> {
            log.warn("User with id {} is not found", userId);
            throw new ObjectNotFoundException("User was not found");
        });
    }

    private News checkNewsById(Long newsId) {

        return newsRepository.findById(newsId).orElseThrow(() -> {
            log.warn("News with id {} is not found", newsId);
            throw new ObjectNotFoundException("News was not found");
        });
    }
}
