package ru.skillbox.homework4.commentary.service;

import org.springframework.data.domain.PageRequest;
import ru.skillbox.homework4.commentary.dto.CommentariesDto;
import java.util.List;

public interface CommentaryService {

    List<CommentariesDto> findAllCommentary(PageRequest page);
}
