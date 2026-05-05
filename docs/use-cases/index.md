# Use Cases

Each file documents every operation available for one domain, with request/response bodies, what each layer does, and all possible error outcomes.

| File | Domain |
|---|---|
| [auth.md](auth.md) | Register & login |
| [students.md](students.md) | Student CRUD |
| [tutoring-classes.md](tutoring-classes.md) | Tutoring class CRUD |
| [enrollments.md](enrollments.md) | Enrollment CRUD + status update |
| [tutoring-sessions.md](tutoring-sessions.md) | Session CRUD |
| [payments.md](payments.md) | Payment CRUD |

## Ownership model

Every resource is scoped to the authenticated user. The JWT token embeds a `userId`; every service method receives that userId and rejects access if the requested resource belongs to a different user.

```
User
 ├── Student (many)          → owned directly via student.user_id
 ├── TutoringClass (many)    → owned directly via tutoring_class.user_id
 ├── Payment (many)          → owned directly via payment.user_id
 └── Enrollment              → owned via enrollment → student → user
      └── TutoringSession    → owned via session → enrollment → student → user
```

## Authentication

All endpoints except `POST /auth/register` and `POST /auth/login` require the JWT token in every request header.

**Step 1** — get a token from login (or register):

```http
POST /auth/login
Content-Type: application/json

{ "email": "alice@example.com", "password": "secret123" }
```

Response:
```json
{ "token": "eyJhbGciOiJIUzI1NiJ9...", "userId": 1 }
```

**Step 2** — include that token in every subsequent request:

```http
GET /students
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

You only send the token — no userId, no email. The server extracts the userId from the token itself.

Missing or invalid token → `401 Authentication required`.

Tokens expire after **24 hours**. Login again to get a fresh one.
