package ru.skillbox.homework4.news.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.when;
import org.mockito.quality.Strictness;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import org.mockito.InjectMocks;
import java.util.Collections;
import java.util.Optional;
import org.mockito.Mock;
import ru.skillbox.homework4.news.dto.FullNewsDto;
import ru.skillbox.homework4.news.dto.NewsDto;
import ru.skillbox.homework4.news.mapper.NewsMapper;
import ru.skillbox.homework4.news.model.News;
import ru.skillbox.homework4.news.model.category.Category;
import ru.skillbox.homework4.news.repository.CategoryRepository;
import ru.skillbox.homework4.news.repository.NewsRepository;
import ru.skillbox.homework4.user.model.User;
import ru.skillbox.homework4.user.repository.UserRepository;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class NewsServiceImplTest {

    @Mock
    private NewsService newsService;

    @Mock
    private NewsRepository newsRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CategoryRepository categoryRepository;

    private User user1;

    private User user2;

    private Category category1;

    private Category category2;

    private News news1;

    private News news2;

    private News news3;

    @BeforeEach
    void beforeEach() {

        user1 = User.builder()
                .id(1L)
                .name("User1 name")
                .email("user1@mail.com")
                .build();

        userRepository.save(user1);

        user2 = User.builder()
                .id(2L)
                .name("User2 name")
                .email("user2@mail.com")
                .build();

        userRepository.save(user2);

        category1 = Category.builder()
                .id(1L)
                .name("Celebrity")
                .build();

        categoryRepository.save(category1);

        category2 = Category.builder()
                .id(2L)
                .name("Sport")
                .build();

        categoryRepository.save(category2);

        news1 = News.builder()
                .id(1L)
                .newsName("Test name news 1")
                .newsMessage("Test message news 1")
                .category(category1)
                .user(user1)
                .build();

        newsRepository.save(news1);

        news2 = News.builder()
                .id(2L)
                .newsName("Test name news 2")
                .newsMessage("Test message news 2")
                .category(category2)
                .user(user1)
                .build();

        newsRepository.save(news2);

        news3 = News.builder()
                .id(3L)
                .newsName("Test name news 3")
                .newsMessage("Test message news 3")
                .category(category1)
                .user(user2)
                .build();

        newsRepository.save(news3);
    }


    @Test
    void filteredByCriteria() {

        //я хз, как тестить критерию
    }

    @Test
    void findAll() {

        PageRequest p = PageRequest.of(0, 20);

        when(newsRepository.findAll()).thenReturn(List.of(news1, news2, news3));

        List<NewsDto> newsDtoList = newsService.findAll(p);

        assertEquals(0, newsDtoList.size());
    }

    @Test
    void findNewsById() {

        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(user1));

        when(newsRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(news1));


        FullNewsDto newsDto = newsService.findNewsById(news1.getId());
        System.out.println(news1);

        assertEquals(1l, newsDto.getId());
    }

    @Test
    void createNews() {
    }

    @Test
    void updateNewsById() {
    }

    @Test
    void deleteNewsById() {
    }
}