package gruppo1.epicenergy.repositories;

import gruppo1.epicenergy.entities.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID>, JpaSpecificationExecutor<Cliente> {
    Page<Cliente> findByNomeContattoContaining(String nome, Pageable pageable);

    Page<Cliente> findByDataInserimento(LocalDate dataInserimento, Pageable pageable);

    Page<Cliente> findByDataUltimoContatto(LocalDate dataUltimoContatto, Pageable pageable);

    Page<Cliente> findByFatturatoAnnualeGreaterThanEqual(double fatturato, Pageable pageable);

}
