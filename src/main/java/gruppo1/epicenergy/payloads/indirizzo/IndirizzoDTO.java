package gruppo1.epicenergy.payloads.indirizzo;

import gruppo1.epicenergy.entities.Comune;
import jakarta.validation.constraints.NotBlank;

public record IndirizzoDTO(
        @NotBlank(message = "Obbligatorio inserire una via")
        String via,
        @NotBlank(message = "Obbligatorio inserire un numero civico")
        int numeroCivico,
        @NotBlank(message = "Obbligatorio indicare la località")
        String localita,
        @NotBlank(message = "Obbligatorio indicare il comune")
        Comune comune
) {
}
