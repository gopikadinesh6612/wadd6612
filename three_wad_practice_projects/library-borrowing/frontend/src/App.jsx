import React, { useEffect, useState } from 'react';

const API = 'http://localhost:8080';

function BookList() {
  const [books, setBooks] = useState([]);
  const [error, setError] = useState('');
  useEffect(() => {
    fetch(`${API}/books`)
      .then(r => { if (!r.ok) throw new Error('Could not load records'); return r.json(); })
      .then(setBooks)
      .catch(e => setError(e.message));
  }, []);
  const colors = { 'FICTION': '#2563eb', 'SCIENCE': '#16a34a', 'HISTORY': '#9333ea' };
  return <section>
    <h2>Library Book Borrowing list</h2>
    {error && <p className="error">{error}. Check that the backend is running.</p>}
    <div className="grid">
      {books.map(book => <article key={book.bookId} className="room-card" style={{borderColor: colors[book.category] || '#64748b'}} >
        <h3>{book.title}</h3>
        <p>{book.author}</p>
        <p><b>Type:</b> {book.category}</p>
        <p><b>Available:</b> {book.availableCopies}</p>
        <p><b>Status:</b> {book.availabilityStatus}</p>
      </article>)}
    </div>
  </section>;
}

function BookingStatistics() {
  const [counts, setCounts] = useState([]);
  const [error, setError] = useState('');
  useEffect(() => {
    fetch(`${API}/loans/count-by-category`)
      .then(r => { if (!r.ok) throw new Error('Could not load statistics'); return r.json(); })
      .then(setCounts)
      .catch(e => setError(e.message));
  }, []);
  return <section>
    <h2>Loans by category</h2>
    {error && <p className="error">{error}. Check that the backend is running.</p>}
    <div className="stats">
      {counts.map(item => <article className="stat-card" key={item.category}>
        <h3>{item.category}</h3>
        <p className="number">{item.count}</p>
        <p>Loan count</p>
      </article>)}
      {counts.length === 0 && !error && <p>No count data yet. Add a booking/registration first.</p>}
    </div>
  </section>;
}

export default function App() {
  return <main>
    <header><p className="eyebrow">Spring Boot + MongoDB + React</p><h1>Library Borrowing Dashboard</h1><p>Records and grouped statistics from the backend API</p></header>
    <BookList />
    <BookingStatistics />
  </main>;
}
