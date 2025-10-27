package gruppo1.epicenergy.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

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

    @Enumerated(EnumType.STRING)
    private StatoFattura stato;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;


    public Fattura() {
    }

    public Fattura(UUID id, LocalDate data, Double importo, String numero, StatoFattura stato, Cliente cliente) {
        this.id = id;
        this.data = data;
        this.importo = importo;
        this.numero = numero;
        this.stato = stato;
        this.cliente = cliente;
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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
                ", cliente=" + (cliente != null ? cliente.getId() : null) +
                '}';
    }
}
