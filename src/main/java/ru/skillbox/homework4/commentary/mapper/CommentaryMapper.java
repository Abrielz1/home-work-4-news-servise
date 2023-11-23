package ru.skillbox.homework4.commentary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import ru.skillbox.homework4.commentary.dto.CommentariesDto;
import ru.skillbox.homework4.commentary.model.Commentary;
import ru.skillbox.homework4.news.model.News;
import ru.skillbox.homework4.user.model.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentaryMapper {

    CommentaryMapper COMMENTARY_MAPPER = Mappers.getMapper(CommentaryMapper.class);

    @Mapping(target = "id", ignore = true)
    Commentary CommentariesDto(CommentariesDto commentariesDto, User user, News news);

   default CommentariesDto setAuthorIdAndNewsId(CommentariesDto commentariesDto, User user, News news) {

        commentariesDto.setAuthorId(user.getId());
        commentariesDto.setNewsId(news.getId());

        return commentariesDto;
    }

    CommentariesDto CommentaryToCommentariesDto(Commentary commentary);
}
