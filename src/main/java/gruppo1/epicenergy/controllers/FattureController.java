package gruppo1.epicenergy.controllers;

import gruppo1.epicenergy.payloads.fatture.*;
import gruppo1.epicenergy.services.FatturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/fatture")
public class FattureController {

    private final FatturaService service;

    @Autowired
    public FattureController(FatturaService service) {
        this.service = service;
    }

    @GetMapping
    public Page<FatturaResponseDTO> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Pageable p = PageRequest.of(page, size);
        return service.getAll(p);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FatturaResponseDTO> findById(@PathVariable UUID id){
        try {
            return ResponseEntity.ok(service.getById(id));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<FatturaResponseDTO> create(@RequestBody FatturaDTO body){
        FatturaResponseDTO created = service.create(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<FatturaResponseDTO> update(@PathVariable UUID id, @RequestBody FatturaDTO body){
        try {
            return ResponseEntity.ok(service.update(id, body));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // Filtrate per cliente
    @GetMapping("/by-client")
    public Page<FatturaResponseDTO> findByClient(@RequestParam UUID clienteId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return service.findByCliente(clienteId, PageRequest.of(page, size));
    }

    // Filtrate per stato(enum)
    @GetMapping("/by-state")
    public Page<FatturaResponseDTO> findByState(@RequestParam String stato, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return service.findByStato(stato, PageRequest.of(page, size));
    }

    // Filtrate per data
    @GetMapping("/by-date")
    public Page<FatturaResponseDTO> findByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return service.findByDate(data, PageRequest.of(page, size));
    }

    // Filtrate per anno
    @GetMapping("/by-year")
    public Page<FatturaResponseDTO> findByYear(@RequestParam int anno, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return service.findByYear(anno, PageRequest.of(page, size));
    }

    // Filtrate per range di importi
    @GetMapping("/by-range")
    public Page<FatturaResponseDTO> findByRange(@RequestParam(required = false) Double minImporto, @RequestParam(required = false) Double maxImporto, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return service.findByRange(minImporto, maxImporto, PageRequest.of(page, size));
    }

}
