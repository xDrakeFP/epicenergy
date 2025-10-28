package gruppo1.epicenergy.payloads.fatture;

import gruppo1.epicenergy.entities.StatoFattura;

import java.time.LocalDate;
import java.util.UUID;



public record FatturaResponseDTO(UUID id, LocalDate data, Double importo, String numero, StatoFattura stato, UUID clienteId) {
}

