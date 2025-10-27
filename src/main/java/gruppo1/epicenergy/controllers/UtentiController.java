package gruppo1.epicenergy.controllers;

import gruppo1.epicenergy.entities.Utente;
import gruppo1.epicenergy.payloads.clienti.ClienteDTO;
import gruppo1.epicenergy.payloads.utenti.NewUtenteDTO;
import gruppo1.epicenergy.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UtentiController {
    @Autowired
    private UtenteService utenteService;

    @GetMapping
    public Page<Utente> getAll(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "nome") String sortBy) {
        return utenteService.findAll(pageNumber, pageSize, sortBy);
    }

    @GetMapping("/{id}")
    public Utente findById(@PathVariable UUID id) {
        return utenteService.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        utenteService.findByIdAndDelete(id);
    }

    @PutMapping("/{id}")
    public Utente update(@PathVariable UUID id, @RequestBody NewUtenteDTO body) {
        return utenteService.findByIdAndUpdate(id, body);

    }
}
