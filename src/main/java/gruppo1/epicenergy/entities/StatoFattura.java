package gruppo1.epicenergy.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class StatoFattura {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String statoStr;


    public StatoFattura(String stato, Fattura statoFattura) {
        this.statoStr = stato;
    }
}
