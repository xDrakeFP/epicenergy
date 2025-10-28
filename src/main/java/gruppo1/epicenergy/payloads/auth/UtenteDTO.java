package gruppo1.epicenergy.payloads.auth;

import gruppo1.epicenergy.entities.TipoUtente;
import jakarta.validation.constraints.*;

public record UtenteDTO (
        @NotBlank(message = "Username obbligatorio!")
        String username,
        @NotBlank(message = "Email obbligatoria!")
        @Email(message = "L'email deve essere inserita nel formato corretto")
        String email,
        @NotBlank(message = "Password obbligatoria!")
        @Size(min = 6, message = "La password deve avere almeno 6 caratteri")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{6,}$\n", message = "La password deve avere: \n •Almeno una lettera maiuscola \n •Lunghezza di almeno 6 caratteri")//PASSWORD CON ALMENO UNA LETTERA MAIUSCOLA E UN NUMERO
        String password,
        @NotBlank(message = "Nome obbligatorio")
        @Size(min = 2, max = 30, message = "Il nome deve essere compreso tra 2 e 30 caratteri")
        String nome,
        @NotBlank(message = "Cognome obbligatorio")
        @Size(min = 2, max = 30, message = "Il cognome deve essere compreso tra 2 e 30 caratteri")
        String cognome,
        @NotNull(message = "Il campo 'tipo' non può essere vuoto") TipoUtente tipo
){}
