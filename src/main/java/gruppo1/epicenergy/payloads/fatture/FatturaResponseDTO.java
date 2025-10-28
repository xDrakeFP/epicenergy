package gruppo1.epicenergy.payloads.fatture;

import java.time.LocalDate;
import java.util.UUID;

import gruppo1.epicenergy.entities.StatoFattura;

public record FatturaResponseDTO(UUID id, LocalDate data, Double importo, String numero, StatoFattura stato, UUID clienteId) {
}

