package gruppo1.epicenergy.payloads.errors;

import java.time.LocalDateTime;

public record ErrorsDTO(
        String message,
        LocalDateTime date
) {
}