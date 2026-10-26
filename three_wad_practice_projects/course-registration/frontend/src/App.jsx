import React, { useEffect, useState } from 'react';

const API = 'http://localhost:8080';

function CourseList() {
  const [courses, setCourses] = useState([]);
  const [error, setError] = useState('');
  useEffect(() => {
    fetch(`${API}/courses`)
      .then(r => { if (!r.ok) throw new Error('Could not load records'); return r.json(); })
      .then(setCourses)
      .catch(e => setError(e.message));
  }, []);
  const colors = { 'BEGINNER': '#2563eb', 'INTERMEDIATE': '#16a34a', 'ADVANCED': '#9333ea' };
  return <section>
    <h2>Course Registration list</h2>
    {error && <p className="error">{error}. Check that the backend is running.</p>}
    <div className="grid">
      {courses.map(course => <article key={course.courseId} className="room-card" style={{borderColor: colors[course.courseLevel] || '#64748b'}} >
        <h3>{course.courseName}</h3>
        <p>{course.instructorName}</p>
        <p><b>Type:</b> {course.courseLevel}</p>
        <p><b>Available:</b> {course.availableSeats}</p>
        <p><b>Status:</b> {course.availabilityStatus}</p>
      </article>)}
    </div>
  </section>;
}

function BookingStatistics() {
  const [counts, setCounts] = useState([]);
  const [error, setError] = useState('');
  useEffect(() => {
    fetch(`${API}/registrations/count-by-level`)
      .then(r => { if (!r.ok) throw new Error('Could not load statistics'); return r.json(); })
      .then(setCounts)
      .catch(e => setError(e.message));
  }, []);
  return <section>
    <h2>Registrations by course level</h2>
    {error && <p className="error">{error}. Check that the backend is running.</p>}
    <div className="stats">
      {counts.map(item => <article className="stat-card" key={item.courseLevel}>
        <h3>{item.courseLevel}</h3>
        <p className="number">{item.count}</p>
        <p>Registration count</p>
      </article>)}
      {counts.length === 0 && !error && <p>No count data yet. Add a booking/registration first.</p>}
    </div>
  </section>;
}

export default function App() {
  return <main>
    <header><p className="eyebrow">Spring Boot + MongoDB + React</p><h1>Course Registration Dashboard</h1><p>Records and grouped statistics from the backend API</p></header>
    <CourseList />
    <BookingStatistics />
  </main>;
}
