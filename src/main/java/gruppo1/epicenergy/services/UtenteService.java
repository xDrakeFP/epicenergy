package gruppo1.epicenergy.services;

import gruppo1.epicenergy.entities.Utente;
import gruppo1.epicenergy.exceptions.NotFoundException;
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
}
