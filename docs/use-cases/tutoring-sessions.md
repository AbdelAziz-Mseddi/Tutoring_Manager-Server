# Tutoring Session Use Cases

All endpoints require `Authorization: Bearer <token>`.  
Ownership is verified through the session's enrollment: `session → enrollment → student → user_id == authenticatedUserId`.

---

## UC-TS1: List all sessions (paginated)

**`GET /tutoring-sessions?page=0&size=20`**

### Request

```http
GET /tutoring-sessions?page=0&size=20
Authorization: Bearer <token>
```

### What happens

1. `TutoringSessionController.getAllSessions()` reads `userId` from the `SecurityContext`
2. `TutoringSessionService` calls `TutoringSessionRepository.findAllByEnrollment_Student_User_Id(userId, pageable)`
3. Each entity is mapped to a `TutoringSessionResponse`

### Success response `200`

```json
{
  "content": [
    {
      "id": 1,
      "enrollmentId": 1,
      "paymentId": null,
      "scheduledAt": "2024-02-01T14:00:00",
      "durationMin": 60,
      "notes": "First session",
      "createdAt": "2024-01-25T09:00:00"
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

## UC-TS2: Get a single session

**`GET /tutoring-sessions/{id}`**

### Request

```http
GET /tutoring-sessions/1
Authorization: Bearer <token>
```

### What happens

1. Loads the session — 404 if not found
2. Verifies ownership by traversing `session.enrollmentId → enrollment → student → user.id` — 403 if mismatch
3. Returns the `TutoringSessionResponse`

### Success response `200`

```json
{
  "id": 1,
  "enrollmentId": 1,
  "paymentId": null,
  "scheduledAt": "2024-02-01T14:00:00",
  "durationMin": 60,
  "notes": "First session",
  "createdAt": "2024-01-25T09:00:00"
}
```

### Error responses

| Status | Trigger |
|---|---|
| `401` | Missing or invalid JWT |
| `403` | Session's enrollment chain leads to another user |
| `404` | No session with that id |

---

## UC-TS3: Create a session

**`POST /tutoring-sessions`**

### Request

```http
POST /tutoring-sessions
Authorization: Bearer <token>
Content-Type: application/json

{
  "enrollmentId": 1,
  "paymentId": null,
  "scheduledAt": "2024-02-01T14:00:00",
  "durationMin": 90,
  "notes": "Focus on derivatives"
}
```

| Field | Required | Validation | Default |
|---|---|---|---|
| `enrollmentId` | yes | not null | — |
| `paymentId` | no | — | null |
| `scheduledAt` | yes | not null | — |
| `durationMin` | no | >= 1 if provided | `60` |
| `notes` | no | — | null |

### What happens

1. `TutoringSessionService.createSession(request, userId)` immediately verifies ownership of `enrollmentId` — 403 if the enrollment's student doesn't belong to this user
2. Creates a new `TutoringSession` entity
3. Saves and returns `201 Created`

### Success response `201`

Same shape as UC-TS2.

### Error responses

| Status | Trigger |
|---|---|
| `400` | `enrollmentId` or `scheduledAt` is null, or `durationMin` < 1 |
| `401` | Missing or invalid JWT |
| `403` | The enrollment does not belong to this user |
| `404` | `enrollmentId` does not exist |

---

## UC-TS4: Update a session

**`PUT /tutoring-sessions/{id}`**

### Request

```http
PUT /tutoring-sessions/1
Authorization: Bearer <token>
Content-Type: application/json

{
  "enrollmentId": 1,
  "paymentId": 2,
  "scheduledAt": "2024-02-01T15:00:00",
  "durationMin": 60,
  "notes": "Rescheduled"
}
```

Replaces all editable fields. Same field validations as UC-TS3.

### What happens

1. Loads the session — 404 if not found
2. Verifies ownership of the existing session — 403 if mismatch
3. Overwrites `enrollmentId`, `paymentId`, `scheduledAt`, `notes`, and `durationMin` if provided
4. Saves and returns the updated `TutoringSessionResponse`

### Success response `200`

Same shape as UC-TS2.

### Error responses

| Status | Trigger |
|---|---|
| `400` | Validation failure |
| `401` | Missing or invalid JWT |
| `403` | Session's enrollment chain leads to another user |
| `404` | No session with that id |

---

## UC-TS5: Delete a session

**`DELETE /tutoring-sessions/{id}`**

### Request

```http
DELETE /tutoring-sessions/1
Authorization: Bearer <token>
```

### What happens

1. Loads the session — 404 if not found
2. Verifies ownership — 403 if mismatch
3. Deletes via `TutoringSessionRepository.deleteById(id)`
4. Returns `204 No Content`

### Success response `204` (empty body)

### Error responses

| Status | Trigger |
|---|---|
| `401` | Missing or invalid JWT |
| `403` | Session's enrollment chain leads to another user |
| `404` | No session with that id |
