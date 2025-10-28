package gruppo1.epicenergy.payloads.fatture;

import gruppo1.epicenergy.entities.Cliente;
import gruppo1.epicenergy.entities.StatoFattura;

import java.time.LocalDate;
import java.util.UUID;



public record FatturaDTO(LocalDate data,
                         Double importo, String numero,
                         StatoFattura stato, Cliente cliente) {
}
