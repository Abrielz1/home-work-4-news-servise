package ru.skillbox.homework4.news.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.skillbox.homework4.news.dto.NewsDto;
import ru.skillbox.homework4.news.mapper.NewsMapper;
import ru.skillbox.homework4.news.model.News;
import ru.skillbox.homework4.news.repository.NewsRepository;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    @Override
    public List<NewsDto> findAll(PageRequest page) {
        return newsRepository.findAll(page)
                .stream().map(NewsMapper.NEWS_MAPPER::toNewsDto)
                .collect(Collectors.toList());
    }
}
