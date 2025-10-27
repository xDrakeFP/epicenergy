package gruppo1.epicenergy.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import gruppo1.epicenergy.enums.TipoUtente;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
@Table(name = "utenti")
@JsonIgnoreProperties({"password"})
public class Utente implements UserDetails {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String username;
    private String email;
    private String password;
    private String nome;
    private String cognome;
    private String avatar;
    @Enumerated(EnumType.STRING)
    private TipoUtente tipo;

    // COSTRUTTORE SENZA INDICARE IL TIPO

    public Utente(String username, String email, String password, String nome, String cognome) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.avatar = "https://ui-avatars.com/api/?name=" + nome + "+" + cognome; //AVATAR CON INIZIALI
        this.tipo = TipoUtente.USER; //DEFAULT USER NORMALE
    }

    // COSTRUTTORE INDICANDO IL TIPO

    public Utente(String username, String email, String password, String nome, String cognome, TipoUtente tipo) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.tipo = tipo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(tipo.name()));
    }

    @Override
    public String getUsername() {
        return username;
    }
}
