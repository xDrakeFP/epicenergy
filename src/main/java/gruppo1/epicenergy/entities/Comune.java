package gruppo1.epicenergy.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comune {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    @Column(name = "codice_storico")
    private int codiceStorico;
    @Column(name = "progressivo_comune")
    private int progressivoComune;
    private String comune;
    @ManyToOne
    @JoinColumn(name = "provincia_id")
    private Provincia provincia;


    public Comune(int codiceStorico, int progressivoComune, String comune, Provincia provincia){
        this.codiceStorico=codiceStorico;
        this.progressivoComune=progressivoComune;
        this.comune=comune;
        this.provincia=provincia;
    }
}
