package server.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
// Request DTO for creating or updating a payment.
public class PaymentRequest {
    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.0", message = "Total amount must be non-negative")
    private BigDecimal totalAmount;

    private String notes;
}
