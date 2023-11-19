package ru.skillbox.homework4.news.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skillbox.homework4.news.dto.category.CategoryDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsDto {

    private Long id;

    private String newsMessage;

    private CategoryDto category;

    private Integer numberOfCommentaries;
}
