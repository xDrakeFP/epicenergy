package gruppo1.epicenergy.payloads.fatture;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdatedFatturaDTO(@NotNull Double importo, @NotBlank String stato) {
}
