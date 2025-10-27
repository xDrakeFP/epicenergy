package gruppo1.epicenergy.entities;

import gruppo1.epicenergy.enums.TipoCliente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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

   // @OneToOne
   // @JoinColumn (name = "sede_legale", nullable = false)
   // private Indirizzo sedeLegale;

  //  @OneToOne
 //   @JoinColumn (name = "sede_operativa", nullable = false)
  //  private Indirizzo sedeOperativa;

   // @OneToMany
  //  @JoinColumn  (name = "cliente_id", nullable = false)
   // private Fattura fattura;
}
