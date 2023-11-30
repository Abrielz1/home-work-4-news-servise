package ru.skillbox.homework4.news.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import org.junit.jupiter.api.AfterEach;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.skillbox.homework4.news.mapper.CategoryMapper.CATEGORY_MAPPER;
import static ru.skillbox.homework4.user.mapper.UserMapper.USER_MAPPER;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import java.util.ArrayList;
import java.util.Optional;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.skillbox.homework4.exception.exceptions.ObjectNotFoundException;
import ru.skillbox.homework4.news.dto.category.CategoryDto;
import ru.skillbox.homework4.news.dto.category.CategoryNewDto;
import ru.skillbox.homework4.news.mapper.CategoryMapper;
import ru.skillbox.homework4.news.model.category.Category;
import ru.skillbox.homework4.news.repository.CategoryRepository;
import ru.skillbox.homework4.user.dto.UserDto;
import ru.skillbox.homework4.user.model.User;
import ru.skillbox.homework4.user.repository.UserRepository;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    CategoryRepository repository;

    @InjectMocks
    CategoryServiceImpl service;

    private CategoryNewDto categoryNewDto;

    private Category category;

    private CategoryDto categoryDto;

    @BeforeEach
    void beforeEach() {

        categoryNewDto = CategoryNewDto.builder()
                .name("IT")
                .build();

//        category = Category.builder()
//                .id(1L)
//                .name("IT")
//                .build();
//
//        repository.save(category);
//
////        if (category.getId() == null) {
////            category.setId(1L);
////        }
//
//        categoryDto = CategoryDto.builder()
//                .id(1L)
//                .name("IT")
//                .build();
    }

    @AfterEach
    void afterEach() {
        repository.deleteAll();
    }


    @Test
    void findAll() {

       category = CATEGORY_MAPPER.toCategory(categoryNewDto);
       category.setId(1L);
       categoryDto = CATEGORY_MAPPER.toCategoryDto(category);

        List<Category> list = new ArrayList<>();
        list.add(category);

        when(repository.findAll())
                .thenReturn(list);

        List<CategoryDto> categoryDtoList = service.findAll();
        List<CategoryDto> test = List.of(categoryDto);

        assertThat(categoryDtoList.size()).isEqualTo(1);
        assertThat(categoryDtoList).isEqualTo(test);
        assertThat(categoryDtoList.get(0).getId()).isEqualTo(1L);
        assertThat(categoryDtoList.get(0).getName()).isEqualToIgnoringCase("IT");
        assertThat(categoryDtoList.get(0)).isEqualTo(categoryDto);
    }

    @Test
    void findCategoryById() {

        category = CATEGORY_MAPPER.toCategory(categoryNewDto);
        category.setId(1L);
        repository.save(category);

        when(repository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(category));

        categoryDto = service.findCategoryById(1L);

        assertThat(categoryDto.getId()).isEqualTo(1L);
        assertThat(categoryDto.getName()).isEqualToIgnoringCase("IT");
    }

    @Test
    void createCategory() {

        category = CATEGORY_MAPPER.toCategory(categoryNewDto);

        when(repository.save(any(Category.class)))
                .thenReturn(category);

        categoryDto = service.createCategory(categoryNewDto);
        categoryDto.setId(1L);

        assertThat(categoryDto.getId()).isEqualTo(1L);
        assertThat(categoryDto.getName()).isEqualToIgnoringCase("IT");
    }

    @Test
    void updateCategory() {

        categoryNewDto.setName("Auto");
        category = CATEGORY_MAPPER.toCategory(categoryNewDto);
        category.setId(1L);

        when(repository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(category));

        when(repository.save(any(Category.class)))
                .thenReturn(category);

        categoryDto = CATEGORY_MAPPER.toCategoryDto(category);
        CategoryDto testCategory = categoryDto;

        categoryDto = service.updateCategory(category.getId() , categoryDto);

        assertThat(categoryDto.getId()).isEqualTo(1L);
        assertThat(categoryDto.getName()).isEqualToIgnoringCase("Auto");
        assertThat(testCategory).isEqualTo(categoryDto);
    }

    @Test
    void deleteCategoryById() {

        category = CATEGORY_MAPPER.toCategory(categoryNewDto);
        category.setId(1L);
        repository.save(category);

        when(repository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(category));

        categoryDto = service.findCategoryById(1L);

        assertThat(category.getId()).isEqualTo(1L);
        assertThat(category.getName()).isEqualToIgnoringCase("IT");
    }
}