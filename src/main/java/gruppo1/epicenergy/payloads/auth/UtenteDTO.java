package gruppo1.epicenergy.payloads.auth;

import gruppo1.epicenergy.enums.TipoUtente;
import jakarta.validation.constraints.NotBlank;

public record UtenteDTO (@NotBlank(message = "L'username è obbligatorio") String username,
                         @NotBlank(message = "L'username è obbligatorio") String email,
                         @NotBlank(message = "L'username è obbligatorio") String password,
                         @NotBlank(message = "L'username è obbligatorio") String nome,
                         @NotBlank(message = "L'username è obbligatorio") String cognome,
                         @NotBlank(message = "L'username è obbligatorio") String avatar,
                         TipoUtente tipo
                         ){
}
