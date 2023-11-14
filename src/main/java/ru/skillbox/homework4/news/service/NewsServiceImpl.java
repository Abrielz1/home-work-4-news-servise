package ru.skillbox.homework4.news.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.skillbox.homework4.exception.exceptions.ObjectNotFoundException;
import ru.skillbox.homework4.news.dto.NewsDto;
import ru.skillbox.homework4.news.mapper.NewsMapper;
import ru.skillbox.homework4.news.model.News;
import ru.skillbox.homework4.news.model.category.NewsCategory;
import ru.skillbox.homework4.news.repository.NewsRepository;
import ru.skillbox.homework4.user.model.User;
import ru.skillbox.homework4.user.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    private final UserRepository userRepository;

    @Override
    public List<NewsDto> findAll(PageRequest page) {

        log.info("List of news was sent!");

        return newsRepository.findAll(page)
                .stream().map(NewsMapper.NEWS_MAPPER::toNewsDto)
                .collect(Collectors.toList());
    }

    @Override
    public NewsDto findNewsById(Long id) {

        News news = newsRepository.findById(id).orElseThrow(() -> {
        log.warn("news with id: {} not present!", id);

            throw new ObjectNotFoundException("News not Found!");
        });

        //todo добавить коментарии
        log.info("news with id: {} was sent", id);

        return NewsMapper.NEWS_MAPPER.toNewsDto(news);
    }

    @Override
    public NewsDto createNews(Long id, NewsDto newsDto) {
        //todo добавить коментарии

        User user = userRepository.findById(id).orElseThrow(() -> {
            log.warn("Пользователь с id {} не найден", id);
            return new ObjectNotFoundException("Пользователь не найден");
        });

        log.info("News was created");
        News news = newsRepository.save(NewsMapper.NEWS_MAPPER.toNews(newsDto, user));

        return NewsMapper.NEWS_MAPPER.toNewsDto(news);
    }

    @Override
    public NewsDto updateNewsById(Long userId, Long newsId, NewsDto newsDto) {

        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.warn("Пользователь с id {} не найден", userId);
            return new ObjectNotFoundException("Пользователь не найден");
        });

        News news = newsRepository.findById(newsId).orElseThrow(() -> {
            log.warn("news with id: {} not present!", newsId);

            throw new ObjectNotFoundException("News not Found!");
        });

        if (newsDto.getNewsMessage() != null) {
            news.setNewsMessage(newsDto.getNewsMessage());
        }

        if (newsDto.getNewsCategory() != null) {
            news.setNewsCategory(newsDto.getNewsCategory());
        }

        newsRepository.save(news);
        log.info("news was updated");
        //todo добавить коментарии

        return NewsMapper.NEWS_MAPPER.toNewsDto(news);
    }

    @Override
    public NewsDto deleteNewsById(Long id) {

        News news = newsRepository.findById(id).orElseThrow(() -> {
            log.warn("news with id: {} not present!", id);
            throw new ObjectNotFoundException("News not Found!");
        });

        newsRepository.deleteById(id);
        log.info("news with id: {} was deleted!", id);

        return NewsMapper.NEWS_MAPPER.toNewsDto(news);
    }

    // todo: метод управления категориями и проверки права владения.

    @Override
    public NewsDto changeNewsCategoryById(Long userId, Long newsId, String category) {

        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.warn("Пользователь с id {} не найден", userId);
            return new ObjectNotFoundException("Пользователь не найден");
        });

        News news = newsRepository.findById(newsId).orElseThrow(() -> {
            log.warn("news with id: {} not present!", newsId);

            throw new ObjectNotFoundException("News not Found!");
        });

        switch (category) {
            case "POLITIC", "SPORT", "AUTO", "CELEBRITY" -> {
                news.setNewsCategory(NewsCategory.valueOf(category));
            }
        }

        newsRepository.save(news);
        log.info("news was updated");
        //todo добавить коментарии

        return NewsMapper.NEWS_MAPPER.toNewsDto(news);
    }



    // todo: приватный метод для проверки наличия и выброса исключения в случае отсутствия
}
