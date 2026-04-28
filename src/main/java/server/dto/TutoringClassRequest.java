package server.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
// Request DTO for creating or updating a tutoring class.
public class TutoringClassRequest {
    @NotNull(message = "User ID is required")
    private Integer userId;

    @NotBlank(message = "Class name is required")
    private String name;

    private String subject;

    @DecimalMin(value = "0.0", message = "Hourly rate must be non-negative")
    private BigDecimal hourlyRate;
}
