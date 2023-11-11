package ru.skillbox.homework4.commentary.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skillbox.homework4.news.model.News;
import ru.skillbox.homework4.user.model.User;
import java.util.List;

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

    private String comment;

    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL)
    List<News> listNews;

    @ManyToOne
    private User user;
}
