# Student Use Cases

All endpoints require `Authorization: Bearer <token>`.  
A user can only access students they own (`student.user_id == authenticatedUserId`).

---

## UC-S1: List all students (paginated)

**`GET /students?page=0&size=20`**

### Request

```http
GET /students?page=0&size=20
Authorization: Bearer <token>
```

### What happens

1. `StudentController.getAllStudents()` reads `userId` from the `SecurityContext`
2. `StudentService` calls `StudentRepository.findAllByUser_Id(userId, pageable)` — returns only this user's students
3. Each `Student` entity is mapped to a `StudentResponse`

### Success response `200`

```json
{
  "content": [
    {
      "id": 1,
      "userId": 1,
      "firstName": "Bob",
      "lastName": "Dupont",
      "phone": "0612345678",
      "email": "bob@example.com",
      "createdAt": "2024-01-15T10:00:00"
    }
  ],
  "totalElements": 1,
  "totalPages": 1,
  "number": 0,
  "size": 20
}
```

### Error responses

| Status | Trigger |
|---|---|
| `401` | Missing or invalid JWT |

---

## UC-S2: Get a single student

**`GET /students/{id}`**

### Request

```http
GET /students/1
Authorization: Bearer <token>
```

### What happens

1. `StudentService.getStudentById(id, userId)` loads the student by id — if not found → `RuntimeException` (404)
2. Verifies `student.user.id == userId` — if mismatch → `ForbiddenException`
3. Returns the `StudentResponse`

### Success response `200`

```json
{
  "id": 1,
  "userId": 1,
  "firstName": "Bob",
  "lastName": "Dupont",
  "phone": "0612345678",
  "email": "bob@example.com",
  "createdAt": "2024-01-15T10:00:00"
}
```

### Error responses

| Status | Trigger |
|---|---|
| `401` | Missing or invalid JWT |
| `403` | Student exists but belongs to another user |
| `404` | No student with that id |

---

## UC-S3: Create a student

**`POST /students`**

### Request

```http
POST /students
Authorization: Bearer <token>
Content-Type: application/json

{
  "firstName": "Bob",
  "lastName": "Dupont",
  "phone": "0612345678",
  "email": "bob@example.com"
}
```

| Field | Required | Validation |
|---|---|---|
| `firstName` | yes | not blank |
| `lastName` | yes | not blank |
| `phone` | no | — |
| `email` | no | valid email format if provided |

### What happens

1. `StudentService.createStudent(request, userId)` loads the `User` entity by `userId`
2. Creates a new `Student` entity linked to that user
3. Saves via `StudentRepository`
4. Returns `201 Created`

### Success response `201`

```json
{
  "id": 2,
  "userId": 1,
  "firstName": "Bob",
  "lastName": "Dupont",
  "phone": "0612345678",
  "email": "bob@example.com",
  "createdAt": "2024-01-15T10:05:00"
}
```

### Error responses

| Status | Trigger |
|---|---|
| `400` | `firstName` or `lastName` blank, or invalid email format |
| `401` | Missing or invalid JWT |

---

## UC-S4: Update a student

**`PUT /students/{id}`**

### Request

```http
PUT /students/1
Authorization: Bearer <token>
Content-Type: application/json

{
  "firstName": "Bob",
  "lastName": "Dupont",
  "phone": "0699999999",
  "email": "bob.new@example.com"
}
```

Replaces all editable fields. Same field validations as UC-S3.

### What happens

1. Loads the student — 404 if not found
2. Verifies ownership — 403 if mismatch
3. Overwrites `firstName`, `lastName`, `phone`, `email`
4. Saves and returns the updated `StudentResponse`

### Success response `200`

Same shape as UC-S2.

### Error responses

| Status | Trigger |
|---|---|
| `400` | Validation failure |
| `401` | Missing or invalid JWT |
| `403` | Student belongs to another user |
| `404` | No student with that id |

---

## UC-S5: Delete a student

**`DELETE /students/{id}`**

### Request

```http
DELETE /students/1
Authorization: Bearer <token>
```

### What happens

1. Loads the student — 404 if not found
2. Verifies ownership — 403 if mismatch
3. Deletes via `StudentRepository.deleteById(id)`
4. Returns `204 No Content`

### Success response `204` (empty body)

### Error responses

| Status | Trigger |
|---|---|
| `401` | Missing or invalid JWT |
| `403` | Student belongs to another user |
| `404` | No student with that id |
