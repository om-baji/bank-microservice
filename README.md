﻿# 🏦 Bank Service

A modular, event-driven banking microservice built using **Spring Boot**, supporting account management, user authentication with JWT and refresh tokens, transactional operations, and Kafka-based event publishing for integration with auxiliary services like mail, SMS, and logging.

---

## ✨ Features

- ✅ Account creation & balance fetching
- 🔐 Secure user authentication (JWT + Refresh Tokens)
- 💸 Transaction processing (with pagination and account-specific retrieval)
- 📤 Kafka-based event publishing (account created, transaction success/failure)
- 📊 Health status endpoint (with user/account counts)
- 📦 Modular service layer
- 🛡️ Spring Security for authentication and authorization

---

## System Architechute 

![image](https://github.com/user-attachments/assets/89523655-68db-4860-874b-0670bf5da96d)

---

## 🔐 API Endpoints

### 🧾 Authentication
| Method | Endpoint       | Description              |
|--------|----------------|--------------------------|
| POST   | `/register`    | Register a new user      |
| POST   | `/login`       | Login and get JWT token  |
| POST   | `/refresh`     | Get new JWT using refresh token |

---

### 🏦 Accounts
| Method | Endpoint           | Description               |
|--------|--------------------|---------------------------|
| GET    | `/accounts`        | Get all accounts          |
| GET    | `/accounts/{id}`   | Get account by ID         |
| GET    | `/accounts/{id}/balance` | Get balance for account |
| POST   | `/accounts`        | Create a new account      |

---

### 💸 Transactions
| Method | Endpoint                     | Description                         |
|--------|------------------------------|-------------------------------------|
| GET    | `/transactions`              | Get paginated transactions by user |
| GET    | `/transactions/account/{id}` | Get all transactions for account   |
| GET    | `/transactions/{id}`         | Get a specific transaction         |
| POST   | `/transaction/execute`       | Execute a transaction              |

---

### ✅ Health
| Method | Endpoint    | Description               |
|--------|-------------|---------------------------|
| GET    | `/status`   | App health and DB status  |

---

## 🛰️ Kafka Topics

| Event                    | Topic Name             | Triggered On                    |
|--------------------------|------------------------|---------------------------------|
| Account Created          | `banking.account.events`       | When a new account is created   |
| Transaction Executed     | `banking.transaction.events`   | On successful transaction       |
| Transaction Failed       | `banking.transaction.failures` | On failed transaction execution |

> These can be consumed by external services like `Mailer`, `SMS`, `Logger`.

---

## 🛠️ Technologies Used

- **Java 17+**
- **Spring Boot**
- **Spring Security + JWT**
- **Kafka (Spring Kafka)**
- **PostgreSQL / H2**
- **Lombok**
- **Gradle / Maven**
- **Docker-ready (optional)**

---

## ⚙️ Running Locally

1. Clone the repo:
   ```bash
   git clone https://github.com/your-username/ledger-bank-service.git
   cd ledger-bank-service
````

2. Set up your `.env` or `application.properties`:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/bankdb
   spring.datasource.username=postgres
   spring.datasource.password=password
   kafka.bootstrap-servers=localhost:9092
   spring.kafka.consumer.group-id=
   jwt.secret=your_jwt_secret_key
   refresh.secret=your_secret
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
   ```

3. Run the service:

   ```bash
   mvn spring-boot:run
   ```

---

## 🧪 Testing

Basic unit tests and integration tests can be placed in `/src/test/java`.

---
