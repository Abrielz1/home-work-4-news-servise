package ru.skillbox.homework4.commentary.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skillbox.homework4.news.model.News;
import ru.skillbox.homework4.user.model.User;

@Data
@Builder
@Entity
@Table(name = "commentaries")
@NoArgsConstructor
@AllArgsConstructor
public class Commentary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "commentary_text")
    private String commentaryText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id")
    News news;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
}
