package gruppo1.epicenergy.repositories;

import gruppo1.epicenergy.entities.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    Page<Cliente> findByNomeContattoStartingWith(String nome, Pageable pageable);
    Page<Cliente> findByDataInserimento(LocalDate dataInserimento, Pageable pageable);
    Page<Cliente> findByDataUltimoContatto(LocalDate dataUltimoContatto, Pageable pageable);
    Page<Cliente> findByFatturatoAnnuale(double fatturato, Pageable pageable);

}
