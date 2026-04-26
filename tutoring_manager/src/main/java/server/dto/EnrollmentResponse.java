package server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentResponse {
    private Integer id;
    private Integer studentId;
    private Integer classId;
    private LocalDate enrolledAt;
    private String status;
}
