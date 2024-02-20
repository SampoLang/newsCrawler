package com.example.newsRetrievalSystem.service;

import com.example.newsRetrievalSystem.model.entity.NewsEntity;

import java.util.List;

public interface YleNewsCrawlerService {
    void fetchNews();
    void startCrawler();

    void stopCrawler();
}
