package ru.skillbox.homework4.commentary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skillbox.homework4.commentary.dto.CommentariesDto;
import ru.skillbox.homework4.commentary.model.Commentary;
import ru.skillbox.homework4.news.model.News;
import ru.skillbox.homework4.user.model.User;

@Mapper
public interface CommentaryMapper {

    CommentaryMapper COMMENTARY_MAPPER = Mappers.getMapper(CommentaryMapper.class);

    @Mapping(target = "id", ignore = true)
  //  @Mapping(target = )

    Commentary CommentariesDto(CommentariesDto commentariesDto, User user, News news);

    CommentariesDto CommentaryToCommentariesDto(Commentary commentary);
}
