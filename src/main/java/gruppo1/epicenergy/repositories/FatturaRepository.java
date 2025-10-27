package gruppo1.epicenergy.repositories;

import gruppo1.epicenergy.entities.Cliente;
import gruppo1.epicenergy.entities.Fattura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FatturaRepository extends JpaRepository<Fattura, UUID> {

    List<Fattura> findByClienteId(UUID clienteId);

    Page<Fattura> findByClienteId(UUID clienteId, Pageable pageable);

    List<Fattura> findByCliente(Cliente cliente);

}

