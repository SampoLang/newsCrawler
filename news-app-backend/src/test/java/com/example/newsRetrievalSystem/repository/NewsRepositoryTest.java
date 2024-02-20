package com.example.newsRetrievalSystem.repository;

import com.example.newsRetrievalSystem.model.entity.NewsEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class NewsRepositoryTest {
    @Autowired
    private NewsRepository repository;
    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void CheckIfNewsCanBeFoundByTitle() {
        //given
        // Prepare test data
        String title = "Test News Title";
        OffsetDateTime publishDate = OffsetDateTime.of(2024, 2, 17, 10, 0, 0, 0, ZoneOffset.UTC);
        String link = "https://example.com/test-news";
        String contentSnippet = "This is a snippet of the test news content.";
        // Create a new NewsEntity instance
        NewsEntity newsEntity = new NewsEntity(title, publishDate, link, contentSnippet);
        repository.save(newsEntity);

        //when
        boolean expected = repository.existsByTitle(title);

        //then
        assertThat(expected).isTrue();
    }

    @Test
    void CheckIfNewsDoesNotExistByTitle() {
    //given
    String title = "Test News Title";
    //when
    boolean expected = repository.existsByTitle(title);
    //then
    assertThat(expected).isFalse();
}

    @Test
    void findAllByOrderByIdDesc() {
        //given
        // create three different test data
        String title1 = "95-mies Torilla";
        String contentSnippet1 = "95-mies nähty torilla";
        OffsetDateTime publishDate = OffsetDateTime.of(2024, 2, 17, 10, 0, 0, 0, ZoneOffset.UTC);
        String link = "https://example.com/test-news";

        String title2 = "Kissa puussa";
        String contentSnippet2 = "Kissa puussa jumissa, palokunta paikalla";

        String title3 = "Koira puree miestä";
        String contentSnippet3 = "Koira puri miestä, poliisi paikalla";

        // Create a new NewsEntity instance
        NewsEntity newsEntity1 = new NewsEntity(title1, publishDate, link, contentSnippet1);
        NewsEntity newsEntity2 = new NewsEntity(title2, publishDate, link, contentSnippet2);
        NewsEntity newsEntity3 = new NewsEntity(title3, publishDate, link, contentSnippet3);
        repository.save(newsEntity1);
        repository.save(newsEntity2);
        repository.save(newsEntity3);

        //when
        //check if the repository can find all news and return them in descending order based on the id
        List<NewsEntity> news = repository.findAllByOrderByIdDesc();
        Boolean expected = news.get(0).getId() > news.get(1).getId() && news.get(1).getId() > news.get(2).getId();
        //then
        assertThat(expected).isTrue();
    }

    @Test
    void findAllByOrderByPublishDateDesc() {
        //given
        // create three different test data
        String title1 = "95-mies Torilla";
        String contentSnippet1 = "95-mies nähty torilla";
        OffsetDateTime publishDate1 = OffsetDateTime.of(2024, 2, 17, 10, 0, 0, 0, ZoneOffset.UTC);
        String link = "https://example.com/test-news";
        String title2 = "Kissa puussa";
        OffsetDateTime publishDate2 = OffsetDateTime.of(2024, 1, 11, 10, 0, 0, 0, ZoneOffset.UTC);
        String contentSnippet2 = "Kissa puussa jumissa, palokunta paikalla";
        String title3 = "Koira puree miestä";
        OffsetDateTime publishDate3 = OffsetDateTime.of(2024, 3, 20, 10, 0, 0, 0, ZoneOffset.UTC);
        String contentSnippet3 = "Koira puri miestä, poliisi paikalla";
        // Create a new NewsEntity instance
        NewsEntity newsEntity1 = new NewsEntity(title1, publishDate1, link, contentSnippet1);
        NewsEntity newsEntity2 = new NewsEntity(title2, publishDate2, link, contentSnippet2);
        NewsEntity newsEntity3 = new NewsEntity(title3, publishDate3, link, contentSnippet3);
        repository.save(newsEntity1);
        repository.save(newsEntity2);
        repository.save(newsEntity3);
        //when
        //check if the repository can find all news and return them in descending order based on the publish date
        List<NewsEntity> news = repository.findAllByOrderByPublishDateDesc();
        Boolean expected = news.get(0).getPublishDate().isAfter(news.get(1).getPublishDate()) && news.get(1).getPublishDate().isAfter(news.get(2).getPublishDate());
        //then
        assertThat(expected).isTrue();

    }
    @Test
    void checkIfDeleteNewsByIdCompletes() {
        //given
        // Prepare test data
        String title = "Test News Title";
        OffsetDateTime publishDate = OffsetDateTime.of(2024, 2, 17, 10, 0, 0, 0, ZoneOffset.UTC);
        String link = "https://example.com/test-news";
        String contentSnippet = "This is a snippet of the test news content.";
        // Create a new NewsEntity instance
        NewsEntity newsEntity = new NewsEntity(title, publishDate, link, contentSnippet);
        repository.save(newsEntity);
        //when
        //check if the repository can delete a news by id
        repository.deleteById(newsEntity.getId());
        //then
        assertThat(repository.existsById(newsEntity.getId())).isFalse();
    }

    @Test
    void getNewsById() {
        //given
        // Prepare test data
        String title = "Test News Title";
        OffsetDateTime publishDate = OffsetDateTime.of(2024, 2, 17, 10, 0, 0, 0, ZoneOffset.UTC);
        String link = "https://example.com/test-news";
        String contentSnippet = "This is a snippet of the test news content.";
        // Create a new NewsEntity instance
        NewsEntity newsEntity = new NewsEntity(title, publishDate, link, contentSnippet);
        repository.save(newsEntity);
        //when
        //check if the repository can find a news by id
        NewsEntity news = repository.findById(newsEntity.getId()).orElse(null);
        //then
        assertThat(news).isNotNull();
    }

    @Test
    void findAllNewsByTitle() {
        //given
        // create three different test data
        String title1 = "95-mies Torilla";
        String contentSnippet1 = "95-mies nähty torilla";
        OffsetDateTime publishDate = OffsetDateTime.of(2024, 2, 17, 10, 0, 0, 0, ZoneOffset.UTC);
        String link = "https://example.com/test-news";
        String title2 = "Kissa puussa";
        String contentSnippet2 = "Kissa puussa jumissa, palokunta paikalla";
        String title3 = "Koira puree miestä";
        String contentSnippet3 = "Koira puri miestä, poliisi paikalla";
        // Create a new NewsEntity instance
        NewsEntity newsEntity1 = new NewsEntity(title1, publishDate, link, contentSnippet1);
        NewsEntity newsEntity2 = new NewsEntity(title2, publishDate, link, contentSnippet2);
        NewsEntity newsEntity3 = new NewsEntity(title3, publishDate, link, contentSnippet3);
        repository.save(newsEntity1);
        repository.save(newsEntity2);
        repository.save(newsEntity3);
        //when
        //check if the repository can find all news by title
        Set<String> news = repository.findAllByTitleIn(Set.of(title1, title2, title3));
        Boolean expected = news.size() == 3;
        //then
        assertThat(expected).isTrue();
    }

}