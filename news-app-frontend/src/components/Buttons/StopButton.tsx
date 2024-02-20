import React from 'react';
import Button from '@mui/material/Button';
import { stopCrawler } from "../../services/newsService";
type Props = {
    crawlerName: string;
};
const StopCrawlerButton: React.FC<Props> = ({ crawlerName }) => {


    const handleStopCrawler = async () => {
        try {
            await stopCrawler(crawlerName);
            // Handle any post-start actions if needed
        } catch (error) {
            console.error("Error starting the crawler:", error);
        }
    };

    return (
        <Button
            variant="contained"
            color="error"
            onClick={handleStopCrawler}
        >
            Stop Crawler
        </Button>
    );
}

export default StopCrawlerButton;