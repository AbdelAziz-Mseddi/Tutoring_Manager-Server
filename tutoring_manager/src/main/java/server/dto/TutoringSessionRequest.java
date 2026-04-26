package server.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TutoringSessionRequest {
    @NotNull(message = "Enrollment ID is required")
    private Integer enrollmentId;

    private Integer paymentId;

    @NotNull(message = "Scheduled date/time is required")
    private LocalDateTime scheduledAt;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    private Integer durationMin;

    private String notes;
}
