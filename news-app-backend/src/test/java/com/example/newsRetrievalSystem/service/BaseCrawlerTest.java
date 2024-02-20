package com.example.newsRetrievalSystem.service;

import com.example.newsRetrievalSystem.model.entity.NewsEntity;
import com.example.newsRetrievalSystem.repository.NewsRepository;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BaseCrawlerTest {
    // Example of creating a concrete subclass for testing, if needed
    private BaseCrawler baseCrawler = new BaseCrawler() {
        @Override
        protected void crawl() {

        }
    };
    @Mock
    private NewsRepository newsRepository;
    @Mock
    private ExecutorService executorService;
    @Mock
    private Future<?> crawlerTask;


    @Test
    void stopCrawler() {
    }

    @Test
    void isValidNewsEntity_WithValidEntity_ReturnsTrue() {
        //given
        NewsEntity validEntity = new NewsEntity("title", OffsetDateTime.now(), "http://example.com", "snippet");
        //when
        boolean result = baseCrawler.isValidNewsEntity(validEntity);
        //then
        assertTrue(result);
    }

    @Test
    void isValidNewsEntity_WithInvalidEntity_ReturnsFalse() {
        // Given
        NewsEntity invalidEntity = new NewsEntity("", OffsetDateTime.now(), "", "");

        // When
        boolean result = baseCrawler.isValidNewsEntity(invalidEntity);

        // Then
        assertFalse(result);
    }


    @Test
    void filterDuplicatesByTitle_RemovesDuplicatesCorrectly() {
        // Given
        List<NewsEntity> newsEntities = new ArrayList<>();
        newsEntities.add(new NewsEntity("Duplicate Title", OffsetDateTime.now(), "http://example.com", "snippet"));
        newsEntities.add(new NewsEntity("Unique Title", OffsetDateTime.now(), "http://example.com/unique", "unique snippet"));
        newsEntities.add(new NewsEntity("Duplicate Title", OffsetDateTime.now(), "http://example.com/duplicate", "duplicate snippet"));

        Set<String> existingTitles = Set.of("Duplicate Title");
        when(newsRepository.findAllByTitleIn(anySet())).thenReturn(existingTitles);

        // When
        List<NewsEntity> filteredNewsEntities = baseCrawler.filterDuplicatesByTitle(newsEntities, newsRepository);

        // Then
        assertEquals(1, filteredNewsEntities.size());
        assertEquals("Unique Title", filteredNewsEntities.get(0).getTitle());
    }
}