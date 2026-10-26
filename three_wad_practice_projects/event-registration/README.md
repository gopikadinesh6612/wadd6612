# Event Registration Management System

This practice project contains a Spring Boot + Maven + MongoDB backend, MockMvc tests, and a React/Vite frontend.

## Prerequisites
- JDK 17 or newer
- Maven 3.9+
- MongoDB running on `localhost:27017`
- Node.js 18+

## Run backend
Open a terminal in `backend` and run:
```bash
mvn spring-boot:run
```
Backend URL: `http://localhost:8080`

Run tests:
```bash
mvn test
```

## Run frontend
Open another terminal in `frontend`:
```bash
npm install
npm run dev
```
Vite usually starts at `http://localhost:5173`.

## API endpoints
- POST /events
- GET /events
- GET /events/types
- POST /registrations
- GET /registrations
- GET /registrations/count-by-type

The aggregation counts registration/loan documents, not the number of copies/seats requested.
