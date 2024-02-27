package ru.skillbox.homework4.commentary.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.homework4.commentary.dto.CommentariesDto;
import ru.skillbox.homework4.commentary.model.Commentary;
import ru.skillbox.homework4.commentary.repository.CommentaryRepository;
import ru.skillbox.homework4.exception.exceptions.ObjectNotFoundException;
import ru.skillbox.homework4.exception.exceptions.UnsupportedStateException;
import ru.skillbox.homework4.news.model.News;
import ru.skillbox.homework4.news.repository.NewsRepository;
import ru.skillbox.homework4.user.model.Role;
import ru.skillbox.homework4.user.model.User;
import ru.skillbox.homework4.user.repository.UserRepository;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import static ru.skillbox.homework4.commentary.mapper.CommentaryMapper.COMMENTARY_MAPPER;

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

        log.info("All commentaries were sent");
        return commentaryRepository.findAllByNewsId(newsId, page).stream()
                .map(COMMENTARY_MAPPER::setNewsAndAuthorsOfComments)
                .collect(Collectors.toList());
    }

    @Override
    public CommentariesDto findCommentaryById(Long newsId, Principal principal, Long commentaryId) {

        User user = checkNyUsername(principal.getName());
        News news = checkNewsById(newsId);
        Commentary commentary = checkCommentaryById(commentaryId);
        CommentariesDto commentariesDto = COMMENTARY_MAPPER.setNewsAndAuthorsOfComments(commentary);
        commentariesDto = COMMENTARY_MAPPER.setAuthorIdAndNewsId(commentariesDto, user, news);

        log.info("Commentary with id {} was sent", commentaryId);
        return commentariesDto;
    }

    @Override
    @Transactional
    public CommentariesDto createCommentary(Long newsId,
                                            Principal principal,
                                            CommentariesDto commentariesDto) {

        News news = checkNewsById(newsId);
        User user = checkNyUsername(principal.getName());
        Commentary commentary = new Commentary();
        commentary.setNews(news);
        commentary.setUser(user);
        commentary.setCommentaryText(commentariesDto.getCommentaryText());

        commentaryRepository.save(commentary);

        CommentariesDto commentariesDtoResponse = COMMENTARY_MAPPER.setNewsAndAuthorsOfComments(commentary);
        commentariesDtoResponse = COMMENTARY_MAPPER.setAuthorIdAndNewsId(commentariesDtoResponse, user, news);

        log.info("Commentary with id {} was created", commentary.getId());
        return commentariesDtoResponse;
    }

    @Override
    @Transactional
    public CommentariesDto updateCommentaryById(Long newsId,
                                                Long commentaryId,
                                                Principal principal,
                                                CommentariesDto commentariesDto) {

        News newsDb = checkNewsById(newsId);
        Commentary commentaryDb = checkCommentaryById(commentaryId);
        User user = checkNyUsername(principal.getName());

        for (Role role : user.getRole()) {
            if (!role.getAuthority().toString().equals("ROLE_ADMIN") ||
                    !role.getAuthority().toString().equals("ROLE_MODERATOR")){
                if (!commentaryDb.getUser().getId().equals(user.getId())) {
                    log.warn("News with id {} was not found", newsId);
                    throw new UnsupportedStateException("You are not commentary with id: %s owner!".formatted(commentaryDb.getUser().getId()));
                }
            }
        }


        if (commentariesDto != null) {

            if (commentariesDto.getCommentaryText() != null) {
                commentaryDb.setCommentaryText(commentariesDto.getCommentaryText());
            }

            commentaryRepository.save(commentaryDb);
            log.info("Commentary with id {} was created", commentaryDb.getId());
        } else {
            log.warn("no item for update");
            throw new ObjectNotFoundException("no item for update");
        }

        CommentariesDto commentariesDtoResponse = COMMENTARY_MAPPER.setNewsAndAuthorsOfComments(commentaryDb);
        commentariesDtoResponse = COMMENTARY_MAPPER.setAuthorIdAndNewsId(commentariesDtoResponse, user, newsDb);

        log.info("Commentary with id {} was created", commentaryDb.getId());
        return commentariesDtoResponse;
    }

    @Override
    @Transactional
    public CommentariesDto deleteCommentaryById(Long newsId,
                                                Principal principal,
                                                Long commentaryId) {

        Commentary commentary = checkCommentaryById(commentaryId);
        User user = checkNyUsername(principal.getName());

        for (Role role : user.getRole()) {
            if (!role.getAuthority().toString().equals("ROLE_ADMIN") ||
                    !role.getAuthority().toString().equals("ROLE_MODERATOR")){
                if (!commentary.getUser().getId().equals(user.getId())) {

                    throw new UnsupportedStateException("You are not commentary with id: %s owner!".formatted(commentary.getUser().getId()));
                }
            }
        }

        commentaryRepository.deleteById(commentaryId);

        log.info("Commentary with id {} was deleted", commentaryId);
        return COMMENTARY_MAPPER.setNewsAndAuthorsOfComments(commentary);
    }

    private Commentary checkCommentaryById(Long commentaryId) {
        log.info("And send from method %s at time - ".formatted("checkCommentaryById") + LocalDateTime.now());
        return commentaryRepository.findById(commentaryId)
                .orElseThrow(() -> {
                    log.warn("Commentary with id {} was not found", commentaryId);
                    throw new ObjectNotFoundException("Commentary with id: %s was not found".formatted(commentaryId));
                });
    }

    private News checkNewsById(Long newsId) {
        log.info("And send from method %s at time - ".formatted("checkNewsById") + LocalDateTime.now());
        return newsRepository.findById(newsId).orElseThrow(() -> {
            log.warn("News with id {} is not found", newsId);
            throw new ObjectNotFoundException("News with id %s was not found".formatted(newsId));
        });
    }

    private User checkNyUsername(String name) {
        log.info("And send from method %s at time - ".formatted("checkNyUsername") + LocalDateTime.now());
        return userRepository.findByUsername(name).orElseThrow(() -> {

            log.warn("User with username {} was not found", name);
            throw new ObjectNotFoundException("User with username %s was not found".formatted(name));
        });
    }
}
