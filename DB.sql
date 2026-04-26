-- ─────────────────────────────────────────
-- Tutoring Manager — Database Schema
-- ─────────────────────────────────────────

CREATE TABLE student (
  id           INT PRIMARY KEY AUTO_INCREMENT,
  first_name   VARCHAR(100) NOT NULL,
  last_name    VARCHAR(100) NOT NULL,
  phone        VARCHAR(20),
  email        VARCHAR(150) UNIQUE,
  created_at   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE class (
  id           INT PRIMARY KEY AUTO_INCREMENT,
  name         VARCHAR(150) NOT NULL,
  subject      VARCHAR(100),
  hourly_rate  DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
  created_at   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
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
  FOREIGN KEY (class_id)   REFERENCES class(id)   ON DELETE CASCADE
);

CREATE TABLE payment (
  id           INT PRIMARY KEY AUTO_INCREMENT,
  total_amount DECIMAL(10, 2) NOT NULL,
  paid_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  notes        TEXT
);

CREATE TABLE session (
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

CREATE INDEX idx_enrollment_student ON enrollment(student_id);
CREATE INDEX idx_enrollment_class   ON enrollment(class_id);
CREATE INDEX idx_session_enrollment ON session(enrollment_id);
CREATE INDEX idx_session_payment    ON session(payment_id);
CREATE INDEX idx_session_scheduled  ON session(scheduled_at);