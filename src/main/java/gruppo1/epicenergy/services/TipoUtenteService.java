package gruppo1.epicenergy.services;

import gruppo1.epicenergy.entities.TipoUtente;
import gruppo1.epicenergy.payloads.utenti.TipoUtenteDTO;
import gruppo1.epicenergy.repositories.TipoUtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoUtenteService {
    @Autowired
    private TipoUtenteRepository tipoUtenteRepository;

    public TipoUtente save(TipoUtenteDTO body) {
        TipoUtente nuovoTipoUtente = new TipoUtente(body.tipo());
        return tipoUtenteRepository.save(nuovoTipoUtente);

    }
}
