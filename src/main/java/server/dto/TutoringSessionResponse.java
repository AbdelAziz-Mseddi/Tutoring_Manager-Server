package server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
// Response DTO returned for tutoring session data.
public class TutoringSessionResponse {
    private Integer id;
    private Integer enrollmentId;
    private Integer paymentId;
    private LocalDateTime scheduledAt;
    private Integer durationMin;
    private String notes;
    private LocalDateTime createdAt;
}
