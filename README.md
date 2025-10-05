# Pastebin-Mini-Demo
A Pastebin-like web service built with Spring Boot, enabling users to store plain text and retrieve it via unique, short URLs. Supports custom URLs, expiration times, and a simple Key Generation Service (KGS) to ensure unique URL hashes. Inspired by "Designing Pastebin" and system design principles from "System Design Interview" books.


---

## Table of Contents
- [Features](#features)
- [Tech Stack](#tech-stack)
- [System Design](#system-design)
- [Database Schema](#database-schema)
- [API Endpoints](#api-endpoints)
- [Installation](#installation)
- [Usage](#usage)
- [License](#license)

---

## Features
- Users can create "pastes" (text content) and receive a **unique short URL**.
- Supports **custom URLs** (optional) and **user-provided names**.
- Automatic expiration for pastes; default expiration is **2 years**.
- Prevents duplicate keys using a **Key Generation Service (KGS)**.
- Retrieve and delete pastes via REST APIs.
- Highly **read-optimized** and supports caching for frequently accessed pastes.
- Error handling and global exception management with Spring Boot `@ControllerAdvice`.

---

## Tech Stack
- **Backend:** Java, Spring Boot, Spring Data JPA
- **Database:** MySQL / PostgreSQL (for metadata storage)
- **Storage:** Local file system (can be replaced with object storage like S3)
- **Build Tools:** Maven
- **Others:** Base64 encoding for key generation, UUID for content keys

---

## System Design

### High-Level Architecture
- **Application Layer:** Handles all read/write requests, key generation, and API responses.
- **Metadata Storage:** Relational DB storing paste metadata (URL hash, content key, creation/expiration date, username).
- **Object Storage:** Stores the actual content of the pastes (text files).

### Key Generation Service (KGS)
- Generates **six-character unique URL hashes**.
- Maintains a database of unused/used keys to avoid collisions.
- Optionally caches some keys in memory for faster allocation.

---

## Database Schema

### Paste Table
| Column       | Type       | Description                          |
| ------------ | ---------- | ------------------------------------ |
| urlHash      | varchar(16)| Primary key, short URL for the paste |
| contentKey   | varchar(512)| Object key for the stored content    |
| userName     | varchar(6)| Optional user name                   |
| customUrl    | varchar(16)| Optional custom URL                  |
| createdAt    | datetime   | Creation timestamp                    |
| expiredAt    | datetime   | Expiration timestamp                  |

### Key Table
| Column       | Type       | Description                          |
| ------------ | ---------- | ------------------------------------ |
| id           | bigint     | Primary key                           |
| keyGenerated | varchar(6) | Unique short key for pastes           |
| isUsed       | boolean    | Whether the key has been used         |

---

## API Endpoints

### 1. Add a Paste

POST /api/pastes
Body:
{
"pasteData": "Your text here",
"customUrl": "optionalCustomUrl",
"userName": "optionalUsername",
"expirationDate": "optionalExpirationDate"
}
Response:
200 OK
{
"urlHash": "abc123"
}

### 2. Get a Paste

GET /api/pastes/{urlHash}
Response:
200 OK
Returns the text content as a file resource

### 3. Delete a Paste

DELETE /api/pastes/{urlHash}
Response:
200 OK
true // if deletion succeeded
false // if not found

---

## Installation

1. **Clone the repository**
```bash
git clone https://github.com/your-username/PastebinMiniDemo.git
cd PastebinMiniDemo
```

2. **Configure Database**

- Create a database in MySQL/PostgreSQL
- Update `application.properties` with DB credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/pastebin
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
```

3. **Run the application**
```bash
mvn spring-boot:run
```

4. **Access**
```
http://localhost:8080/api/pastes
```

## Usage
- Upload a paste using the API or Postman.
- Retrieve it with the generated URL hash.
- Optional: Provide custom URL or username.
- Pastes automatically expire after 2 years if not specified.

### Design Considerations
- System is read-heavy, 5:1 read-to-write ratio.
- Limits: Max paste size = 10MB, max custom URL = 6 characters.
- Storage estimation: ~36TB for 10 years at 1M pastes/day.
- Security: Unique, non-guessable URL hashes.
- Optional analytics: Access count, last access time.
- Scalable: Metadata and content storage can scale independently.

## License
This project is MIT Licensed.
