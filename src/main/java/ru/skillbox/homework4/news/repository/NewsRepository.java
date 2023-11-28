package ru.skillbox.homework4.news.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skillbox.homework4.news.model.News;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long>, JpaSpecificationExecutor<News> {

    @Query(value = """
           SELECT  * FROM postgres.public.news
           """, nativeQuery = true)
    List<News> getAllNews(Pageable pageRequest);

    Boolean existsByIdAndUserId(Long id, Long userId);
}
