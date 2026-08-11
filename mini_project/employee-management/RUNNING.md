# Employee Management - Run Project

## Start MySQL

Docker Compose uses `.env` by default. The committed `.env` has the same local values as `.env.dev`.

```bash
docker compose up -d
```

Run Docker Compose with production env values:

```bash
docker compose --env-file .env.prod up -d
```

## Start Spring Boot

The default profile is `dev`, which uses the MySQL container from `docker-compose.yml`.

```bash
./mvnw spring-boot:run
```

App runs at:

```text
http://localhost:8080
```

If port 8080 is busy:

```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments=--server.port=8081
```

Run with the production profile:

```bash
export $(grep -v '^#' .env.prod | xargs)
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

## Test APIs

```bash
curl http://localhost:8080/api/employees
```

Create employee:

```bash
curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -d '{"name":"Le Van Cuong","email":"cuong.le@example.com","departmentId":1}'
```
