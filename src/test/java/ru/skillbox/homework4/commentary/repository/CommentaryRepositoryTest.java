package ru.skillbox.homework4.commentary.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.skillbox.homework4.commentary.model.Commentary;
import ru.skillbox.homework4.news.model.News;
import ru.skillbox.homework4.news.model.category.Category;
import ru.skillbox.homework4.news.repository.CategoryRepository;
import ru.skillbox.homework4.news.repository.NewsRepository;
import ru.skillbox.homework4.user.model.User;
import ru.skillbox.homework4.user.repository.UserRepository;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CommentaryRepositoryTest {

    @Autowired
    private CommentaryRepository commentaryRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    private UserRepository userRepository;

    private News news;

    private Category category;

    private Commentary commentary;

    @BeforeEach
    void beforeEach() {

        User user1 = userRepository.save(new User());
        user1.setId(1L);
        user1.setName("User1 name");
        user1.setEmail("user1@mail.com");

        User user2 = userRepository.save(new User());
        user2.setId(2L);
        user2.setName("User2 name");
        user2.setEmail("user2@mail.com");

        category = Category.builder()
                .id(1L)
                .name("Celebrity")
                .build();

        commentary = Commentary.builder()
                .id(1l)
                .commentaryText("test commentary message")
                .news(news)
                .user(user1)
                .build();

        news = News.builder()
                .id(1L)
                .newsMessage("test news message")
                .user(user1)
                .category(category)
                .build();

    }

    @AfterEach
    void afterEach() {
        commentaryRepository.deleteAll();
        categoryRepository.deleteAll();
        newsRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void getListOfCommentariesByNewsId() {
        PageRequest pg = PageRequest.of(0, 10);
        List<Commentary> commentaryList = commentaryRepository.getListOfCommentariesByNewsId(1L, pg);

        assertEquals(1, commentaryList.size());
    }
}