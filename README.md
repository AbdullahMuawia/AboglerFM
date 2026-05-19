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
<img width="1371" height="867" alt="image" src="https://github.com/user-attachments/assets/6c00ce59-cc66-4def-8db9-d4cfdd08a142" />

---
<img width="1302" height="867" alt="image" src="https://github.com/user-attachments/assets/8df5806d-531c-4631-8af2-4fdb0aba5d80" />
---
<img width="1239" height="881" alt="image" src="https://github.com/user-attachments/assets/d1db615f-2852-433d-b1e4-1dabc2479aff" />
---
<img width="1191" height="758" alt="image" src="https://github.com/user-attachments/assets/d6815049-6ced-41e6-8ad6-9f5c45f7ed2c" />
---
<img width="1234" height="651" alt="image" src="https://github.com/user-attachments/assets/6874625b-110e-47a0-bd06-b105c31e5646" />



