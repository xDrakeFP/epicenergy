package gruppo1.epicenergy.payloads.clienti;

import gruppo1.epicenergy.entities.Indirizzo;
import gruppo1.epicenergy.enums.TipoCliente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.aspectj.weaver.ast.Not;

import java.util.UUID;

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
        String telefonoContatto,
        @NotBlank
        TipoCliente tipoCliente,
        @NotBlank
        UUID sedeLegaleId,
        @NotBlank
        UUID sedeOperativaId
){


}
