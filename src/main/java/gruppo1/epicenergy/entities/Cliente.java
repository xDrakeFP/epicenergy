package gruppo1.epicenergy.entities;

import gruppo1.epicenergy.enums.TipoCliente;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data

@NoArgsConstructor
@Entity
public class Cliente {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    @Column(name = "ragione_sociale")
    private String ragioneSociale;
    @Column(name = "partita_iva")
    private String partitaIva;
    private String email;
    @Column(name = "data_inserimento")
    private LocalDate dataInserimento;
    @Column(name = "data_ultimo_contatto")
    private LocalDate dataUltimoContatto;
    @Column(name = "fatturato_annuale")
    private double fatturatoAnnuale;
    private String pec;
    private String telefono;
    @Column(name = "email_contatto")
    private String emailContatto;
    @Column(name = "nome_contatto")
    private String nomeContatto;
    @Column(name = "cognome_contatto")
    private String cognomeContatto;
    @Column(name = "telefono_contatto")
    private String telefonoContatto;
    @Column(name = "logo_aziendale")
    private String logoAziendale;
    @Enumerated(EnumType.STRING)
    private TipoCliente tipoCliente;
    @OneToOne
    @JoinColumn (name = "sede_legale_id")
    private Indirizzo sedeLegale;
    @OneToOne
    @JoinColumn (name = "sede_operativa_id")
    private Indirizzo sedeOperativa;

    public Cliente(String ragioneSociale, String partitaIva, String email, String pec, String telefono, String emailContatto, String nomeContatto,
                   String cognomeContatto, String telefonoContatto,TipoCliente tipoCliente, Indirizzo sedeLegale, Indirizzo sedeOperativa) {
        this.ragioneSociale = ragioneSociale;
        this.partitaIva = partitaIva;
        this.email = email;
        this.dataUltimoContatto = LocalDate.now();
        this.dataInserimento = LocalDate.now();
        this.fatturatoAnnuale = 0;
        this.pec = pec;
        this.telefono = telefono;
        this.emailContatto = emailContatto;
        this.nomeContatto = nomeContatto;
        this.cognomeContatto = cognomeContatto;
        this.telefonoContatto = telefonoContatto;
        this.logoAziendale = "https://ui-avatars.com/api/?name=" + ragioneSociale;
        this.sedeLegale = sedeLegale;
        this.tipoCliente = tipoCliente;
        this.sedeOperativa = sedeOperativa;
    }
}
