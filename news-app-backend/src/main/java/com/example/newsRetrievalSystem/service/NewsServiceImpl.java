package com.example.newsRetrievalSystem.service;

import com.example.newsRetrievalSystem.controller.NewsController;
import com.example.newsRetrievalSystem.mapper.NewsMapper;
import com.example.newsRetrievalSystem.model.dto.NewsDTO;
import com.example.newsRetrievalSystem.model.entity.NewsEntity;
import com.example.newsRetrievalSystem.repository.NewsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService{
    private final NewsRepository newsRepository;
    private final YleNewsCrawlerService yleNewsCrawlerService;
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository, YleNewsCrawlerService yleNewsCrawlerService) {
        this.newsRepository = newsRepository;
        this.yleNewsCrawlerService = yleNewsCrawlerService;
    }
    public List<NewsDTO> getAllNews() {
        List<NewsEntity> newsEntities = newsRepository.findAllByOrderByIdDesc();
        return NewsMapper.toDTOs(newsEntities);
    }
    //list all news starting from the newest
    public List<NewsDTO> getAllNewsNewestFirst() {
        List<NewsEntity> newsEntities = newsRepository.findAllByOrderByPublishDateDesc();
        if(newsEntities != null){
            logger.info("Found {} news articles in the database", newsEntities.size());
            return NewsMapper.toDTOs(newsEntities);
        }
        logger.warn("No news articles found");
        return null;

    }
    //get only the latest news, used for checking if new news available
    public NewsDTO getTheLatestNews() {
        NewsEntity newsEntity = newsRepository.findTopByOrderByIdDesc();
        if(newsEntity != null){
            logger.info("Found news article in the database with ID: {}", newsEntity.getId());
            return NewsMapper.toDTO(newsEntity);
        }
        logger.warn("could not fetch the latest news");
        return null;
    }

    public NewsDTO getNewsById(Long id) {
        NewsEntity newsEntity = newsRepository.findById(id).orElse(null);
        if (newsEntity != null) {
            logger.info("Found news article in the database with ID: {}", id);
            return NewsMapper.toDTO(newsEntity);
        }
        logger.warn("News article with ID {} not found", id);
        return null;
    }

    /**
     * Scheduled method to fetch news articles from an external service (every 10 minutes)
     * and save them to the repository. Duplicates (based on title) are filtered out before saving.
     */
    public void fetchYleNews() {
        // Capture the start time
        long startTime = System.currentTimeMillis();
        yleNewsCrawlerService.fetchNews();
        // Log the time taken
        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;
        logger.info("fetchNews method executed in {} milliseconds", timeTaken);
    }

    public boolean deleteNewsById(Long id) {
        if (newsRepository.existsById(id)) {
            logger.info("Deleting news article with ID: {}", id);
            newsRepository.deleteById(id);
            return true;
        }
        logger.warn("News article with ID {} not found", id);
        return false;
    }

}
