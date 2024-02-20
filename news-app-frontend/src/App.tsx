import React from 'react';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';

import styles from './App.module.scss';
// Assuming the other imports remain the same
import AllNewsList from './views/HomePage/AllNewsList';
import YleNewsList from './views/YleNews/YleNewsList';
import { NavBar } from './components/NavBar/NavBar';
function App() {
  return (

    <Router>
      <NavBar />
      <div className={styles.mainContent}>
        <Routes>
          <Route path="/" element={<AllNewsList />} />
          <Route path="/source/all" element={<AllNewsList />} />
          <Route path="/source/yle" element={<YleNewsList />} />
        </Routes>
      </div>
    </Router >


  );
}

export default App;