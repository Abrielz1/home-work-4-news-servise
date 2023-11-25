package ru.skillbox.homework4.commentary.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.homework4.commentary.model.Commentary;
import java.util.List;

@Repository
public interface CommentaryRepository extends JpaRepository<Commentary, Long> {

    List<Commentary> findAllByNewsId(Long newsId, Pageable pageable);

    Boolean exists(Long userId, Long commentaryId);
}
