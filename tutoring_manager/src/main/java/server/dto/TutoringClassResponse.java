package server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TutoringClassResponse {
    private Integer id;
    private String name;
    private String subject;
    private BigDecimal hourlyRate;
    private LocalDateTime createdAt;
}
