import { useState, useEffect } from 'react';
import SingleNewsArticle from '../../components/NewsArticle/SingleNewsArticle';
import { NewsArticle } from '../../types/NewsTypes';
import { useFetchAllNews } from '../../hooks/useFetchAllNews';
import { Button } from "@mui/material";
import RefreshIcon from '@mui/icons-material/Refresh';
import RefreshButton from "../../components/Buttons/RefreshButton";
import StartCrawlerButton from '../../components/Buttons/StartButton';
import StopCrawlerButton from '../../components/Buttons/StopButton';
import styles from "./YleNewsList.module.scss"
const YleNewsList: React.FC = () => {
    const [articles, setArticles] = useState<NewsArticle[]>([]);
    const { data, refresh } = useFetchAllNews();


    useEffect(() => {
        setArticles(data);
    }, [data]);
    const handleArticleDelete = (deletedArticleId: number) => {
        setArticles(prevArticles => prevArticles.filter(article => article.id !== deletedArticleId));
    };
    return (
        <div className={styles.newsList}>
            <RefreshButton refreshNews={refresh} />
            <StartCrawlerButton crawlerName={"yle"} />
            <StopCrawlerButton crawlerName={"yle"} />

            {articles.map((article) => (
                <SingleNewsArticle key={article.id} article={article} onDelete={handleArticleDelete} />
            ))}
        </div>
    );
};

export default YleNewsList;