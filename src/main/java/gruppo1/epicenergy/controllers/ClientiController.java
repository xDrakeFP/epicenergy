package gruppo1.epicenergy.controllers;

import gruppo1.epicenergy.payloads.clienti.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientiController {

    @GetMapping
    public String getAll (){
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

    // Ordinati per nome
    @GetMapping("/by-name")
    public String sortByName(){
        return "ORDINATI IN ORDINE ALFABETICO";
    }

    // Ordinati per fatturato annuale
    @GetMapping("/by-sales")
    public String sortBySales(){
        return "ORDINATI PER FATTURATO ANNUALE";
    }

    // Ordinati per data di inserimento
    @GetMapping("/by-addition")
    public String sortByAdditionDate(){
        return "ORDINATI PER DATA D'INSERIMENTO";
    }

    // Ordinati per data ultimo contatto
    @GetMapping("/by-last-contact")
    public String sortByLastContact(){
        return "ORDINATI PER DATA ULTIMO CONTATTO";
    }

    //Ordinati per provincia della sede legale
    @GetMapping("/by-province")
    public String sortByProvince(){
        return "ORDINATI PER PROVINCIA";
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
