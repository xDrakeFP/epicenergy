package gruppo1.epicenergy.repositories;


import gruppo1.epicenergy.entities.Fattura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface FatturaRepository extends JpaRepository<Fattura, UUID> {

    Page<Fattura> findByCliente(UUID id, Pageable pageable);

    Page<Fattura> findByStato(UUID id, Pageable pageable);

    Page<Fattura> findByDataBetween(LocalDate start, LocalDate end, Pageable pageable);

    Page<Fattura> findByData(LocalDate data, Pageable pageable);

    Page<Fattura> findByImportoBetween(double min, double max, Pageable pageable);

}
