package gruppo1.epicenergy.payloads;

import gruppo1.epicenergy.entities.ComuneProvincia;
import jakarta.validation.constraints.NotBlank;

public record IndirizzoDTO(
        @NotBlank(message = "Obbligatorio inserire una via")
        String via,
        @NotBlank(message = "Obbligatorio inserire un numero civico")
        int numeroCivico,
        @NotBlank(message = "Obbligatorio indicare la località")
        String localita,
        @NotBlank(message = "Obbligatorio indicare il comune")
        ComuneProvincia comuneProvincia
) {
}
