package gruppo1.epicenergy.payloads.fatture;

import java.time.LocalDate;

public record FatturaPerDataDTO(LocalDate data, int page, int size) {
}
