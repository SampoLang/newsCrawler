import { useState, useEffect } from 'react';
import { fetchAllNews } from '../services/newsService';
import { NewsArticle } from '../types/NewsTypes';

export function useFetchAllNews() {
    const [data, setData] = useState<NewsArticle[]>([]);
    const fetchUrl = 'http://localhost:8080/api/news';

    async function fetchData() {
        try {
            const newsData = await fetchAllNews(fetchUrl);
            setData(newsData);
        } catch (err) {
            console.log(err);
        }
    }

    useEffect(() => {
        fetchData();
    }, []); // Remove data from the dependency array to avoid infinite loops

    return { data, refresh: fetchData }; // Expose data and refresh function
}