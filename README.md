# News Crawler

A comprehensive news retrieval system that fetches and displays news articles.

## Overview

This repository contains both the backend and frontend components of the News Crawler application.

## Backend

The backend is developed in Java and provides APIs for fetching and managing news articles. It uses Maven for dependency management.

- **Main Application**: [NewsRetrievalSystemApplication.java](https://github.com/SampoLang/newsCrawler/blob/master/news-app-backend/src/main/java/com/example/newsRetrievalSystem/NewsRetrievalSystemApplication.java)
- **Controllers**: Handles incoming HTTP requests.
  - [CrawlerController.java](https://github.com/SampoLang/newsCrawler/blob/master/news-app-backend/src/main/java/com/example/newsRetrievalSystem/controller/CrawlerController.java)
  - [NewsController.java](https://github.com/SampoLang/newsCrawler/blob/master/news-app-backend/src/main/java/com/example/newsRetrievalSystem/controller/NewsController.java)
- **Services**: Contains the business logic.
  - [NewsService.java](https://github.com/SampoLang/newsCrawler/blob/master/news-app-backend/src/main/java/com/example/newsRetrievalSystem/service/NewsService.java)
  - [YleNewsCrawlerService.java](https://github.com/SampoLang/newsCrawler/blob/master/news-app-backend/src/main/java/com/example/newsRetrievalSystem/service/YleNewsCrawlerService.java)

### Backend API Endpoints

### News Controller

Base URL: `/api/news`

- **GET `/`**: Fetch all news articles.
  - Response: List of `NewsDTO` objects.
  
- **GET `/{id}`**: Fetch a specific news article by its ID.
  - Path Parameters:
    - `id`: ID of the news article.
  - Response: `NewsDTO` object.
  
- **GET `/latest`**: Fetch the latest news article.
  - Response: `NewsDTO` object.
  
- **DELETE `/{id}`**: Delete a specific news article by its ID.
  - Path Parameters:
    - `id`: ID of the news article.
  - Response: HTTP status indicating success or failure.

[NewsController.java](https://github.com/SampoLang/newsCrawler/blob/master/news-app-backend/src/main/java/com/example/newsRetrievalSystem/controller/NewsController.java)

### Crawler Controller

Base URL: `/api/crawler`

- **POST `/{crawlerName}/start`**: Start a specific crawler.
  - Path Parameters:
    - `crawlerName`: Name of the crawler (e.g., "yle").
  - Response: String message indicating the status of the crawler.
  
- **POST `/{crawlerName}/stop`**: Stop a specific crawler.
  - Path Parameters:
    - `crawlerName`: Name of the crawler (e.g., "yle").
  - Response: String message indicating the status of the crawler.

[CrawlerController.java](https://github.com/SampoLang/newsCrawler/blob/master/news-app-backend/src/main/java/com/example/newsRetrievalSystem/controller/CrawlerController.java)
## Frontend

The frontend is developed using React and provides a user interface for displaying the fetched news articles.

- **Main Application**: [App.tsx](https://github.com/SampoLang/newsCrawler/blob/master/news-app-frontend/src/App.tsx)
- **Components**: Reusable UI components.
  - [Header.tsx](https://github.com/SampoLang/newsCrawler/blob/master/news-app-frontend/src/components/Header/Header.tsx)
  - [NavBar.tsx](https://github.com/SampoLang/newsCrawler/blob/master/news-app-frontend/src/components/NavBar/NavBar.tsx)
- **Views**: Contains the main views/pages of the application.
  - [HomePage](https://github.com/SampoLang/newsCrawler/blob/master/news-app-frontend/src/views/HomePage/AllNewsList.tsx)
  - [YleNews](https://github.com/SampoLang/newsCrawler/blob/master/news-app-frontend/src/views/YleNews/YleNewsList.tsx)

### Setup & Installation

(TODO: Add setup and installation instructions for both backend and frontend.)

### Usage

(TODO: Add usage instructions, including how to start the application, access the frontend, and use the provided features.)

### Contributing

(TODO: Add contribution guidelines, if any.)
