package ru.skillbox.homework4.news.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private Long id;

    private List<CategoryDto> listOfCategories;
}
