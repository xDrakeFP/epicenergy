package gruppo1.epicenergy.payloads.indirizzo;

import gruppo1.epicenergy.entities.Comune;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record IndirizzoDTO(
        @NotBlank(message = "Obbligatorio inserire una via")
        String via,
        @NotNull(message = "Obbligatorio inserire un numero civico")
        int numeroCivico,
        @NotBlank(message = "Obbligatorio indicare la località")
        String localita,
        @NotBlank(message = "Obbligatorio indicare il comune")
        String comune
) {
}
