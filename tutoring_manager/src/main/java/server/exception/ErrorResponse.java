package server.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
// Standard JSON body returned when the API reports an error.
public class ErrorResponse {
    private String message;
    private int status;
    private long timestamp;
}
