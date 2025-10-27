package gruppo1.epicenergy.services;

import gruppo1.epicenergy.entities.Cliente;
import gruppo1.epicenergy.entities.Indirizzo;
import gruppo1.epicenergy.exceptions.NotFoundException;
import gruppo1.epicenergy.payloads.clienti.ClienteDTO;
import gruppo1.epicenergy.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Service
public class ClienteService {
    @Autowired
    private IndirizzoService indirizzoService;
    @Autowired
    private ClienteRepository clienteRepository;

    // Crea

    public Cliente createCliente(ClienteDTO cliente){
        Indirizzo sedeLegale = indirizzoService.findById(cliente.sedeLegaleId());
        Indirizzo sedeOperativa = indirizzoService.findById(cliente.sedeOperativaId());
        Cliente newCliente = new Cliente(cliente.ragioneSociale(), cliente.partitaIva(), cliente.email(), cliente.pec(), cliente.telefono(),
                cliente.emailContatto(), cliente.nomeContatto(), cliente.cognomeContatto(), cliente.telefonoContatto(),
                cliente.tipoCliente(), sedeLegale, sedeOperativa);
        return clienteRepository.save(newCliente);
    }

    // Ritorna il singolo cliente
    public Cliente getClienteById(UUID id){
        return clienteRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Cliente " + id + " non trovato"));
    }

    // Ritorna filtraggio per nome
    public Page<Cliente> findByNomeContattoStartingWith(String nome, int pageNumber, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return clienteRepository.findByNomeContattoStartingWith(nome, pageable);
    }
    // Ritorna filtraggio per data inserimento
    public Page<Cliente> findByDataInserimento(LocalDate dataInserimento,int pageNumber, int pageSize, String sortBy ){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return clienteRepository.findByDataInserimento(dataInserimento, pageable);
    }
    // Ritorna filtraggio per ultimo contatto
    public Page<Cliente> findByDataUltimoContatto(LocalDate dataUltimoContatto, int pageNumber, int pageSize, String sortBy){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return clienteRepository.findByDataUltimoContatto(dataUltimoContatto, pageable);
    }
    // Ritorna filtraggio by fatturato
    public Page <Cliente> findByFatturatoAnnuale(double fatturato, int pageNumber, int pageSize, String sortBy){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return clienteRepository.findByFatturatoAnnuale(fatturato, pageable);
    }

     // Lista di clienti
    public Page<Cliente> findAll(int pageNumber, int pageSize, String sortBy, String orderBy){
        Sort sort = Sort.by(sortBy).descending();
        if (Objects.equals(orderBy, "asc")){
             sort = Sort.by(sortBy).ascending();
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
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
