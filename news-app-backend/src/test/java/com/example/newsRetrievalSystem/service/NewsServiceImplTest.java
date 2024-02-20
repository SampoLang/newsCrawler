package com.example.newsRetrievalSystem.service;


import com.example.newsRetrievalSystem.model.dto.NewsDTO;
import com.example.newsRetrievalSystem.model.entity.NewsEntity;
import com.example.newsRetrievalSystem.repository.NewsRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewsServiceImplTest {

    @Mock
    private NewsRepository newsRepository;
    @Mock
    private YleNewsCrawlerService yleNewsCrawlerService;
    @Mock Logger logger;
    @InjectMocks
    private NewsServiceImpl newsService;


    @Test
    void canGetAllNews() {
        //when
        newsService.getAllNews();
        //then
        verify(newsRepository).findAllByOrderByIdDesc();

    }

    @Test
    void whenRepositoryHasNews_thenReturnsNewsDTOsInDescendingOrderOfPublishDate() {
        // Given
        String title1 = "95-mies Torilla";
        String contentSnippet1 = "95-mies nähty torilla";
        OffsetDateTime publishDate = OffsetDateTime.of(2024, 2, 17, 10, 0, 0, 0, ZoneOffset.UTC);
        String link = "https://example.com/test-news";
        String title2 = "Kissa puussa";
        String contentSnippet2 = "Kissa puussa jumissa, palokunta paikalla";
        String title3 = "Koira puree miestä";
        String contentSnippet3 = "Koira puri miestä, poliisi paikalla";

        NewsEntity newsEntity1 = new NewsEntity(title1, publishDate, link, contentSnippet1);
        NewsEntity newsEntity2 = new NewsEntity(title2, publishDate, link, contentSnippet2);
        NewsEntity newsEntity3 = new NewsEntity(title3, publishDate, link, contentSnippet3);

        List<NewsEntity> mockEntities = List.of(newsEntity1, newsEntity2, newsEntity3);
        when(newsRepository.findAllByOrderByPublishDateDesc()).thenReturn(mockEntities);

        // When
        List<NewsDTO> result = newsService.getAllNewsNewestFirst();

        // Then
        // Check that the result is not empty
        assertFalse(result.isEmpty());
        // Check that the number of items is correct
        assertEquals(mockEntities.size(), result.size());
        // Check that the order is correct
        assertEquals(mockEntities.get(0).getTitle(), result.get(0).getTitle());
        assertEquals(mockEntities.get(1).getTitle(), result.get(1).getTitle());
        assertEquals(mockEntities.get(2).getTitle(), result.get(2).getTitle());
        verify(newsRepository).findAllByOrderByPublishDateDesc();
    }


    @Test
    void getTheLatestNews_ReturnNullWhenNoNewNews() {
        //when
        newsService.getTheLatestNews();
        //then
        verify(newsRepository).findTopByOrderByIdDesc();
    }
    @Test
    void getTheLatestNews_ReturnOnlyTheLatestNews(){

        // Given
        String title1 = "95-mies Torilla";
        String contentSnippet1 = "95-mies nähty torilla";
        OffsetDateTime publishDate = OffsetDateTime.of(2024, 2, 17, 10, 0, 0, 0, ZoneOffset.UTC);
        String link = "https://example.com/test-news";
        NewsEntity newsEntity = new NewsEntity(title1, publishDate, link, contentSnippet1);
        when(newsRepository.findTopByOrderByIdDesc()).thenReturn(newsEntity);
        //when
        NewsDTO result = newsService.getTheLatestNews();
        //then
        assertEquals(newsEntity.getTitle(), result.getTitle());
        verify(newsRepository).findTopByOrderByIdDesc();

    }

    @Test
    void getNewsById() {
        // Given
        String title1 = "95-mies Torilla";
        String contentSnippet1 = "95-mies nähty torilla";
        OffsetDateTime publishDate = OffsetDateTime.of(2024, 2, 17, 10, 0, 0, 0, ZoneOffset.UTC);
        String link = "https://example.com/test-news";
        NewsEntity newsEntity = new NewsEntity(title1, publishDate, link, contentSnippet1);
        when(newsRepository.findById(1L)).thenReturn(java.util.Optional.of(newsEntity));
        //when
        NewsDTO result = newsService.getNewsById(1L);
        //then
        assertEquals(newsEntity.getTitle(), result.getTitle());
        verify(newsRepository).findById(1L);
    }

    @Test
    void fetchYleNews() {
        //when
        newsService.fetchYleNews();
        //then
        verify(yleNewsCrawlerService).fetchNews();
    }

    @Test
    void deleteNewsById_WhenNotExists() {

            // Given
            Long id = 1L;
            when(newsRepository.existsById(id)).thenReturn(false);

            // When
            boolean result = newsService.deleteNewsById(id);

            // Then
            assertFalse(result);
            verify(newsRepository, never()).deleteById(id);

    }
    @Test
    void deleteNewsById_WhenExists() {
        // Given
        Long id = 1L;
        when(newsRepository.existsById(id)).thenReturn(true);

        // When
        boolean result = newsService.deleteNewsById(id);

        // Then
        assertTrue(result);
        verify(newsRepository).deleteById(id);
    }


}