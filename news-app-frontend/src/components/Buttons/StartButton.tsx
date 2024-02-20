import React from 'react';
import Button from '@mui/material/Button';
import { startCrawler } from "../../services/newsService";
type Props = {
    crawlerName: string;
};
const StartCrawlerButton: React.FC<Props> = ({ crawlerName }) => {


    const handleStartCrawler = async () => {
        try {
            await startCrawler(crawlerName);
            // Handle any post-start actions if needed
        } catch (error) {
            console.error("Error starting the crawler:", error);
        }
    };

    return (
        <Button variant="contained" color="primary" onClick={handleStartCrawler}>
            Start Crawler
        </Button>
    );
}

export default StartCrawlerButton;