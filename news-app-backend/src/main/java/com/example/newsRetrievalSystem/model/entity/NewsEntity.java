package com.example.newsRetrievalSystem.model.entity;

import jakarta.persistence.*;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
public class NewsEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime retrievalTime;

    @Column(name = "publish_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime publishDate;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 20000)
    private String link;

    @Column(nullable = false, length = 20000)
    private String contentSnippet;

    public NewsEntity() {
        this.retrievalTime = LocalDateTime.now();
    }

    public NewsEntity(String title, OffsetDateTime publishDate,  String link, String contentSnippet) {
        this.retrievalTime = LocalDateTime.now();
        this.publishDate = publishDate;
        this.title = title;
        this.link = link;
        this.contentSnippet = contentSnippet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getRetrievalTime() {
        return retrievalTime;
    }

    public void setRetrievalTime(LocalDateTime retrievalTime) {
        this.retrievalTime = retrievalTime;
    }

    public OffsetDateTime getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(OffsetDateTime publishDate) {
        this.publishDate = publishDate;
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

    public String getContentSnippet() {
        return contentSnippet;
    }

    public void setContentSnippet(String contentSnippet) {
        this.contentSnippet = contentSnippet;
    }
}
