# Auth Use Cases

These are the only public endpoints — no JWT required.

---

## UC-A1: Register a new account

**`POST /auth/register`**

### Request body

```json
{
  "firstName": "Alice",
  "lastName": "Martin",
  "email": "alice@example.com",
  "password": "secret123"
}
```

| Field | Required | Validation |
|---|---|---|
| `firstName` | yes | not blank |
| `lastName` | yes | not blank |
| `email` | yes | valid email format, not blank |
| `password` | yes | not blank |

### What happens

1. `AuthController.register()` receives the validated `RegisterRequest`
2. `AuthService` checks `UserRepository` — if the email already exists → `ConflictException`
3. Password is hashed with BCrypt
4. A new `User` row is inserted
5. `JwtUtil` generates a JWT containing the new `userId` as the subject
6. Returns `201 Created`

### Success response `201`

```json
{
  "token": "<jwt>",
  "userId": 1
}
```

### Error responses

| Status | Trigger |
|---|---|
| `400` | Any required field is blank or email is malformed |
| `409` | Email already registered |

---

## UC-A2: Login

**`POST /auth/login`**

### Request body

```json
{
  "email": "alice@example.com",
  "password": "secret123"
}
```

| Field | Required | Validation |
|---|---|---|
| `email` | yes | valid email format, not blank |
| `password` | yes | not blank |

### What happens

1. `AuthController.login()` receives the validated `LoginRequest`
2. `AuthService` looks up the user by email — if not found → `UnauthorizedException`
3. BCrypt compares the submitted password against the stored hash — if mismatch → `UnauthorizedException`
4. `JwtUtil` generates a fresh JWT for the user
5. Returns `200 OK`

### Success response `200`

```json
{
  "token": "<jwt>",
  "userId": 1
}
```

### Error responses

| Status | Trigger |
|---|---|
| `400` | Any required field is blank or email is malformed |
| `401` | Email not found or password does not match (same message to prevent enumeration) |

---

## Token lifetime

Tokens expire after **24 hours** (`jwt.expiration=86400000` ms in `application.properties`).

You do not need to login before each request. The intended flow is:

1. Login once → receive a token
2. Store the token and reuse it for all requests
3. After 24 hours, login again to get a fresh token

Sending an expired token returns `401 Authentication required`.
