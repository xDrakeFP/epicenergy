package gruppo1.epicenergy.repositories;

import gruppo1.epicenergy.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, UUID> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<Utente> findByUsername(String username);
    Optional<Utente> findByEmail(String email);
}
