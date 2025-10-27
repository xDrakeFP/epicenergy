package gruppo1.epicenergy.payloads.auth;

import gruppo1.epicenergy.enums.TipoUtente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UtenteDTO (@NotBlank(message = "L'username è obbligatorio") String username,
                         @Email @NotBlank(message = "L'email è obbligatorio") String email,
                         @NotBlank(message = "La password è obbligatorio") String password,
                         @NotBlank(message = "Il nome è obbligatorio") String nome,
                         @NotBlank(message = "Il cognome è obbligatorio") String cognome,
                         @NotNull(message = "Il campo 'tipo' non può essere vuoto") TipoUtente tipo
                         ){
}
