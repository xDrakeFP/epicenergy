package gruppo1.epicenergy.controllers;

import gruppo1.epicenergy.entities.Cliente;
import gruppo1.epicenergy.payloads.clienti.*;
import gruppo1.epicenergy.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/client")
public class ClientiController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public String getAll (){
        return "TUTTI I CLIENTI";
    }

    @GetMapping("/{id}")
    public Cliente findById(@PathVariable UUID id){
        return clienteService.getClienteById(id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente create(@RequestBody ClienteDTO body){
        return clienteService.createCliente(body);
    }

    @PutMapping("/{id}")
    public Cliente update(@PathVariable UUID id, @RequestBody ClienteDTO body){


        return clienteService.updateCliente(id, body);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id){
        clienteService.deleteCliente(id);
    }


    // Ordinati in base al parametro che gli passiamo (Page)
    @GetMapping("/sort-by")
    public String sortByParameter(){
        return "ORDINATI IN BASE AL PARAMETRO";
    }

    // Filtrati per fatturato annuale
    @GetMapping("/sales")
    public String findBySales(ClientePerFatturatoDTO body){
        return body.string();
    }

    // Filtrati per data di inserimento
    @GetMapping("/addition")
    public String findByAdditionDate(ClientePerInserimentoDTO body){
        return body.string();
    }

    // Filtrati per data ultimo contatto
    @GetMapping("/last-contact")
    public String findByLastContactDate(ClientePerUltimoContattoDTO body){
        return body.string();
    }

    // Filtrati per parte del nome
    @GetMapping("/part-of-name")
    public String findByPartOfName(ClientePerNomeDTO body){
        return  body.string();
    }

}
