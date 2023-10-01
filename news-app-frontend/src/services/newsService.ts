import axios from 'axios';

export async function fetchAllNews(fetchUrl: string) {
    try {
        const response = await axios.get(fetchUrl);
        return response.data;
    }
    catch (error) {
        console.error('Error fetching all the news:', error);
        return [];
    }
}
// The new deleteNews method
export async function deleteNews(newsId: number, fetchUrl: string) {
    try {
        const response = await axios.delete(fetchUrl + "/" + newsId);
        return response.data;
    }
    catch (error) {
        console.error(`Error deleting the news with ID ${newsId}:`, error);
        throw error;
    }
}
export async function startCrawler(crawlerName: string) {
    try {
        const response = await axios.post(`http://localhost:8080/api/crawler/${crawlerName}/start`);
        console.log("starting the crawler");
    }
    catch (error) {
        throw error;
    }
}
export async function stopCrawler(crawlerName: string) {
    try {
        const response = await axios.post(`http://localhost:8080/api/crawler/${crawlerName}/stop`);
        console.log("stopping the crawler");
    }
    catch (error) {
        throw error;
    }
}




