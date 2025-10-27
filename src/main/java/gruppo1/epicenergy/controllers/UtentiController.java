package gruppo1.epicenergy.controllers;

import gruppo1.epicenergy.payloads.clienti.ClienteDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UtentiController {

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
}
