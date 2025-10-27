package gruppo1.epicenergy.services;

import gruppo1.epicenergy.entities.Utente;
import gruppo1.epicenergy.exceptions.AlreadyExistingException;
import gruppo1.epicenergy.exceptions.NotFoundException;
import gruppo1.epicenergy.payloads.auth.UtenteDTO;
import gruppo1.epicenergy.repositories.UtenteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtenteService {
    @Autowired
    private UtenteRepo repo;

    @Autowired
    private PasswordEncoder bcrypt;

    public Utente findById(UUID id){
        return this.repo.findById(id).orElseThrow(()->new NotFoundException("Nessun utente trovato con l'ID indicato"));
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
    }
}
