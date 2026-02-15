# 📋 Task Management REST API

A production-ready Task Management REST API built with Spring Boot, featuring JWT authentication, CRUD operations, and MySQL database integration.

## 🚀 Features

- ✅ User Authentication (Signup/Login with JWT)
- ✅ Secure password encryption (BCrypt)
- ✅ CRUD operations for tasks
- ✅ Pagination and sorting
- ✅ Filter tasks by status and priority
- ✅ Global exception handling
- ✅ RESTful API design

## 🛠️ Tech Stack

- **Backend:** Spring Boot 3.4.2
- **Security:** Spring Security + JWT
- **Database:** MySQL/MariaDB
- **ORM:** JPA/Hibernate
- **Build Tool:** Maven
- **Java:** 21

## 📡 API Endpoints

### Authentication
- `POST /api/auth/signup` - Register new user
- `POST /api/auth/login` - Login user

### Tasks (Requires JWT Token)
- `POST /api/tasks` - Create task
- `GET /api/tasks` - Get all tasks (with pagination)
- `GET /api/tasks/{id}` - Get task by ID
- `GET /api/tasks/status/{status}` - Filter by status
- `GET /api/tasks/priority/{priority}` - Filter by priority
- `PUT /api/tasks/{id}` - Update task
- `DELETE /api/tasks/{id}` - Delete task

## 🏗️ Project Structure
```
src/main/java/com/taskapi/taskmanager/
├── config/          # Security configuration
├── controller/      # REST API endpoints
├── dto/             # Data Transfer Objects
├── entity/          # Database models
├── exception/       # Error handling
├── repository/      # Database operations
├── security/        # JWT utilities
└── service/         # Business logic
```

## 🚀 Running Locally

1. Clone the repository
```bash
git clone https://github.com/Raguram17ag/taskmanager-api.git
cd taskmanager-api
```

2. Configure MySQL database in `application.properties`

3. Run the application
```bash
mvn spring-boot:run
```

4. API will be available at `http://localhost:8080`

## 📝 Example Usage

### Signup
```bash
curl -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john",
    "email": "john@example.com",
    "password": "password123",
    "fullName": "John Doe"
  }'
```

### Create Task
```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{
    "title": "Complete project",
    "description": "Finish the REST API",
    "status": "IN_PROGRESS",
    "priority": "HIGH"
  }'
```

## 👨‍💻 Author

**Raguram**  
GitHub: [@Raguram17ag](https://github.com/Raguram17ag)

## 📄 License

This project is open source and available under the MIT License.
