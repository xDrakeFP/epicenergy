package gruppo1.epicenergy.repositories;

import gruppo1.epicenergy.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClienteRepo extends JpaRepository<Cliente, UUID> {
}
