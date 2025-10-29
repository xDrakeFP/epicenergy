package gruppo1.epicenergy.payloads.fatture;

import gruppo1.epicenergy.entities.Cliente;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;
import java.util.UUID;


public record FatturaDTO(@PastOrPresent @NotNull LocalDate data,
                         @NotNull Double importo,@NotBlank String numero,
                         @NotBlank String stato,@NotBlank UUID clienteId) {
}
