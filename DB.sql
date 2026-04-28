-- ─────────────────────────────────────────
-- Tutoring Manager — Database Schema
-- ─────────────────────────────────────────

CREATE TABLE "user" (
  id            INT PRIMARY KEY AUTO_INCREMENT,
  first_name    VARCHAR(100) NOT NULL,
  last_name     VARCHAR(100) NOT NULL,
  email         VARCHAR(150) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE student (
  id           INT PRIMARY KEY AUTO_INCREMENT,
  user_id      INT NOT NULL,
  first_name   VARCHAR(100) NOT NULL,
  last_name    VARCHAR(100) NOT NULL,
  phone        VARCHAR(20),
  email        VARCHAR(150) UNIQUE,
  created_at   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE
);

CREATE TABLE tutoring_class (
  id           INT PRIMARY KEY AUTO_INCREMENT,
  user_id      INT NOT NULL,
  name         VARCHAR(150) NOT NULL,
  subject      VARCHAR(100),
  hourly_rate  DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
  created_at   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE
);

CREATE TABLE enrollment (
  id           INT PRIMARY KEY AUTO_INCREMENT,
  student_id   INT NOT NULL,
  class_id     INT NOT NULL,
  enrolled_at  DATE NOT NULL DEFAULT (CURRENT_DATE),
  status       VARCHAR(20) NOT NULL DEFAULT 'active'
                 CHECK (status IN ('active', 'paused', 'completed')),
  UNIQUE (student_id, class_id),
  FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
  FOREIGN KEY (class_id)   REFERENCES tutoring_class(id) ON DELETE CASCADE
);

CREATE TABLE payment (
  id           INT PRIMARY KEY AUTO_INCREMENT,
  user_id      INT NOT NULL,
  total_amount DECIMAL(10, 2) NOT NULL,
  paid_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  notes        TEXT,
  FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE
);

CREATE TABLE tutoring_session (
  id             INT PRIMARY KEY AUTO_INCREMENT,
  enrollment_id  INT NOT NULL,
  payment_id     INT DEFAULT NULL,
  scheduled_at   TIMESTAMP NOT NULL,
  duration_min   INT NOT NULL DEFAULT 60,
  notes          TEXT,
  created_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (enrollment_id) REFERENCES enrollment(id) ON DELETE CASCADE,
  FOREIGN KEY (payment_id)    REFERENCES payment(id)    ON DELETE SET NULL
);

-- ─────────────────────────────────────────
-- Indexes
-- ─────────────────────────────────────────

CREATE INDEX idx_student_user       ON student(user_id);
CREATE INDEX idx_class_user         ON tutoring_class(user_id);
CREATE INDEX idx_payment_user       ON payment(user_id);
CREATE INDEX idx_enrollment_student ON enrollment(student_id);
CREATE INDEX idx_enrollment_class   ON enrollment(class_id);
CREATE INDEX idx_session_enrollment ON tutoring_session(enrollment_id);
CREATE INDEX idx_session_payment    ON tutoring_session(payment_id);
CREATE INDEX idx_session_scheduled  ON tutoring_session(scheduled_at);
