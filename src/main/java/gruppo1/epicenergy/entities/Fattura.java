package gruppo1.epicenergy.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "fatture")
public class Fattura {

    @Id
    @GeneratedValue
    private UUID id;

    private LocalDate data;

    private Double importo;

    private String numero;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn (name = "stato_fattura_id")
    private StatoFattura stato;


    public Fattura(UUID id, LocalDate data, Double importo, String numero, StatoFattura stato, Cliente cliente) {
        this.id = id;
        this.data = data;
        this.importo = importo;
        this.numero = numero;
        this.stato = stato;
        this.cliente = cliente;
    }




}
