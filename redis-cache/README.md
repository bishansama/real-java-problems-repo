# Redis Cache + Session Spring Boot Demo

This app demonstrates using Redis for both application caching and HTTP session management.

## Features
- `@Cacheable` product lookup backed by Redis with TTLs
- Redis-backed HTTP sessions with simple increment/get/reset endpoints

## Requirements
- Java 11+
- Maven 3.6+
- Redis server running on `localhost:6379`

## Run
```bash
cd redis-cache
mvn spring-boot:run
```

## Endpoints
- `GET /products/{id}`: Returns product, cached in Redis (first call slow, subsequent calls fast)
- `POST /session/increment`: Increments a counter in the session
- `GET /session/value`: Retrieves current counter value and session id
- `POST /session/reset`: Resets counter to 0

## Notes
- Cache TTL defaults to 10 minutes; `products` cache TTL is 5 minutes (see `CacheConfig`).
- Ensure Redis is running: `redis-server` or Docker `docker run -p 6379:6379 redis:7`