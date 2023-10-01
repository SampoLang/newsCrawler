import React, { useState } from 'react';
import Button from '@mui/material/Button';
import DeleteIcon from '@mui/icons-material/Delete';
import { NewsArticle } from '../../types/NewsTypes';
import styles from './SingleNewsArticle.module.scss';
import { deleteNews } from '../../services/newsService';
type Props = {
    article: NewsArticle;
    onDelete: (deletedArticleId: number) => void;
};
function truncateAfterWords(str: string, numberOfWords: number) {
    return str.split(" ").splice(0, numberOfWords).join(" ");
}

const NewsCard: React.FC<Props> = ({ article, onDelete }) => {
    const [showFullSnippet, setShowFullSnippet] = useState(false);
    const truncatedSnippet = truncateAfterWords(article.snippet, 50);
    const wordCount = article.snippet.split(" ").length;

    const handleDelete = async () => {
        if (window.confirm('Haluatko varmasti poistaa uutisen?')) {
            try {
                await deleteNews(article.id, 'http://localhost:8080/api/news');
                console.log('News deleted successfully');

                // Inform the parent that the article has been deleted
                onDelete(article.id);

            } catch (error) {
                console.error('Failed to delete news:', error);
            }
        }
    };
    return (
        <div className={styles.newsCard}>
            <h3>{article.title}</h3>
            <p>
                {showFullSnippet ? article.snippet : truncatedSnippet}
                {!showFullSnippet && wordCount > 50 && '...'}
                {!showFullSnippet && wordCount > 50 && (
                    <span
                        onClick={() => setShowFullSnippet(true)}
                        style={{ cursor: 'pointer', color: 'blue' }}
                    >
                        Lue lisää
                    </span>
                )}
            </p>
            <a href={article.link} title="Open article in a new tab">Lue koko artikkeli</a>
            <Button className={styles.deleteButton}
                variant="outlined" startIcon={<DeleteIcon />}
                onClick={handleDelete}
            >
                Poista
            </Button>

        </div>
    );
};

export default NewsCard;