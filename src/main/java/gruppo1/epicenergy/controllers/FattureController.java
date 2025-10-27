package gruppo1.epicenergy.controllers;


import gruppo1.epicenergy.payloads.fatture.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/invoice")
public class FattureController {
    @GetMapping
    public String getAll (){
        return "TUTTI LE FATTURE";
    }

    @GetMapping("/{id}")
    public UUID findById(@PathVariable UUID id){
        return id;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String create(FatturaDTO body){
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

    // Filtrate per cliente
    @GetMapping("/by-client")
    public String findByClient(FatturaPerClienteDTO body){
        return body.string();
    }

    // Filtrate per stato(enum)
    @GetMapping("/by-state")
    public String findByState(FatturaPerStatoDTO body) {
        return body.string();
    }

    // Filtrate per data
    @GetMapping("/by-date")
    public String findByDate(FatturaPerStatoDTO body){
        return body.string();
    }

    // Filtrate per anno
    @GetMapping("/by-year")
    public String findByYear(FatturaPerAnnoDTO body){
        return body.string();
    }

    // Filtrate per range di importi
    @GetMapping("/by-range")
    public String findByRange(FatturaPerRangeImporti body){
        return body.string();
    }

}
