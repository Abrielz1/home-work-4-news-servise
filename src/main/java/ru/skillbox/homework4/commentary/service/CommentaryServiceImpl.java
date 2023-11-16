package ru.skillbox.homework4.commentary.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.skillbox.homework4.commentary.dto.CommentariesDto;
import ru.skillbox.homework4.commentary.mapper.CommentaryMapper;
import ru.skillbox.homework4.commentary.repository.CommentaryRepository;
import ru.skillbox.homework4.news.repository.NewsRepository;
import ru.skillbox.homework4.user.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentaryServiceImpl implements CommentaryService {


    private final CommentaryRepository commentaryRepository;

    private final UserRepository userRepository;

    private final NewsRepository newsRepository;

    @Override
    public List<CommentariesDto> findAllCommentary(PageRequest page) {

        log.info("All users commentary was sent");

        return commentaryRepository.findAll(page).stream()
                .map(CommentaryMapper.COMMENTARY_MAPPER::CommentaryToCommentariesDto)
                .collect(Collectors.toList());
    }



}
