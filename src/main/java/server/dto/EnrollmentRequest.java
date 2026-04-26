package server.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
// Request DTO for creating or updating an enrollment.
public class EnrollmentRequest {
    @NotNull(message = "Student ID is required")
    private Integer studentId;

    @NotNull(message = "Class ID is required")
    private Integer classId;

    private LocalDate enrolledAt;

    private String status;
}
