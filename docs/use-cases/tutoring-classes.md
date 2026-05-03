# Tutoring Class Use Cases

All endpoints require `Authorization: Bearer <token>`.  
A user can only access classes they own (`tutoring_class.user_id == authenticatedUserId`).

---

## UC-TC1: List all classes (paginated)

**`GET /tutoring-classes?page=0&size=20`**

### Request

```http
GET /tutoring-classes?page=0&size=20
Authorization: Bearer <token>
```

### What happens

1. `TutoringClassController.getAllClasses()` reads `userId` from the `SecurityContext`
2. `TutoringClassService` calls `TutoringClassRepository.findAllByUser_Id(userId, pageable)`
3. Each entity is mapped to a `TutoringClassResponse`

### Success response `200`

```json
{
  "content": [
    {
      "id": 1,
      "userId": 1,
      "name": "Maths Terminale",
      "subject": "Mathematics",
      "hourlyRate": 30.00,
      "createdAt": "2024-01-10T09:00:00"
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

## UC-TC2: Get a single class

**`GET /tutoring-classes/{id}`**

### Request

```http
GET /tutoring-classes/1
Authorization: Bearer <token>
```

### What happens

1. Loads the class — 404 if not found
2. Verifies `tutoringClass.user.id == userId` — 403 if mismatch
3. Returns the `TutoringClassResponse`

### Success response `200`

```json
{
  "id": 1,
  "userId": 1,
  "name": "Maths Terminale",
  "subject": "Mathematics",
  "hourlyRate": 30.00,
  "createdAt": "2024-01-10T09:00:00"
}
```

### Error responses

| Status | Trigger |
|---|---|
| `401` | Missing or invalid JWT |
| `403` | Class belongs to another user |
| `404` | No class with that id |

---

## UC-TC3: Create a class

**`POST /tutoring-classes`**

### Request

```http
POST /tutoring-classes
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Maths Terminale",
  "subject": "Mathematics",
  "hourlyRate": 30.00
}
```

| Field | Required | Validation |
|---|---|---|
| `name` | yes | not blank |
| `subject` | no | — |
| `hourlyRate` | no | >= 0.0 if provided |

### What happens

1. `TutoringClassService.createClass(request, userId)` loads the `User` entity
2. Creates a new `TutoringClass` entity linked to that user
3. Saves and returns `201 Created`

### Success response `201`

Same shape as UC-TC2.

### Error responses

| Status | Trigger |
|---|---|
| `400` | `name` is blank or `hourlyRate` is negative |
| `401` | Missing or invalid JWT |

---

## UC-TC4: Update a class

**`PUT /tutoring-classes/{id}`**

### Request

```http
PUT /tutoring-classes/1
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Maths Terminale",
  "subject": "Mathematics",
  "hourlyRate": 35.00
}
```

Replaces all editable fields. Same field validations as UC-TC3.

### What happens

1. Loads the class — 404 if not found
2. Verifies ownership — 403 if mismatch
3. Overwrites `name`, `subject`, `hourlyRate`
4. Saves and returns the updated `TutoringClassResponse`

### Success response `200`

Same shape as UC-TC2.

### Error responses

| Status | Trigger |
|---|---|
| `400` | Validation failure |
| `401` | Missing or invalid JWT |
| `403` | Class belongs to another user |
| `404` | No class with that id |

---

## UC-TC5: Delete a class

**`DELETE /tutoring-classes/{id}`**

### Request

```http
DELETE /tutoring-classes/1
Authorization: Bearer <token>
```

### What happens

1. Loads the class — 404 if not found
2. Verifies ownership — 403 if mismatch
3. Deletes via `TutoringClassRepository.deleteById(id)`
4. Returns `204 No Content`

### Success response `204` (empty body)

### Error responses

| Status | Trigger |
|---|---|
| `401` | Missing or invalid JWT |
| `403` | Class belongs to another user |
| `404` | No class with that id |
