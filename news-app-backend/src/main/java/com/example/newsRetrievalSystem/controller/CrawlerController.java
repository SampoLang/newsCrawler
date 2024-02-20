package com.example.newsRetrievalSystem.controller;

import com.example.newsRetrievalSystem.service.YleNewsCrawlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/crawler")
public class CrawlerController {
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    private YleNewsCrawlerService yleNewsCrawler;

    @Autowired
    public CrawlerController(YleNewsCrawlerService yleNewsCrawler) {
        this.yleNewsCrawler = yleNewsCrawler;
    }

    @PostMapping("/{crawlerName}/start")
    public ResponseEntity<String> startCrawler(@PathVariable String crawlerName) {
        switch (crawlerName) {
            case "yle":
                yleNewsCrawler.startCrawler();
                break;
            default:
                logger.warn("Unknown crawler name");
                return ResponseEntity.badRequest().body("Unknown crawler name");
        }
        logger.info(crawlerName + " Crawler started");
        return ResponseEntity.ok(crawlerName + " Crawler started");
    }
     @PostMapping("/{crawlerName}/stop")
    public ResponseEntity<String> stopCrawler(@PathVariable String crawlerName){
        switch (crawlerName) {
            case "yle":
                yleNewsCrawler.stopCrawler();
                break;
            default:
                return ResponseEntity.badRequest().body("Unknown crawler name");
        }
        return ResponseEntity.ok(crawlerName + " Crawler stopped");
     }
}
