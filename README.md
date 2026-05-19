# AboglerFM

AboglerFM is a music analytics platform built around Last.fm listening data. It syncs your history, generates analytics (top artists, top tracks, listening timeline, mood analysis), and provides AI-powered recommendations using Ollama.

---

## Features

- Last.fm OAuth login → JWT session
- Listening history sync
- Top artists & top tracks analytics
- Listening timeline (daily activity)
- Mood analysis
- AI recommendations (Ollama: `qwen2.5:3b`)
- Redis caching (10-minute TTL)
- Global + per-user rate limiting (Resilience4j)

---

## Tech Stack

**Backend**
- Java + Spring Boot **4.0.6** *(update if your pom differs)*
- PostgreSQL
- Redis
- JWT authentication
- Resilience4j rate limiting

**Frontend**
- React + Vite
- Tailwind CSS
- Recharts (charts)

**AI**
- Ollama (`qwen2.5:3b` model)

---
