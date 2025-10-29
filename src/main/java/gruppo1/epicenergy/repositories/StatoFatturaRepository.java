package gruppo1.epicenergy.repositories;

import gruppo1.epicenergy.entities.Fattura;
import gruppo1.epicenergy.entities.StatoFattura;
import gruppo1.epicenergy.payloads.fatture.StatoFatturaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StatoFatturaRepository extends JpaRepository <StatoFattura, UUID>{

    Optional<StatoFattura> findByStatoStr(String statoStr);
   // Page<StatoFatturaDTO> findByStatoStr(String statoStr, Pageable pageable);
}
