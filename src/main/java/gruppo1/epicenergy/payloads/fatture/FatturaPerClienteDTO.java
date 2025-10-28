package gruppo1.epicenergy.payloads.fatture;

import java.util.UUID;

public record FatturaPerClienteDTO(UUID clienteId, int page, int size) {
}
