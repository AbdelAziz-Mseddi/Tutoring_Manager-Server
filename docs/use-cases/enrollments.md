# Enrollment Use Cases

All endpoints require `Authorization: Bearer <token>`.  
Ownership is verified through the enrollment's student: `enrollment → student → user_id == authenticatedUserId`.

---

## UC-E1: List all enrollments (paginated)

**`GET /enrollments?page=0&size=20`**

### What happens

1. `EnrollmentController.getAllEnrollments()` reads `userId` from the `SecurityContext`
2. `EnrollmentService` calls `EnrollmentRepository.findAllByStudent_User_Id(userId, pageable)`
3. Each entity is mapped to an `EnrollmentResponse`

### Success response `200`

```json
{
  "content": [
    {
      "id": 1,
      "studentId": 2,
      "classId": 1,
      "enrolledAt": "2024-01-20",
      "status": "active"
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

## UC-E2: Get a single enrollment

**`GET /enrollments/{id}`**

### What happens

1. Loads the enrollment — 404 if not found
2. Verifies ownership via `enrollment.studentId → Student.user.id == userId` — 403 if mismatch
3. Returns the `EnrollmentResponse`

### Success response `200`

```json
{
  "id": 1,
  "studentId": 2,
  "classId": 1,
  "enrolledAt": "2024-01-20",
  "status": "active"
}
```

### Error responses

| Status | Trigger |
|---|---|
| `401` | Missing or invalid JWT |
| `403` | Enrollment's student belongs to another user |
| `404` | No enrollment with that id |

---

## UC-E3: Create an enrollment

**`POST /enrollments`**

### Request body

```json
{
  "studentId": 2,
  "classId": 1,
  "enrolledAt": "2024-01-20",
  "status": "active"
}
```

| Field | Required | Validation | Default |
|---|---|---|---|
| `studentId` | yes | not null | — |
| `classId` | yes | not null | — |
| `enrolledAt` | no | — | today's date |
| `status` | no | — | `"active"` |

### What happens

1. `EnrollmentService.createEnrollment(request, userId)` immediately verifies that `studentId` belongs to the authenticated user — 403 if not
2. Creates a new `Enrollment` entity with the provided fields (or defaults)
3. Saves and returns `201 Created`

### Success response `201`

Same shape as UC-E2.

### Error responses

| Status | Trigger |
|---|---|
| `400` | `studentId` or `classId` is null |
| `401` | Missing or invalid JWT |
| `403` | The student with `studentId` belongs to another user |
| `404` | `studentId` does not exist |

---

## UC-E4: Update an enrollment

**`PUT /enrollments/{id}`**

Replaces all editable fields. Same request body and validations as UC-E3.

### What happens

1. Loads the enrollment — 404 if not found
2. Verifies ownership of the existing enrollment — 403 if mismatch
3. Overwrites `studentId`, `classId`, and optionally `enrolledAt` and `status` if provided
4. Saves and returns the updated `EnrollmentResponse`

### Success response `200`

Same shape as UC-E2.

### Error responses

| Status | Trigger |
|---|---|
| `400` | Validation failure |
| `401` | Missing or invalid JWT |
| `403` | Enrollment's student belongs to another user |
| `404` | No enrollment with that id |

---

## UC-E5: Update enrollment status only

**`PATCH /enrollments/{id}/status?status=inactive`**

A lightweight endpoint to change just the status field without sending the full body.

### Query parameter

| Param | Required | Example values |
|---|---|---|
| `status` | yes | `active`, `inactive`, `completed`, `cancelled` |

### What happens

1. Loads the enrollment — 404 if not found
2. Verifies ownership — 403 if mismatch
3. Overwrites `status` with the provided value
4. Saves and returns the updated `EnrollmentResponse`

### Success response `200`

```json
{
  "id": 1,
  "studentId": 2,
  "classId": 1,
  "enrolledAt": "2024-01-20",
  "status": "inactive"
}
```

### Error responses

| Status | Trigger |
|---|---|
| `401` | Missing or invalid JWT |
| `403` | Enrollment's student belongs to another user |
| `404` | No enrollment with that id |

---

## UC-E6: Delete an enrollment

**`DELETE /enrollments/{id}`**

### What happens

1. Loads the enrollment — 404 if not found
2. Verifies ownership — 403 if mismatch
3. Deletes via `EnrollmentRepository.deleteById(id)`
4. Returns `204 No Content`

### Success response `204` (empty body)

### Error responses

| Status | Trigger |
|---|---|
| `401` | Missing or invalid JWT |
| `403` | Enrollment's student belongs to another user |
| `404` | No enrollment with that id |
