package gruppo1.epicenergy.payloads.fatture;


import gruppo1.epicenergy.entities.StatoFattura;

public record FatturaPerStatoDTO(StatoFattura stato, int page, int size) {
}
