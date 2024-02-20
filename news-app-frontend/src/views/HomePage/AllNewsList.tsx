import { useEffect, useState } from 'react';
import SingleNewsArticle from '../../components/NewsArticle/SingleNewsArticle';
import { useFetchAllNews } from '../../hooks/useFetchAllNews';
import styles from "./AllNewsList.module.scss"
import { NewsArticle } from '../../types/NewsTypes';
import RefreshButton from "../../components/Buttons/RefreshButton";
import StartCrawlerButton from '../../components/Buttons/StartButton';
import StopCrawlerButton from '../../components/Buttons/StopButton';
const AllNewsList: React.FC = () => {
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

export default AllNewsList;