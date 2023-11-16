package ru.skillbox.homework4.news.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.skillbox.homework4.news.dto.NewsDto;
import ru.skillbox.homework4.news.model.News;
import ru.skillbox.homework4.user.model.User;

@Mapper
public interface NewsMapper {

    //todo: добавить коментарий в сущность новости

    NewsMapper NEWS_MAPPER = Mappers.getMapper(NewsMapper.class);

    NewsDto toNewsDto(News news);

    News toNews(NewsDto newsDto, User user); // Commentary commentary
}
