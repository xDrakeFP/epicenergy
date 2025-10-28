package gruppo1.epicenergy.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Provincia {
    @Id
    @GeneratedValue
    private long id;
    private String sigla;
    @Column(name = "nome_provincia")
    private String nomeProvincia;
    private String regione;

    public Provincia(String sigla, String nomeProvincia, String regione){
        this.sigla=sigla;
        this.nomeProvincia=nomeProvincia;
        this.regione=regione;
    }
}
