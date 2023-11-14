package ru.skillbox.homework4.news.service;

import org.springframework.data.domain.PageRequest;
import ru.skillbox.homework4.news.dto.NewsDto;
import java.util.List;

public interface NewsService {

    List<NewsDto> findAll(PageRequest page);

    NewsDto findNewsById(Long id);

    NewsDto deleteNewsById(Long id);

    NewsDto createNews(Long id, NewsDto newsDto);

    NewsDto updateNewsById(Long userId, Long newsId, NewsDto newsDto);

    NewsDto changeNewsCategoryById(Long userId, Long newsId, String category);
}
