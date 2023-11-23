package ru.skillbox.homework4.commentary.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentariesDto {

    private Long id;

    @NotNull
    @NotBlank
    private String commentaryText;

    private Long newsId;

    private Long authorId;
}

