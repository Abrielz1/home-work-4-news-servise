package ru.skillbox.homework4.user.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skillbox.homework4.user.model.User;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = """
        SELECT  * FROM postgres.public.users
        """, nativeQuery = true)
    List<User> findAllUsers(Pageable pageable);
}
