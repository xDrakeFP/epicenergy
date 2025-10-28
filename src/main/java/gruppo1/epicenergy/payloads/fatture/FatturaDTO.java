package gruppo1.epicenergy.payloads.fatture;

import gruppo1.epicenergy.enums.StatoFattura;

import java.time.LocalDate;
import java.util.UUID;



public record FatturaDTO(LocalDate data, Double importo, String numero, StatoFattura stato, UUID clienteId) {
}
