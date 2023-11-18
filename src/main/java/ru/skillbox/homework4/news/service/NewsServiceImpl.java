package ru.skillbox.homework4.news.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.skillbox.homework4.commentary.dto.CommentariesDto;
import ru.skillbox.homework4.exception.exceptions.ObjectNotFoundException;
import ru.skillbox.homework4.news.dto.NewsDto;
import ru.skillbox.homework4.news.mapper.NewsMapper;
import ru.skillbox.homework4.news.model.News;
import ru.skillbox.homework4.news.model.category.Category;
import ru.skillbox.homework4.news.repository.CategoryRepository;
import ru.skillbox.homework4.news.repository.NewsRepository;
import ru.skillbox.homework4.user.model.User;
import ru.skillbox.homework4.user.repository.UserRepository;
import ru.skillbox.homework4.util.Utils;
import java.util.List;
import java.util.stream.Collectors;

import static ru.skillbox.homework4.news.mapper.CategoryMapper.CATEGORY_MAPPER;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

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
    public NewsDto updateNewsById(Long userId, Long newsId, Long categoryId, NewsDto newsDto) {

        User user = checkUserById(userId);

        News newsBd = checkNewsById(newsId);

        Category category = checkCategoryById(categoryId);

        if (newsDto.getNewsMessage() != null) {
            newsBd.setNewsMessage(newsDto.getNewsMessage());
        }

        newsDto.setCategory(CATEGORY_MAPPER.toCategoryDto(category));

//        Utils.copyNonNullProperties(newsDto, newsBd); //todo: проверить

  //      newsBd.setUser(user); //

        newsRepository.save(newsBd);
        log.info("News was updated");
        //todo добавить коментарии

        return NewsMapper.NEWS_MAPPER.toNewsDto(newsBd);
    }

    @Override
    public NewsDto deleteNewsById(Long newsId) {

        News news = checkNewsById(newsId);

        newsRepository.deleteById(newsId);
        log.info("News with id: {} was deleted!", newsId);

        return NewsMapper.NEWS_MAPPER.toNewsDto(news);
    }

    // todo: метод управления категориями и проверки права владения.

    private User checkUserById(Long userId) {
     User user = userRepository.findById(userId).orElseThrow(() -> {
            log.warn("User with id {} is not found", userId);
            throw  new ObjectNotFoundException("User was not found");
        });

        return user;
    }

    private News checkNewsById(Long newsId) {
        News news = newsRepository.findById(newsId).orElseThrow(() -> {
            log.warn("News with id {} is not found", newsId);
            throw  new ObjectNotFoundException("News was not found");
        });

        return news;
    }

    private Category checkCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> {
            log.warn("Category with id {} is not found", categoryId);
            throw  new ObjectNotFoundException("Category was not found");
        });

        return category;
    }
}
