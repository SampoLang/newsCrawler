package com.example.newsRetrievalSystem.controller;

import com.example.newsRetrievalSystem.model.dto.NewsDTO;
import com.example.newsRetrievalSystem.model.entity.NewsEntity;
import com.example.newsRetrievalSystem.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/news")
public class NewsController {
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }
    @GetMapping
    public List<NewsDTO> getAllNews() {
        logger.info("Fetching all news articles");
        List<NewsDTO> news = newsService.getAllNewsNewestFirst();
        if(news != null){
            logger.info("Found {} news articles", news.size());
            return newsService.getAllNewsNewestFirst();
        }
        logger.warn("No news articles found");
        return null;

    }
    @GetMapping("/{id}")
    public NewsDTO getNewsById(@PathVariable Long id) {
        logger.info("Fetching news article with ID: {}", id);
        NewsDTO news = newsService.getNewsById(id);
        if(news != null){
            logger.info("Found news article with ID: {}", id);
            return news;
        }
        logger.warn("News article with ID {} not found", id);
        return null;
    }
    @GetMapping("/latest")
    public NewsDTO getLatestNews() {
        logger.info("Fetching latest news article");
        NewsDTO news = newsService.getTheLatestNews();
        if(news != null){
            logger.info("Found latest news with id: {} ", news.getId());
            return news;
        }
        logger.warn("Could not fetch the latest news from the database");
        return null;
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNewsById(@PathVariable Long id) {
        logger.info("Request to delete news article with ID: {}", id);

        if (newsService.deleteNewsById(id)) {
            logger.info("News article with ID {} deleted successfully", id);
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("News article with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }
}
