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

All endpoints except `POST /auth/register` and `POST /auth/login` require:

```
Authorization: Bearer <token>
```

Missing or invalid token → `401 Authentication required`.
