package server.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tutoring_class")
@Data
@NoArgsConstructor
@AllArgsConstructor
// TutoringClass entity mapped to the tutoring_class table.
public class TutoringClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "User is required")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank(message = "Class name is required")
    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 100)
    private String subject;

    @DecimalMin(value = "0.0", message = "Hourly rate must be non-negative")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal hourlyRate;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
