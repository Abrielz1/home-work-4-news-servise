package ru.skillbox.homework4.news.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.homework4.commentary.model.Commentary;
import ru.skillbox.homework4.commentary.repository.CommentaryRepository;
import ru.skillbox.homework4.exception.exceptions.ObjectNotFoundException;
import ru.skillbox.homework4.news.dto.NewsDto;
import ru.skillbox.homework4.news.dto.FullNewsDto;
import ru.skillbox.homework4.news.mapper.NewsMapper;
import ru.skillbox.homework4.news.model.News;
import ru.skillbox.homework4.news.model.category.Category;
import ru.skillbox.homework4.news.repository.CategoryRepository;
import ru.skillbox.homework4.news.repository.NewsRepository;
import ru.skillbox.homework4.user.model.User;
import ru.skillbox.homework4.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import static ru.skillbox.homework4.news.mapper.CategoryMapper.CATEGORY_MAPPER;
import static ru.skillbox.homework4.news.mapper.NewsMapper.NEWS_MAPPER;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    private final UserRepository userRepository;

    private final CommentaryRepository commentaryRepository;

    private final CategoryRepository categoryRepository;

    @Override
    public List<NewsDto> findAll(PageRequest page) {

       List<News> newsList = newsRepository.findAll();
       List<NewsDto> newsDtoList = new ArrayList<>();

        for (News news : newsList) {
           NewsDto newsDto = NEWS_MAPPER.toNewsDto(news);
           newsDto.setNumberOfCommentaries(news.getCommentaryList().size());
           newsDtoList.add(newsDto);
        }

        log.info("List of news were sent!");
        return newsDtoList;
    }

    @Override
    public FullNewsDto findNewsById(Long newsId) {

        News news = checkNewsById(newsId);

        System.out.println("News" + news);

        List<Commentary> commentariesList = commentaryRepository.findAll();

        log.info("News with id: {} was sent", newsId);

        return NEWS_MAPPER.toFullNewsDto(news, commentariesList);
    }

    @Override
    @Transactional
    public NewsDto createNews(Long userId, Long categoryId, NewsDto newsDto) {

        User user = checkUserById(userId);
        Category category = checkCategoryById(categoryId);

        News news = new News();
        news.setUser(user);
        news = NEWS_MAPPER.setCategory(newsDto, user, category);

        newsRepository.save(news);
        log.info("News was created");
        return NEWS_MAPPER.toNewsDto(news);
    }

    @Override
    @Transactional
    public NewsDto updateNewsById(Long userId,
                                  Long categoryId,
                                  Long newsId,
                                  NewsDto newsDto) {

        User user = checkUserById(userId);

        News newsBd = checkNewsById(newsId);

        Category category = checkCategoryById(categoryId);

        if (newsDto.getNewsMessage() != null) {
            newsBd.setNewsMessage(newsDto.getNewsMessage());
        }

        newsDto.setCategory(CATEGORY_MAPPER.toCategoryDto(category));

        newsRepository.save(newsBd);
        log.info("News was updated");

        return NEWS_MAPPER.toNewsDto(newsBd);
    }

    @Override
    @Transactional
    public NewsDto deleteNewsById(Long newsId) {

        News news = checkNewsById(newsId);

        newsRepository.deleteById(newsId);
        log.info("News with id: {} was deleted!", newsId);

        return NEWS_MAPPER.toNewsDto(news);
    }

    // todo: метод управления категориями и проверки права владения.

    private User checkUserById(Long userId) {

        return userRepository.findById(userId).orElseThrow(() -> {
               log.warn("User with id {} was not found", userId);
               throw  new ObjectNotFoundException("User was not found");
           });
    }

    private News checkNewsById(Long newsId) {

        return newsRepository.findById(newsId).orElseThrow(() -> {
            log.warn("News with id {} was not found", newsId);
            throw  new ObjectNotFoundException("News was not found");
        });
    }

    private Category checkCategoryById(Long categoryId) {

        return categoryRepository.findById(categoryId).orElseThrow(() -> {
            log.warn("Category with id {} was not found", categoryId);
            throw  new ObjectNotFoundException("Category was not found");
        });
    }

    private Commentary checkCommentaryById(Long commentaryId) {

        return commentaryRepository.findById(commentaryId)
                .orElseThrow(() -> {
                    log.warn("Commentary with id {} was not found", commentaryId);
                    throw new ObjectNotFoundException("Commentary was not found");
                });
    }
}
