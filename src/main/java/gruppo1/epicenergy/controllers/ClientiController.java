package gruppo1.epicenergy.controllers;

import gruppo1.epicenergy.entities.Cliente;
import gruppo1.epicenergy.payloads.clienti.*;
import gruppo1.epicenergy.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
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

    @PatchMapping("/{id}/logo")
    public String uploadLogo(@RequestParam("logo") MultipartFile file) {
        return clienteService.uploadLogoAziendale(file);
    }



    @GetMapping("/search")
    public ResponseEntity <Page<Cliente>> sortBy(@RequestParam(required = false) String nome,
                                                 @RequestParam(required = false) Double fatturato,
                                                 @RequestParam(required = false) LocalDate dataInserimento,
                                                 @RequestParam(required = false) LocalDate dataUltimoContatto,
                                                 @RequestParam(required = false) UUID provincia,
                                                 @RequestParam(defaultValue = "0") int pageNumber,
                                                 @RequestParam(defaultValue = "10") int pageSize,
                                                 @RequestParam(defaultValue = "nomeContatto") String sortBy,
                                                 @RequestParam(defaultValue = "asc") String direction){

        Page<Cliente> cliente = clienteService.sortBy(nome, fatturato, dataInserimento, dataUltimoContatto, pageNumber, pageSize, sortBy, direction);
        return ResponseEntity.ok(cliente);
}
}

