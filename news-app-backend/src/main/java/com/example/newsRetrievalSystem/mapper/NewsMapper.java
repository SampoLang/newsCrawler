package com.example.newsRetrievalSystem.mapper;

import com.example.newsRetrievalSystem.model.dto.NewsDTO;
import com.example.newsRetrievalSystem.model.entity.NewsEntity;

import java.util.List;
import java.util.stream.Collectors;

public class NewsMapper {
    public static NewsDTO toDTO(NewsEntity entity) {
        NewsDTO dto = new NewsDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setLink(entity.getLink());
        dto.setSnippet(entity.getContentSnippet());
        dto.setPublishDate(entity.getPublishDate());
        dto.setRetrievalTime(entity.getRetrievalTime());
        return dto;
    }

    public static List<NewsDTO> toDTOs(List<NewsEntity> entities) {
        return entities.stream()
                .map(NewsMapper::toDTO)
                .collect(Collectors.toList());
    }
}

