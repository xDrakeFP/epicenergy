package gruppo1.epicenergy.repositories;

import gruppo1.epicenergy.entities.Fattura;
import gruppo1.epicenergy.entities.StatoFattura;
import gruppo1.epicenergy.payloads.fatture.StatoFatturaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StatoFatturaRepository extends JpaRepository<StatoFattura, UUID> {

    Page<StatoFatturaDTO> findByStatoStr(String statoStr, Pageable pageable);
}
