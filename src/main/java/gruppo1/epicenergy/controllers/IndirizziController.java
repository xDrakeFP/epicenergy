package gruppo1.epicenergy.controllers;

import gruppo1.epicenergy.entities.Indirizzo;
import gruppo1.epicenergy.payloads.IndirizzoDTO;
import gruppo1.epicenergy.payloads.clienti.ClienteDTO;
import gruppo1.epicenergy.services.IndirizzoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/address")
public class IndirizziController {
    @Autowired
    private IndirizzoService indirizzoService;

    //TODO: GESTIRE ERRORI VALIDAZIONE
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Indirizzo create(@RequestBody @Validated IndirizzoDTO body, BindingResult validationResult){
        if(validationResult.hasErrors()) throw new RuntimeException();
        return indirizzoService.saveIndirizzo(body);
    }

    @GetMapping
    public Page<Indirizzo> getAll (@RequestParam(defaultValue = "0")int pageN, @RequestParam(defaultValue = "10") int pageSize){
        return indirizzoService.findAll(pageN, pageSize);
    }

    @GetMapping("/{id}")
    public Indirizzo findById(@PathVariable UUID id){
        return indirizzoService.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id){
        indirizzoService.findAndDelete(id);
    }

    //TODO: GESTIRE ERRORI VALIDAZIONE
    @PutMapping("/{id}")
    public Indirizzo update(@PathVariable UUID id, @RequestBody @Validated IndirizzoDTO body, BindingResult validationResult){
        if(validationResult.hasErrors()) throw new RuntimeException();
        return indirizzoService.findByIdAndUpdate(id, body);
    }
}
