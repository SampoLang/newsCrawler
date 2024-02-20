package com.example.newsRetrievalSystem.service;

import com.example.newsRetrievalSystem.model.dto.NewsDTO;
import com.example.newsRetrievalSystem.model.entity.NewsEntity;

import java.util.List;

public interface NewsService {

    List<NewsDTO> getAllNews();
    List<NewsDTO> getAllNewsNewestFirst();
    NewsDTO getTheLatestNews();
    void fetchYleNews();
    boolean deleteNewsById(Long id);

    NewsDTO getNewsById(Long id);
}
