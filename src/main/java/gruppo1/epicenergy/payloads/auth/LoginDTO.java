package gruppo1.epicenergy.payloads.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(@NotBlank (message = "L'username non può essere vuoto") String username, @NotBlank(message = "la password non può essere vuota") String password) {
}
