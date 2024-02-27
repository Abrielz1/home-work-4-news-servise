package ru.skillbox.homework4.news.service;

import org.springframework.data.domain.PageRequest;
import ru.skillbox.homework4.news.dto.FullNewsDto;
import ru.skillbox.homework4.news.dto.NewsDto;
import ru.skillbox.homework4.news.model.category.CategoryFilter;

import java.security.Principal;
import java.util.List;

public interface NewsService {

    List<NewsDto> filteredByCriteria(CategoryFilter filter, PageRequest page);

    List<NewsDto> findAll(PageRequest page);

    FullNewsDto findNewsById(Long id);

    NewsDto deleteNewsById(Long id, Principal principal);

    NewsDto createNews(Principal principal, Long categoryId, NewsDto newsDto);

    NewsDto updateNewsById(Principal principal, Long categoryId, Long newsId, NewsDto newsDto);
}
