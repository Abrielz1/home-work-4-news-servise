package ru.skillbox.homework4.news.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import ru.skillbox.homework4.commentary.model.Commentary;
import ru.skillbox.homework4.news.dto.NewsDto;
import ru.skillbox.homework4.news.dto.FullNewsDto;
import ru.skillbox.homework4.news.model.News;
import ru.skillbox.homework4.news.model.category.Category;
import ru.skillbox.homework4.user.model.User;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NewsMapper {

    //todo: добавить коментарий в сущность новости , uses = {CategoryMapper.class, UserMapper.class}

    NewsMapper NEWS_MAPPER = Mappers.getMapper(NewsMapper.class);

    NewsDto toNewsDto(News news);

    FullNewsDto toFullNewsDto(News news, List<Commentary> commentariesList);

//    default FullNewsDto setCommentariesList(News news, List<Commentary> commentariesList) {
//
//        FullNewsDto fullNewsDto = toFullNewsDto(news);
//
//       fullNewsDto.setCommentaryList(commentariesList.stream().toList());
//
//        return fullNewsDto;
//    }

    @Mapping(source = "category.id", target = "id")
    News toNews(NewsDto newsDto, User user, Category category);

    default News setCategory(NewsDto newsDto, User user, Category category) { //todo: Босс проверь этот костыль

        News news = toNews( newsDto, user, category);
        news.setCategory(category);

        return news;
    }
}
