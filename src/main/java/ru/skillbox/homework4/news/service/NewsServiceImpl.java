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
    public NewsDto findNewsById(Long newsId) {

        News news = checkNewsById(newsId);;

        //todo добавить коментарии
        log.info("News with id: {} was sent", newsId);

        return NewsMapper.NEWS_MAPPER.toNewsDto(news);
    }

    @Override
    public NewsDto createNews(Long userId, NewsDto newsDto) {
        //todo добавить коментарии

        User user = checkUserById(userId);

        log.info("News was created");
        News news = newsRepository.save(NewsMapper.NEWS_MAPPER.toNews(newsDto, user));

        return NewsMapper.NEWS_MAPPER.toNewsDto(news);
    }

    @Override
    public NewsDto updateNewsById(Long userId, Long newsId, NewsDto newsDto) {

        User user = checkUserById(userId);

        News news = checkNewsById(newsId);

        if (newsDto.getNewsMessage() != null) {
            news.setNewsMessage(newsDto.getNewsMessage());
        }

        if (newsDto.getNewsCategory() != null) {
            news.setNewsCategory(newsDto.getNewsCategory());
        }

        newsRepository.save(news);
        log.info("News was updated");
        //todo добавить коментарии

        return NewsMapper.NEWS_MAPPER.toNewsDto(news);
    }

    @Override
    public NewsDto deleteNewsById(Long newsId) {

        News news = checkNewsById(newsId);

        newsRepository.deleteById(newsId);
        log.info("News with id: {} was deleted!", newsId);

        return NewsMapper.NEWS_MAPPER.toNewsDto(news);
    }

    // todo: метод управления категориями и проверки права владения.

    @Override
    public NewsDto changeNewsCategoryById(Long userId, Long newsId, String category) {

        User user = checkUserById(userId);

        News news = checkNewsById(newsId);

        switch (category) {
            case "POLITIC", "SPORT", "AUTO", "CELEBRITY" -> {
                news.setNewsCategory(NewsCategory.valueOf(category));
            }
        }

        newsRepository.save(news);
        log.info("News was updated");
        //todo добавить коментарии

        return NewsMapper.NEWS_MAPPER.toNewsDto(news);
    }


    private User checkUserById(Long userId) {
     User user = userRepository.findById(userId).orElseThrow(() -> {
            log.warn("User with id {} is not found", userId);
            return new ObjectNotFoundException("Пользователь не найден");
        });
        return user;
    }

    private News checkNewsById(Long newsId) {
        News news = newsRepository.findById(newsId).orElseThrow(() -> {
            log.warn("News with id {} is not found", newsId);
            return new ObjectNotFoundException("Пользователь не найден");
        });
        return news;
    }
}
