package gruppo1.epicenergy.controllers;

import gruppo1.epicenergy.entities.Cliente;
import gruppo1.epicenergy.entities.Fattura;
import gruppo1.epicenergy.entities.StatoFattura;
import gruppo1.epicenergy.payloads.fatture.*;
import gruppo1.epicenergy.services.FatturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/fatture")
public class FattureController {

    @Autowired
    private FatturaService service;


    @GetMapping
    public Page<Fattura> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy){
        return this.service.getAll(page, size, sortBy);
    }

    @GetMapping("/{id}")
    public Fattura findById(@PathVariable UUID id){
       return this.service.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Fattura create(@RequestBody FatturaDTO body){
        return this.service.createFattura(body);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable UUID id){
        this.service.delete(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Fattura update(@PathVariable UUID id, @RequestBody FatturaDTO body){
        return this.service.update(id,body);
    }
//
//    // Filtrate per cliente
//    @GetMapping("/by-client")
//    public Page<FatturaResponseDTO> findByClient(@RequestParam UUID clienteId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
//        return service.findByCliente(clienteId, PageRequest.of(page, size));
//    }
//
//    // Filtrate per stato(enum)
//    @GetMapping("/by-state")
//    public Page<StatoFatturaDTO> findByStato(@RequestParam UUID id, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam String sortBy) {
//        return service.findByStato(id, page, size, sortBy);
//    }
//
//    // Filtrate per data
//    @GetMapping("/by-date")
//    public Page<FatturaResponseDTO> findByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
//        return service.findByDate(data, PageRequest.of(page, size));
//    }
//
//    // Filtrate per anno
//    @GetMapping("/by-year")
//    public Page<FatturaResponseDTO> findByYear(@RequestParam int anno, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
//        return service.findByYear(anno, PageRequest.of(page, size));
//    }
//
//    // Filtrate per range di importi
//    @GetMapping("/by-range")
//    public Page<FatturaResponseDTO> findByRange(@RequestParam(required = false) Double minImporto, @RequestParam(required = false) Double maxImporto, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
//        return service.findByRange(minImporto, maxImporto, PageRequest.of(page, size));
//    }

    @GetMapping("/search")
    public ResponseEntity <Page<Fattura>> sortBy(@RequestParam(required = false) UUID cliente,
                                                 @RequestParam(required = false) UUID idStato,
                                                 @RequestParam(required = false) LocalDate data,
                                                 @RequestParam(required = false) Double min,
                                                 @RequestParam(required = false) Double max,
                                                 @RequestParam(defaultValue = "0") int pageNumber,
                                                 @RequestParam(defaultValue = "10") int pageSize,
                                                 @RequestParam(defaultValue = "nomeContatto") String sortBy,
                                                 @RequestParam(defaultValue = "asc") String direction){

        Page<Fattura> fatture = service.sortBy(cliente,idStato,data,min,max, pageNumber, pageSize, sortBy, direction);
        return ResponseEntity.ok(fatture);
    }
}
