package gruppo1.epicenergy.repositories;

import gruppo1.epicenergy.entities.Comune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ComuneRepository extends JpaRepository<Comune, UUID> {
}
