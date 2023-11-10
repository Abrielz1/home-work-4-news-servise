package ru.skillbox.homework4.news.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skillbox.homework4.commentary.model.Commentary;
import ru.skillbox.homework4.user.model.User;

@Data
@Builder
@Entity
@Table(name = "news")
@NoArgsConstructor
@AllArgsConstructor
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "news_message")
    private String newsMessage;

    @ManyToOne
    private User user;

    @ManyToOne
    private Commentary commentary;
}
