<h1 align="center">Welcome to PathToHire - Backend 👋</h1>
<p align="center">
  <img alt="Version" src="https://img.shields.io/badge/version-0.0.1-blue.svg?cacheSeconds=2592000" />
</p>

> This is the Spring Boot backend for **PathToHire** — a platform helping job seekers with AI resume tools, application tracking, and job organization.

---

## 🚀 Features

- 📄 Resume vs Job Description Matching (Stanford CoreNLP)
- ✍️ Resume Rewriter (LLaMA 3.1 integration)
- 🤖 AI Assistant for Resume Q&A (LLaMA backend)
- 📌 Application Tracker (with timeline + status)
- 📅 Calendar View for applications
- 🔐 OAuth2 Google Login (Spring Security + OAuth2)
- 💳 Stripe Subscription Integration (tested, ready for deploy)

---

## 🧱 Tech Stack

- ⚙️ **Backend**: Java, Spring Boot, JPA, Hibernate, OAuth2
- 🧠 **AI/NLP**: Stanford CoreNLP, LLaMA
- 🗄️ **Database**: PostgreSQL (via Supabase)

---

## 🛠 Getting Started

### Prerequisites

- Java 17+
- Maven
- PostgreSQL (running + database configured)
- `application.properties` or `.env` with required keys (OAuth, DB, Stripe)

### Run the server

```bash
mvn spring-boot:run
