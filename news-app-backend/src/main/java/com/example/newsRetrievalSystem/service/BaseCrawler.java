package com.example.newsRetrievalSystem.service;

import com.example.newsRetrievalSystem.model.entity.NewsEntity;
import com.example.newsRetrievalSystem.repository.NewsRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public abstract class BaseCrawler {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private Future<?> crawlerTask;

    private static final int CRAWLFREQUENCY = 60000; // in milliseconds

    protected BaseCrawler() {
    }

    /**
     * Fetches the Document object for the provided URL.
     *
     * @param url The URL of the page to fetch.
     * @return A Document object representing the fetched page.
     * @throws IOException If there's an error during fetching.
     */
    protected Document fetchPage(String url) throws IOException {
        log.debug("Fetching page from {}", url);
        return Jsoup.connect(url).get();
    }

    protected abstract void crawl();

    /**
     * Starts the web crawler task if it's not already running.
     * This method checks if a crawler task is currently running or if there's no existing task at all.
     * If these conditions are met, it will submit a new task to the executor service which involves
     * repeatedly invoking the crawl() method. After each crawl, the thread sleeps for a predefined
     * duration (currently set to 1 minute) before the next iteration.
     */
    public void startCrawler() {
        if (crawlerTask == null || crawlerTask.isDone()) {
            crawlerTask = executorService.submit(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    crawl();
                    try {
                        // Sleep for some time before the next iteration to avoid aggressive crawling.
                        // Adjust the sleep time as needed.
                        Thread.sleep(CRAWLFREQUENCY);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }
    }

    public void stopCrawler() {
        if (crawlerTask != null && !crawlerTask.isDone()) {
            ;
            crawlerTask.cancel(true); // Interrupts the thread
            log.info("Crawler stopped successfully");
        }
    }

    protected boolean isValidNewsEntity(NewsEntity newsEntity) {
        return newsEntity != null
                && !newsEntity.getTitle().isEmpty()
                && !newsEntity.getContentSnippet().isEmpty()
                && !newsEntity.getLink().isEmpty();
    }

    protected void filterDuplicatesByTitle(List<NewsEntity> newsEntities, NewsRepository newsRepository) {
        Set<String> fetchedTitles = newsEntities.stream()
                .map(NewsEntity::getTitle)
                .collect(Collectors.toSet());

        // Fetch all existing titles in the database that match with the fetched titles
        Set<String> existingTitles = newsRepository.findAllByTitleIn(fetchedTitles);

        // Filter out duplicates
        List<NewsEntity> newsList = newsEntities.stream()
                .filter(news -> !existingTitles.contains(news.getTitle()))
                .collect(Collectors.toList());
        Collections.reverse(newsList);

    }



    /**
     * Handles exceptions and logs them.
     * @param message A custom error message.
     * @param e The exception to be logged.
     */
    protected void handleError(String message, Exception e) {
        log.error("{}: {}", message, e.getMessage(), e);
    }

    /**
     * To be implemented by subclasses to define how to fetch news articles.
     * @return A list of news articles specific to the implementation.
     */

}