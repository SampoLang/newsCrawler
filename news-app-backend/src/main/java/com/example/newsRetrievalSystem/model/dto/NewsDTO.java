package com.example.newsRetrievalSystem.model.dto;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class NewsDTO {
    private Long id;
    private String title;
    private String link;
    private String snippet;
    private LocalDateTime retrievalTime;
    private OffsetDateTime publishDate;

    public NewsDTO() {}

    public NewsDTO(Long id, String title, String link, String snippet,OffsetDateTime publishDate, LocalDateTime retrievalTime) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.snippet = snippet;
        this.publishDate = publishDate;
        this.retrievalTime = retrievalTime;

    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public OffsetDateTime getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(OffsetDateTime publishDate) {
        this.publishDate = publishDate;
    }

    public LocalDateTime getRetrievalTime() {
        return retrievalTime;
    }

    public void setRetrievalTime(LocalDateTime retrievalTime) {
        this.retrievalTime = retrievalTime;
    }
}
