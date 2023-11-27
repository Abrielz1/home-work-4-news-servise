package ru.skillbox.homework4.commentary.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @Positive
    private Long newsId;

    @Positive
    private Long authorId;
}

