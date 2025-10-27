package gruppo1.epicenergy.services;

import gruppo1.epicenergy.entities.Cliente;
import gruppo1.epicenergy.payloads.clienti.ClienteDTO;
import gruppo1.epicenergy.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    // Crea
    public Cliente createCliente(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    // Ritorna il singolo cliente
    public Cliente getClienteById(UUID id){
        return clienteRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Cliente " + id + " non trovato"));
    }
     // Lista di clienti
    public Page<Cliente> findAll(int pageNumber, int pageSize, String sortBy){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        return clienteRepository.findAll(pageable);
    }

    // Aggiorna
    public Cliente updateCliente( UUID id, ClienteDTO updatedCliente){
        Cliente oldCliente = getClienteById(id);

        oldCliente.setRagioneSociale(updatedCliente.ragioneSociale());
        oldCliente.setPartitaIva(updatedCliente.partitaIva());
        oldCliente.setEmail(updatedCliente.email());
        oldCliente.setPec(updatedCliente.pec());
        oldCliente.setTelefono(updatedCliente.telefono());
        oldCliente.setEmailContatto(updatedCliente.emailContatto());
        oldCliente.setNomeContatto(updatedCliente.nomeContatto());
        oldCliente.setCognomeContatto(updatedCliente.cognomeContatto());

        Cliente newCliente = clienteRepository.save(oldCliente);

        return newCliente;
    }

    // Cancella cliente
    public void deleteCliente(UUID id){
        Cliente clienteTrovato = getClienteById(id);
        clienteRepository.delete(clienteTrovato);
    }
}
