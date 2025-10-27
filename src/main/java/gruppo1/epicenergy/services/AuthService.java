package gruppo1.epicenergy.services;

import gruppo1.epicenergy.entities.Utente;
import gruppo1.epicenergy.exceptions.UnauthorizedException;
import gruppo1.epicenergy.payloads.auth.LoginDTO;
import gruppo1.epicenergy.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UtenteService service;
    @Autowired
    private JWTTools tools;
    @Autowired
    private PasswordEncoder bcrypt;

    public String checkCredentialsAndGenerateToken(LoginDTO body){
        Utente found = this.service.findByUsername(body.username());

        if(bcrypt.matches(body.password(), found.getPassword())) {
            return tools.createToken(found);
        } else {
            throw new UnauthorizedException("Credenziali errate!");
        }
    }
}
