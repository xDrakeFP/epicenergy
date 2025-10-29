package gruppo1.epicenergy.controllers;

import gruppo1.epicenergy.entities.Cliente;
import gruppo1.epicenergy.payloads.clienti.*;
import gruppo1.epicenergy.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/client")
public class ClientiController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public Page<Cliente> getAll(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "nomeContatto") String sortBy, @RequestParam(defaultValue = "asc") String orderBy) {
        return clienteService.findAll(pageNumber, pageSize, sortBy, orderBy);
    }

    @GetMapping("/{id}")
    public Cliente findById(@PathVariable UUID id) {
        return clienteService.getClienteById(id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente create(@RequestBody ClienteDTO body) {
        return clienteService.createCliente(body);
    }

    @PutMapping("/{id}")
    public Cliente update(@PathVariable UUID id, @RequestBody ClienteDTO body){
        return clienteService.updateCliente(id, body);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable UUID id) {
        clienteService.deleteCliente(id);
    }


    /* Ordinati in base al parametro che gli passiamo (Page)
    @GetMapping("/sort-by")
    public Page<Cliente> sortByParameter(@RequestParam int pageNumber, @RequestParam int pageSize, @RequestParam String sortBy, @RequestParam String orderBy) {
        return clienteService.findAll(pageNumber, pageSize, sortBy, orderBy);
    }

     Filtrati per nome
    @GetMapping("/search-by-name")
    public Page<Cliente> findByNomeContattoStartingWith(@RequestParam String nome, @RequestParam int pageNumber, @RequestParam int pageSize,
                                                        @RequestParam String sortBy) {
        return clienteService.findByNomeContattoStartingWith(nome, pageNumber, pageSize, sortBy);
    }

    // Filtrati per fatturato annuale
    @GetMapping("/sales")
    public Page<Cliente> findByFatturatoAnnuale(@RequestParam double fatturato, @RequestParam int pageNumber, @RequestParam int pageSize,
                                                @RequestParam String sortBy) {
        return clienteService.findByFatturatoAnnuale(fatturato, pageNumber, pageSize, sortBy);
    }

    // Filtrati per data di inserimento
    @GetMapping("/addition")
    public Page<Cliente> findByDataInserimento(@RequestParam LocalDate dataInserimento, @RequestParam int pageNumber, @RequestParam int pageSize,
                                               @RequestParam String sortBy) {
        return clienteService.findByDataInserimento(dataInserimento, pageNumber, pageSize, sortBy);
    }

    // Filtrati per data ultimo contatto
    @GetMapping("/last-contact")
    public Page<Cliente> findByDataUltimoContatto(@RequestParam LocalDate dataUltimoContatto, @RequestParam int pageNumber, @RequestParam int pageSize,
                                                  @RequestParam String sortBy) {
        return clienteService.findByDataUltimoContatto(dataUltimoContatto, pageNumber, pageSize, sortBy);
    } */

    @PatchMapping("/{id}/logo")
    public String uploadLogo(@RequestParam("logo") MultipartFile file) {
        return clienteService.uploadLogoAziendale(file);
    }


    @GetMapping("/search")
    public Page<Cliente> sortBy(@RequestParam(required = false) String nome,
                                @RequestParam(required = false) Double fatturato,
                                @RequestParam(required = false) LocalDate dataInserimento,
                                @RequestParam(required = false) LocalDate dataUltimoContatto,
                                @RequestParam(defaultValue = "0") int pageNumber,
                                @RequestParam(defaultValue = "10") int pageSize,
                                @RequestParam(defaultValue = "nomeContatto") String sortBy,
                                @RequestParam(defaultValue = "asc") String direction) {
        return clienteService.sortBy(
                nome,
                fatturato,
                dataInserimento,
                dataUltimoContatto,
                pageNumber,
                pageSize,
                sortBy,
                direction
        );
}
}

