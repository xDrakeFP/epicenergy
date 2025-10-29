package gruppo1.epicenergy.payloads.fatture;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record FatturaDataDTO(@NotNull @PastOrPresent LocalDate data) {
}
