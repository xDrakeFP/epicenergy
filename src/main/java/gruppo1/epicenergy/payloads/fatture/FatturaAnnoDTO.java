package gruppo1.epicenergy.payloads.fatture;

import jakarta.validation.constraints.NotNull;

public record FatturaAnnoDTO(@NotNull int anno) {
}
