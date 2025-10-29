package gruppo1.epicenergy.repositories;

import gruppo1.epicenergy.entities.TipoUtente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TipoUtenteRepository extends JpaRepository<TipoUtente, UUID> {
    Optional<TipoUtente> findByTipo(String tipo);
}
