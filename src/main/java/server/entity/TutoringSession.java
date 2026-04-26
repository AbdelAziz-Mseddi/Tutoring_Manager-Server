package server.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tutoring_session")
@Data
@NoArgsConstructor
@AllArgsConstructor
// TutoringSession entity storing scheduled session details.
public class TutoringSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Enrollment ID is required")
    @Column(nullable = false)
    private Integer enrollmentId;

    @Column
    private Integer paymentId;

    @NotNull(message = "Scheduled date/time is required")
    @Column(nullable = false)
    private LocalDateTime scheduledAt;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    @Column(nullable = false)
    private Integer durationMin;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id", insertable = false, updatable = false)
    private Enrollment enrollment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", insertable = false, updatable = false)
    private Payment payment;

    @PrePersist
    public void prePersist() {
        if (durationMin == null) {
            durationMin = 60;
        }
    }
}
