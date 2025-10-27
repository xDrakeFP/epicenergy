package gruppo1.epicenergy.entities;

import gruppo1.epicenergy.enums.TipoCliente;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor

public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String ragioneSociale;
    private String partitaIva;
    private String email;
    private LocalDate dataInserimento;
    private LocalDate dataUltimoContatto;
    private double fatturatoAnnuale;
    private String pec;
    private String telefono;
    private String emailContatto;
    private String nomeContatto;
    private String cognomeContatto;
    private String telefonoContatto;
    private String logoAziendale;

    @Enumerated(EnumType.STRING)
    private TipoCliente tipoCliente;

   @OneToOne
    @JoinColumn (name = "sede_legale_id")
   private Indirizzo sedeLegale;

   @OneToOne
   @JoinColumn (name = "sede_operativa_id")
   private Indirizzo sedeOperativa;

    public Cliente(String ragioneSociale, String partitaIva, String email, LocalDate dataUltimoContatto, LocalDate dataInserimento, double fatturatoAnnuale, String pec, String telefono, String emailContatto, String nomeContatto, String cognomeContatto, String telefonoContatto, String logoAziendale, Indirizzo sedeLegale, TipoCliente tipoCliente, Indirizzo sedeOperativa) {
        this.ragioneSociale = ragioneSociale;
        this.partitaIva = partitaIva;
        this.email = email;
        this.dataUltimoContatto = dataUltimoContatto;
        this.dataInserimento = LocalDate.now();
        this.fatturatoAnnuale = 0;
        this.pec = pec;
        this.telefono = telefono;
        this.emailContatto = emailContatto;
        this.nomeContatto = nomeContatto;
        this.cognomeContatto = cognomeContatto;
        this.telefonoContatto = telefonoContatto;
        this.logoAziendale = logoAziendale;
        this.sedeLegale = sedeLegale;
        this.tipoCliente = tipoCliente;
        this.sedeOperativa = sedeOperativa;
    }
}
