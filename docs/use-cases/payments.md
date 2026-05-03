# Payment Use Cases

All endpoints require `Authorization: Bearer <token>`.  
A user can only access payments they own (`payment.user_id == authenticatedUserId`).

A payment is a standalone record of money received. It can be linked to one or more tutoring sessions by setting `session.paymentId` when creating/updating a session.

---

## UC-P1: List all payments (paginated)

**`GET /payments?page=0&size=20`**

### Request

```http
GET /payments?page=0&size=20
Authorization: Bearer <token>
```

### What happens

1. `PaymentController.getAllPayments()` reads `userId` from the `SecurityContext`
2. `PaymentService` calls `PaymentRepository.findAllByUser_Id(userId, pageable)`
3. Each entity is mapped to a `PaymentResponse`

### Success response `200`

```json
{
  "content": [
    {
      "id": 1,
      "userId": 1,
      "totalAmount": 90.00,
      "paidAt": "2024-02-01T15:00:00",
      "notes": "3 sessions at 30/hr"
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

## UC-P2: Get a single payment

**`GET /payments/{id}`**

### Request

```http
GET /payments/1
Authorization: Bearer <token>
```

### What happens

1. Loads the payment — 404 if not found
2. Verifies `payment.user.id == userId` — 403 if mismatch
3. Returns the `PaymentResponse`

### Success response `200`

```json
{
  "id": 1,
  "userId": 1,
  "totalAmount": 90.00,
  "paidAt": "2024-02-01T15:00:00",
  "notes": "3 sessions at 30/hr"
}
```

### Error responses

| Status | Trigger |
|---|---|
| `401` | Missing or invalid JWT |
| `403` | Payment belongs to another user |
| `404` | No payment with that id |

---

## UC-P3: Create a payment

**`POST /payments`**

### Request

```http
POST /payments
Authorization: Bearer <token>
Content-Type: application/json

{
  "totalAmount": 90.00,
  "notes": "3 sessions at 30/hr"
}
```

| Field | Required | Validation |
|---|---|---|
| `totalAmount` | yes | not null, >= 0.0 |
| `notes` | no | — |

### What happens

1. `PaymentService.createPayment(request, userId)` loads the `User` entity
2. Creates a new `Payment` entity linked to that user; `paidAt` is set automatically via `@CreationTimestamp`
3. Saves and returns `201 Created`

### Success response `201`

Same shape as UC-P2.

### Error responses

| Status | Trigger |
|---|---|
| `400` | `totalAmount` is null or negative |
| `401` | Missing or invalid JWT |

---

## UC-P4: Update a payment

**`PUT /payments/{id}`**

### Request

```http
PUT /payments/1
Authorization: Bearer <token>
Content-Type: application/json

{
  "totalAmount": 120.00,
  "notes": "4 sessions at 30/hr"
}
```

Replaces all editable fields. Same field validations as UC-P3.

> Note: `paidAt` is set at creation time and cannot be updated.

### What happens

1. Loads the payment — 404 if not found
2. Verifies ownership — 403 if mismatch
3. Overwrites `totalAmount` and `notes`
4. Saves and returns the updated `PaymentResponse`

### Success response `200`

Same shape as UC-P2.

### Error responses

| Status | Trigger |
|---|---|
| `400` | Validation failure |
| `401` | Missing or invalid JWT |
| `403` | Payment belongs to another user |
| `404` | No payment with that id |

---

## UC-P5: Delete a payment

**`DELETE /payments/{id}`**

### Request

```http
DELETE /payments/1
Authorization: Bearer <token>
```

### What happens

1. Loads the payment — 404 if not found
2. Verifies ownership — 403 if mismatch
3. Deletes via `PaymentRepository.deleteById(id)`
4. Returns `204 No Content`

> Note: deleting a payment does **not** automatically clear `paymentId` on linked sessions. Those sessions will then reference a non-existent payment id.

### Success response `204` (empty body)

### Error responses

| Status | Trigger |
|---|---|
| `401` | Missing or invalid JWT |
| `403` | Payment belongs to another user |
| `404` | No payment with that id |
