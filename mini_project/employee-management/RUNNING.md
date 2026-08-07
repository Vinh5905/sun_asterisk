# Employee Management - Run Project

## Start MySQL

```bash
docker compose up -d
```

## Start Spring Boot

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
