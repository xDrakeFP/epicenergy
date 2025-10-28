package gruppo1.epicenergy.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "fatture")
public class Fattura {

    @Id
    @GeneratedValue
    private UUID id;

    private LocalDate data;

    private Double importo;

    private String numero;

    @Column(name = "cliente_id")
    private UUID clienteId;

    @ManyToOne
    @JoinColumn (name = "stato_fattura_id")
    private StatoFattura stato;


    public Fattura() {
    }

    public Fattura(UUID id, LocalDate data, Double importo, String numero, StatoFattura stato, UUID clienteId) {
        this.id = id;
        this.data = data;
        this.importo = importo;
        this.numero = numero;
        this.stato = stato;
        this.clienteId = clienteId;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Double getImporto() {
        return importo;
    }

    public void setImporto(Double importo) {
        this.importo = importo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public StatoFattura getStato() {
        return stato;
    }

    public void setStato(StatoFattura stato) {
        this.stato = stato;
    }

    public UUID getClienteId() {
        return clienteId;
    }

    public void setClienteId(UUID clienteId) {
        this.clienteId = clienteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fattura fattura = (Fattura) o;
        return Objects.equals(id, fattura.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Fattura{" +
                "id=" + id +
                ", data=" + data +
                ", importo=" + importo +
                ", numero='" + numero + '\'' +
                ", stato=" + stato +
                ", clienteId=" + clienteId +
                '}';
    }
}
