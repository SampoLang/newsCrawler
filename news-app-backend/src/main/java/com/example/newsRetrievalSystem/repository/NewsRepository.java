package com.example.newsRetrievalSystem.repository;

import com.example.newsRetrievalSystem.model.dto.NewsDTO;
import com.example.newsRetrievalSystem.model.entity.NewsEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface NewsRepository extends JpaRepository<NewsEntity, Long> {
    boolean existsByTitle(String title);
    List<NewsEntity> findAllByOrderByIdDesc();
    List<NewsEntity> findAllByOrderByPublishDateDesc();

    @Query("SELECT n.title FROM NewsEntity n WHERE n.title IN :titles")
    Set<String> findAllByTitleIn(Set<String> titles);

    NewsEntity findTopByOrderByIdDesc();

}
