import React from 'react';
import Button from '@mui/material/Button';
import RefreshIcon from '@mui/icons-material/Refresh';

type Props = {
    refreshNews: () => void;
};

const RefreshButton: React.FC<Props> = ({ refreshNews }) => (
    <Button
        variant="outlined"
        color="primary"
        startIcon={<RefreshIcon />}
        onClick={refreshNews}
        sx={{ margin: 2 }}
    >
        Fetch New News
    </Button>
);

export default RefreshButton;