package gruppo1.epicenergy.repositories;

import gruppo1.epicenergy.entities.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, Long> {
    Provincia findById(long provincia);
}
