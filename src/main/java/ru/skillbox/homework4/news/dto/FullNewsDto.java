package ru.skillbox.homework4.news.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skillbox.homework4.commentary.dto.CommentariesDto;
import ru.skillbox.homework4.news.dto.category.CategoryDto;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullNewsDto {

    private Long id;

    private String newsMessage;

    private CategoryDto category;

    private List<CommentariesDto> commentaryList;
}

