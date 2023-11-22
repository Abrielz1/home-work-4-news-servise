package ru.skillbox.homework4.commentary.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skillbox.homework4.commentary.model.Commentary;
import java.util.List;

@Repository
public interface CommentaryRepository extends JpaRepository<Commentary, Long> {

@Query("""
       SELECT commentary FROM Commentary commentary WHERE commentary.news.id = :newsId
       """)
    List<Commentary> getListOfCommentariesByNewsId(@Param("newsId")Long newsId, Pageable pageable);
}
