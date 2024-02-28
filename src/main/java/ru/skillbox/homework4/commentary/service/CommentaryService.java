package ru.skillbox.homework4.commentary.service;

import org.springframework.data.domain.PageRequest;
import ru.skillbox.homework4.commentary.dto.CommentariesDto;

import java.security.Principal;
import java.util.List;

public interface CommentaryService {

    List<CommentariesDto> findAllCommentary(Long newsId, PageRequest page);

    CommentariesDto findCommentaryById(Long newsId, Principal principal, Long commentaryId);

    CommentariesDto createCommentary(Long newsId, Principal principal, CommentariesDto commentariesDto);

    CommentariesDto updateCommentaryById(Long newsId, Long commentaryId, Principal principal, CommentariesDto commentariesDto);

    CommentariesDto deleteCommentaryById(Long newsId, Principal principal, Long commentaryId);
}
