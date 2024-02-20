package com.example.newsRetrievalSystem.service;

import com.example.newsRetrievalSystem.exception.ArticleNotFoundException;
import com.example.newsRetrievalSystem.model.entity.NewsEntity;
import com.example.newsRetrievalSystem.repository.NewsRepository;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@EnableScheduling
public class YleNewsCrawlerImpl extends BaseCrawler implements YleNewsCrawlerService {
    //base url for the news site
    private static final String BASE_URL = "https://yle.fi";
    //end url for the news site to fetch the lates news
    private static final String END_URL = "/uutiset/tuoreimmat";
    //amount of articles to fetch
    private static final Integer ARTICLE_AMOUNT = 10;
    //css query to fetch the links to the articles
    private static final String CSS_lINK_QUERY = "a.underlay-link";
    //css query to fetch the article content for the paragraph elements
    private static final String CSS_PUBLISH_DATE_QUERY = "time.aw-1266ca3.cPqzUm.yle__article__date--published";
    private static final String CSS_TEXT_PARAGRAPGH_QUERY = "p.yle__article__paragraph";
    private final NewsRepository newsRepository;



    @Autowired
    public YleNewsCrawlerImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }
    @Override
    protected void crawl() {
            fetchNews();
    }



    /**
     * Fetches the latest news articles and returns a list of NewsEntity objects.
     * Attempts to fetch up to ARTICLE_AMOUNT of news articles.
     * @return A list of NewsEntity objects containing the fetched news articles.
     */
public void fetchNews() {
    long startTime = System.currentTimeMillis();
    log.info("Starting to fetch news from {}", BASE_URL + END_URL);

        List<NewsEntity> newsEntities = new ArrayList<>();
        try {
            //Fetch all the news links from the main page
            Document doc = fetchPage(BASE_URL+END_URL);
            if (doc == null) {
                log.warn("Failed to retrieve the document from the URL: {}", BASE_URL + END_URL);
                return ;
            }
            Elements newsElements = doc.select(CSS_lINK_QUERY);
            if (newsElements == null || newsElements.isEmpty()) {
                log.warn("No news articles found using the CSS query: {}", CSS_lINK_QUERY);
                return ;
            }
            //Fetch the first 10 news articles
            fetchAndAddNewsEntities(newsElements, newsEntities);
            log.info("Successfully fetched {} news articles.", newsEntities.size());
            //filter duplicates by title
            List <NewsEntity> filteredNewsEntities = filterDuplicatesByTitle(newsEntities, newsRepository);
            //save the news to the database
            newsRepository.saveAll(filteredNewsEntities);
            log.info("Saved {} new news articles to the database", newsEntities.size());


        } catch (IOException e) {
            handleError("IO Exception while fetching news from " + BASE_URL + END_URL, e);
        } catch (ArticleNotFoundException e) {
            handleError("Article not found", e);
        } catch (Exception e) {
            handleError("General error while fetching news", e);
        }

    long endTime = System.currentTimeMillis();
    long timeTaken = endTime - startTime;
    log.info("fetchNews method executed in {} milliseconds", timeTaken);

}
    private void fetchAndAddNewsEntities(Elements newsElements, List<NewsEntity> newsEntities) throws ArticleNotFoundException, IOException {
        int count = 0;
        for (Element element : newsElements) {
            if (count >= ARTICLE_AMOUNT) {
                break;
            }
            String link = BASE_URL + element.attr("href");
            NewsEntity newsEntity = fetchArticleData(link);
            if (isValidNewsEntity(newsEntity)) {
                count++;
                newsEntities.add(newsEntity);
            }
        }
    }

    /**
     * Fetches data from a given article URL and creates a NewsEntity object.
     * @param link The URL of the article to fetch.
     * @return A NewsEntity object containing the article's data.
     */
    private NewsEntity fetchArticleData(String link) throws IOException, ArticleNotFoundException {
        log.debug("Fetching article data from {}", link);
        Document articleDoc = fetchPage(link);
        if (articleDoc == null) {
            log.warn("Failed to retrieve the article document from the URL: {}", link);
            return null;
        }

        String title = articleDoc.select("h1").text();
        if (title == null || title.isEmpty()) {
            log.warn("No title found for article at URL: {}", link);
            title = "";
        }
        Element dateElement = articleDoc.selectFirst(CSS_PUBLISH_DATE_QUERY);
        OffsetDateTime publishDateTime = null;
        if (dateElement == null) {
            log.warn("No date found for article at URL: {}", link);
        } else {
            publishDateTime = OffsetDateTime.parse(dateElement.attr("datetime").toString());
        }

        Elements pElements = articleDoc.select(CSS_TEXT_PARAGRAPGH_QUERY);
        String snippet = "";
        if (pElements.isEmpty()) {
            log.warn("No paragraphs found using CSS query '{}' for article at URL: {}", CSS_TEXT_PARAGRAPGH_QUERY, link);
        } else {
            snippet = createContentSnippet(pElements);
        }

        NewsEntity newsEntity = new NewsEntity();
        newsEntity.setLink(link);
        newsEntity.setPublishDate(publishDateTime);
        newsEntity.setTitle(title);
        newsEntity.setContentSnippet(snippet);
        log.debug("Successfully fetched data for article: {}", title);

        return newsEntity;

    }

    /**
     * Creates a content snippet from a given set of paragraph elements.
     * This method attempts to build a snippet with up to 200 words from the provided paragraphs.
     * @param pElements The paragraph elements containing the article content.
     * @return A string containing up to 200 words formed from the provided paragraph elements.
     */
    private String createContentSnippet(Elements pElements) {
        log.debug("Creating content snippet.");
        StringBuilder snippetBuilder = new StringBuilder();

        int wordCount = 0;
        //Yle had their paragraphs split into multiple elements, so we need to iterate through them
        //Iterate through all the paragraphs and add words to the snippet until the word count reaches 200
        for (Element pElement : pElements){
            String[] words = pElement.text().split(" ");
            for (String word : words) {
                if (wordCount >= 200) {
                    break;
                }
                snippetBuilder.append(word).append(" ");
                wordCount++;
            }
        }
        log.debug("Content snippet created with {} words.", wordCount);

        return snippetBuilder.toString().trim();
    }
}

