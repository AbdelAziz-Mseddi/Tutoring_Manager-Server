# Entity Package

## Purpose
Maps Java classes to database tables using JPA annotations.

## Decorators Used Here

- `@Entity`: marks a class as a JPA entity.
- `@Table`: sets the database table name.
- `@Id`: marks the primary key field.
- `@GeneratedValue`: configures automatic ID generation.
- `@Column`: defines column constraints and metadata.
- `@ManyToOne`: declares a many-to-one relationship.
- `@JoinColumn`: maps the relationship to a foreign key column.
- `@CreationTimestamp`: fills the timestamp automatically on insert.
- `@PrePersist`: runs just before inserting a new entity.
- `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`: Lombok helpers that generate boilerplate methods and constructors.
- `@NotBlank`, `@NotNull`, `@Email`, `@DecimalMin`, `@Min`: validation annotations for entity input rules.

## Classes
- `User`
- `Student`
- `TutoringClass`
- `Enrollment`
- `Payment`
- `TutoringSession`

## Class Details
- `User`: Represents the `"user"` table (quoted because `user` is a reserved word in PostgreSQL). Stores firstName, lastName, unique email, passwordHash, and createdAt. The root owner of all domain resources.
- `Student`: Represents the `student` table. Belongs to a `User` via `user_id`. Holds personal info and creation timestamp.
- `TutoringClass`: Represents `tutoring_class`. Belongs to a `User` via `user_id`. Holds class metadata and hourly rate.
- `Enrollment`: Represents `enrollment` linking `student_id` and `class_id`, including status and enrollment date.
- `Payment`: Represents `payment`. Belongs to a `User` via `user_id`. Stores amount, payment timestamp, and notes.
- `TutoringSession`: Represents `tutoring_session`, linking enrollment and optional payment with schedule and duration.

## Ownership Model
`User` is the root owner. `Student`, `TutoringClass`, and `Payment` have a direct `user_id` foreign key. `Enrollment` ownership is derived via its `student.user_id`. `TutoringSession` ownership is derived via its `enrollment.student.user_id`.

## Lifecycle Notes
- `Enrollment` has `@PrePersist` defaults for `enrolledAt` (today) and `status` ("active").
- `TutoringSession` has `@PrePersist` default for `durationMin` (60 minutes).
