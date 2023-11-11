package ru.skillbox.homework4.news.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.skillbox.homework4.exception.exceptions.ObjectNotFoundException;
import ru.skillbox.homework4.news.dto.NewsDto;
import ru.skillbox.homework4.news.mapper.NewsMapper;
import ru.skillbox.homework4.news.model.News;
import ru.skillbox.homework4.news.repository.NewsRepository;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    @Override
    public List<NewsDto> findAll(PageRequest page) {

        log.info("List of news was sent!");

        return newsRepository.findAll(page)
                .stream().map(NewsMapper.NEWS_MAPPER::toNewsDto)
                .collect(Collectors.toList());
    }

    @Override
    public NewsDto findNewsById(Long id) {

        News news = newsRepository.findById(id).orElseThrow( () -> {
            log.warn("news with id: {} not present!", id);

            return new ObjectNotFoundException("News not Found!");
        });

        //todo добавить коментарии
        log.info("news with id: {} was sent", id);

        return NewsMapper.NEWS_MAPPER.toNewsDto(news);
    }

    //Todo: create
    // update

    @Override
    public NewsDto deleteNewsById(Long id) {

        News news = newsRepository.findById(id).orElseThrow( ()-> {
            log.warn("news with id: {} not present!", id);
            return new ObjectNotFoundException("News not Found!");
        });

        newsRepository.deleteById(id);
        log.info("news with id: {} was deleted!", id);

        return NewsMapper.NEWS_MAPPER.toNewsDto(news);
    }
}
