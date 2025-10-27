package gruppo1.epicenergy.services;


import gruppo1.epicenergy.entities.Utente;
import gruppo1.epicenergy.payloads.utenti.NewUtenteDTO;
import gruppo1.epicenergy.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtenteService {
    @Autowired
    private UtenteRepository utenteRepository;

    //NUOVO UTENTE
    public Utente newUtente(NewUtenteDTO body) {
        Utente newUtente = new Utente(body.username(), body.email(), body.password(), body.nome(), body.cognome());
        return utenteRepository.save(newUtente);
    }

    //CERCA UTENTE TRAMITE ID
    public Utente findById(UUID id) {
        return utenteRepository.findById(id).orElseThrow(() -> new RuntimeException());//TODO:cambiare con exceptions personalizzate
    }

    //MODIFICA UTENTE TODO: SOLO ADMIN E "UTENTE LOGGATO" POSSONON MODIFICARE
    public Utente findByIdAndUpdate(UUID id, NewUtenteDTO body) {
        Utente utenteTrovato = findById(id);
        utenteTrovato.setUsername(body.username());
        utenteTrovato.setEmail(body.email());
        utenteTrovato.setPassword(body.password());
        utenteTrovato.setNome(body.nome());
        utenteTrovato.setCognome(body.cognome());
        Utente utenteModificiato = utenteRepository.save(utenteTrovato);
        return utenteModificiato;
    }

    //ELIMINA UTENTE TODO: SOLO ADMIN E "UTENTE LOGGATO" POSSONON ELIMINARE
    public void findByIdAndDelete(UUID id) {
        Utente utenteDaEliminare = findById(id);
        utenteRepository.delete(utenteDaEliminare);
    }

    //TUTTI GLI UTENTI (PAGINATI)
    public Page<Utente> findAll(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 15) pageSize = 15;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        return utenteRepository.findAll(pageable);
    }

    //RICERCA TRAMITE EMAIL
    public Utente findByEmail(String email) {
        Utente utenteTrovato = utenteRepository.findByEmail(email);
        return utenteTrovato;
    }
}
