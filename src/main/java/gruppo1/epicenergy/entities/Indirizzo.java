package gruppo1.epicenergy.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Setter
public class Indirizzo {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String via;
    @Column(name = "numero_civico")
    private int numeroCivico;
    private String localita;
    @ManyToOne
    @JoinColumn(name = "comune_provincia_id")
    private ComuneProvincia comuneProvincia;

    public Indirizzo(String via, int numeroCivico, String localita, ComuneProvincia comuneProvincia){
        this.via=via;
        this.numeroCivico=numeroCivico;
        this.localita=localita;
        this.comuneProvincia = comuneProvincia;
    }
}
