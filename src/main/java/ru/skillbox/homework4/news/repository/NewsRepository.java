package ru.skillbox.homework4.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.homework4.news.model.News;

public interface NewsRepository extends JpaRepository<News, Long> {
}
