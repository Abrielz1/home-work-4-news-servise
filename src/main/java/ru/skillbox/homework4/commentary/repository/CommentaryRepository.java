package ru.skillbox.homework4.commentary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.homework4.commentary.model.Commentary;

@Repository
public interface CommentaryRepository extends JpaRepository<Commentary, Long> {

}
