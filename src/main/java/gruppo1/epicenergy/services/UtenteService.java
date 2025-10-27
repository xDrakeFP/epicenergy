package gruppo1.epicenergy.services;


import gruppo1.epicenergy.entities.Utente;
import gruppo1.epicenergy.exceptions.AlreadyExistingException;
import gruppo1.epicenergy.exceptions.NotFoundException;
import gruppo1.epicenergy.payloads.utenti.NewUtenteDTO;
import gruppo1.epicenergy.repositories.UtenteRepository;
import gruppo1.epicenergy.payloads.auth.UtenteDTO;
import gruppo1.epicenergy.repositories.UtenteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtenteService {
    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    //CERCA UTENTE TRAMITE ID
    public Utente findById(UUID id) {
        return utenteRepository.findById(id).orElseThrow(() -> new NotFoundException("Utente con id " + id + " non trovato"));//TODO:cambiare con exceptions personalizzate
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

    public Utente findByEmail(String email){
        return this.repo.findByEmail(email).orElseThrow(()-> new NotFoundException("Nessun utente trovato con l'email indicata"));
    }

    public Utente findByUsername(String username){
        return this.repo.findByUsername(username).orElseThrow(()-> new NotFoundException("Nessun utente trovato con l'email indicata"));
    }

    public Utente registerUser(UtenteDTO body){
        if(this.repo.existsByUsername(body.username())) throw new AlreadyExistingException("L'username indicato è già in uso");
        if(this.repo.existsByEmail(body.email())) throw new AlreadyExistingException("L'email indicata è già in uso");
        Utente utente = new Utente(body.username(), body.email(), bcrypt.encode(body.password()),body.nome(), body.cognome(), body.tipo());
        return this.repo.save(utente);
    //TUTTI GLI UTENTI (PAGINATI)
    public Page<Utente> findAll(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 15) pageSize = 15;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        return utenteRepository.findAll(pageable);
    }
}
