package ru.skillbox.homework4.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.skillbox.homework4.news.model.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Long>, JpaSpecificationExecutor<News> {

    Boolean existsByIdAndUserId(Long id, Long userId);
}
