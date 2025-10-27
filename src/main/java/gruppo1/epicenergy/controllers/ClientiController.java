package gruppo1.epicenergy.controllers;

import gruppo1.epicenergy.payloads.clienti.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/client")
public class ClientiController {

    @GetMapping
    public String getAll (){
        return "TUTTI I CLIENTI";
    }

    @GetMapping("/{id}")
    public UUID findById(@PathVariable UUID id){
        return id;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String create(ClienteDTO body){
        return body.string();
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String delete(){
        return "CANCELLATO";
    }

    @PutMapping("/{id}")
    public String update(){
        return "MODIFICATO";
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
