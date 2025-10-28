package gruppo1.epicenergy.repositories;

import gruppo1.epicenergy.entities.Cliente;
import gruppo1.epicenergy.entities.Fattura;
import gruppo1.epicenergy.entities.StatoFattura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface FatturaRepository extends JpaRepository<Fattura, UUID> {

    List<Fattura> findByClienteId(Cliente cliente);

    Page<Fattura> findByClienteId(Cliente cliente, Pageable pageable);

    List<Fattura> findByCliente(Cliente cliente, Pageable pageable);

    Page<Fattura> findByStato(StatoFattura stato, Pageable pageable);

    Page<Fattura> findByData(LocalDate data, Pageable pageable);

    Page<Fattura> findByDataBetween(LocalDate start, LocalDate end, Pageable pageable);

    Page<Fattura> findByImportoBetween(Double min, Double max, Pageable pageable);

    Page<Fattura> findByImportoGreaterThanEqual(Double min, Pageable pageable);

    Page<Fattura> findByImportoLessThanEqual(Double max, Pageable pageable);

}
