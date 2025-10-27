package gruppo1.epicenergy.controllers;

import gruppo1.epicenergy.payloads.clienti.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientiController {

    @GetMapping
    public String findAll (){
        return "TUTTI I CLIENTI";
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String createClient(ClienteDTO body){
        return body.string();
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteClient(){
        return "CANCELLATO";
    }

    @PutMapping("/{id}")
    public String updateClient(){
        return "MODIFICATO";
    }

    // Fatturato annuale
    @GetMapping("/sales")
    public String getBySales(ClientePerFatturatoDTO body){
        return body.string();
    }

    // Data di inserimento
    @GetMapping("/addition")
    public String getByAdditionDate(ClientePerInserimentoDTO body){
        return body.string();
    }

    // Data ultimo contatto
    @GetMapping("/last-contact")
    public String getByLastContactDate(ClientePerUltimoContattoDTO body){
        return body.string();
    }

    // Parte del nome
    @GetMapping("/names")
    public String getByName(ClientePerNomeDTO body){
        return  body.string();
    }

}
