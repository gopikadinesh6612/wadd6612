import React, { useEffect, useState } from 'react';

const API = 'http://localhost:8080';

function EventList() {
  const [events, setEvents] = useState([]);
  const [error, setError] = useState('');
  useEffect(() => {
    fetch(`${API}/events`)
      .then(r => { if (!r.ok) throw new Error('Could not load records'); return r.json(); })
      .then(setEvents)
      .catch(e => setError(e.message));
  }, []);
  const colors = { 'TECH': '#2563eb', 'CULTURAL': '#16a34a', 'SPORTS': '#9333ea' };
  return <section>
    <h2>Event Registration list</h2>
    {error && <p className="error">{error}. Check that the backend is running.</p>}
    <div className="grid">
      {events.map(event => <article key={event.eventId} className="room-card" style={{borderColor: colors[event.eventType] || '#64748b'}} >
        <h3>{event.eventName}</h3>
        <p>{event.venue}</p>
        <p><b>Type:</b> {event.eventType}</p>
        <p><b>Available:</b> {event.availableSeats}</p>
        <p><b>Status:</b> {event.availabilityStatus}</p>
      </article>)}
    </div>
  </section>;
}

function BookingStatistics() {
  const [counts, setCounts] = useState([]);
  const [error, setError] = useState('');
  useEffect(() => {
    fetch(`${API}/registrations/count-by-type`)
      .then(r => { if (!r.ok) throw new Error('Could not load statistics'); return r.json(); })
      .then(setCounts)
      .catch(e => setError(e.message));
  }, []);
  return <section>
    <h2>Registrations by event type</h2>
    {error && <p className="error">{error}. Check that the backend is running.</p>}
    <div className="stats">
      {counts.map(item => <article className="stat-card" key={item.eventType}>
        <h3>{item.eventType}</h3>
        <p className="number">{item.count}</p>
        <p>Registration count</p>
      </article>)}
      {counts.length === 0 && !error && <p>No count data yet. Add a booking/registration first.</p>}
    </div>
  </section>;
}

export default function App() {
  return <main>
    <header><p className="eyebrow">Spring Boot + MongoDB + React</p><h1>Event Registration Dashboard</h1><p>Records and grouped statistics from the backend API</p></header>
    <EventList />
    <BookingStatistics />
  </main>;
}
