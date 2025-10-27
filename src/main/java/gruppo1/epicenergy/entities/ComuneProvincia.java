package gruppo1.epicenergy.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "comuni_province")
public class ComuneProvincia {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String comune;
    private String provincia;

    public ComuneProvincia(String comune, String provincia){
        this.comune=comune;
        this.provincia=provincia;
    }
}
