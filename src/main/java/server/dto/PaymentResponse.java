package server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
// Response DTO returned for payment data.
public class PaymentResponse {
    private Integer id;
    private Integer userId;
    private BigDecimal totalAmount;
    private LocalDateTime paidAt;
    private String notes;
}
