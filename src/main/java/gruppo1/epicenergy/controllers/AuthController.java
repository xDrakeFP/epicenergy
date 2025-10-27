package gruppo1.epicenergy.controllers;

import gruppo1.epicenergy.entities.Utente;
import gruppo1.epicenergy.payloads.auth.LoginDTO;
import gruppo1.epicenergy.payloads.auth.LoginResponseDTO;
import gruppo1.epicenergy.payloads.auth.UtenteDTO;
import gruppo1.epicenergy.services.AuthService;
import gruppo1.epicenergy.services.UtenteService;
import gruppo1.epicenergy.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO body){
        return new LoginResponseDTO(authService.checkCredentialsAndGenerateToken(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Utente register(@RequestBody @Validated UtenteDTO body, BindingResult validationResult){
        if(validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
        }
        return this.utenteService.registerUser(body);

    }
}
