package gruppo1.epicenergy.payloads.clienti;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClienteDTO(
        @NotBlank
        String ragioneSociale,
        @NotBlank
        String partitaIva,
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Email
        String pec,
        @NotBlank
        String telefono,
        @NotBlank
        @Email
        String emailContatto,
        @NotBlank
        String nomeContatto,
        @NotBlank
        String cognomeContatto,
        @NotBlank
        String telefonoContatto
){


}
