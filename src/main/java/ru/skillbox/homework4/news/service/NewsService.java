package ru.skillbox.homework4.news.service;

import org.springframework.data.domain.PageRequest;
import ru.skillbox.homework4.news.dto.NewsDto;
import java.util.List;

public interface NewsService {

    List<NewsDto> findAll(PageRequest page);
}
