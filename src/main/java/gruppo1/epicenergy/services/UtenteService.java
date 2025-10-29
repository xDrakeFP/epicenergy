package gruppo1.epicenergy.services;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import gruppo1.epicenergy.entities.TipoUtente;
import gruppo1.epicenergy.entities.Utente;
import gruppo1.epicenergy.exceptions.AlreadyExistingException;
import gruppo1.epicenergy.exceptions.BadRequestException;
import gruppo1.epicenergy.exceptions.NotFoundException;
import gruppo1.epicenergy.payloads.utenti.NewUtenteDTO;
import gruppo1.epicenergy.repositories.TipoUtenteRepository;
import gruppo1.epicenergy.repositories.UtenteRepository;
import gruppo1.epicenergy.payloads.auth.UtenteDTO;
import gruppo1.epicenergy.tools.MailGun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UtenteService {
    private long MAX_SIZE = 5 * 1024 * 1024;
    private List<String> ALLOWED_TYPES = List.of("image/png", "image/jpeg");
    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private Cloudinary uploader;

    @Autowired
    private MailGun mailGun;

    @Autowired
    TipoUtenteRepository tipoUtenteRepository;

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

    public Utente findByEmail(String email) {
        return this.utenteRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Nessun utente trovato con l'email indicata"));
    }

    public Utente findByUsername(String username) {
        return this.utenteRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Nessun utente trovato con l'email indicata"));
    }

    public Utente registerUser(UtenteDTO body) {
        if (this.utenteRepository.existsByUsername(body.username()))
            throw new AlreadyExistingException("L'username indicato è già in uso");
        if (this.utenteRepository.existsByEmail(body.email()))
            throw new AlreadyExistingException("L'email indicata è già in uso");
        TipoUtente found = tipoUtenteRepository.findByTipo(body.tipo()).orElseThrow(() -> new NotFoundException("Tipo utente non trovato"));
        Utente utente = new Utente(body.username(), body.email(), bcrypt.encode(body.password()), body.nome(), body.cognome(), found);
        Utente utenteSalvato = this.utenteRepository.save(utente);
        mailGun.sendWelcomeEmailUtente(utenteSalvato);
        return utenteSalvato;
    }

    //TUTTI GLI UTENTI (PAGINATI)
    public Page<Utente> findAll(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 15) pageSize = 15;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        return utenteRepository.findAll(pageable);
    }

    //UPLOAD AVATAR
    public Utente uploadAvatar(UUID id, MultipartFile file) {
        //CONTROLLO SUL FILE
        if (file.isEmpty()) throw new BadRequestException("Empty File!");
        if (file.getSize() > MAX_SIZE) throw new BadRequestException("File troppo grande! (max 5mb)");
        if (!ALLOWED_TYPES.contains(file.getContentType()))
            throw new BadRequestException("Formato non accettato (png o jpeg)");
        Utente trovato = findById(id);

        try {
            Map result = uploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String urlImg = (String) result.get("url");
            trovato.setAvatar(urlImg);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return utenteRepository.save(trovato);
    }
}
