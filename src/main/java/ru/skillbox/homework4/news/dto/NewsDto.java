package ru.skillbox.homework4.news.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @NotBlank
    private String newsMessage;

    private String newsName;

    private CategoryDto category;

    private Integer numberOfCommentaries;
}
