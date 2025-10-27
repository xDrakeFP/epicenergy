package gruppo1.epicenergy.services;

import gruppo1.epicenergy.entities.Indirizzo;
import gruppo1.epicenergy.payloads.IndirizzoDTO;
import gruppo1.epicenergy.repositories.IndirizzoRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IndirizzoService {
    @Autowired
    private IndirizzoRepository indirizzoRepository;

    public Indirizzo saveIndirizzo(IndirizzoDTO payload){
        Indirizzo newIndirizzo = new Indirizzo(payload.via(), payload.numeroCivico(), payload.localita(), payload.comuneProvincia());
        return indirizzoRepository.save(newIndirizzo);
    }

    public Page<Indirizzo> findAll(int pageN, int pageSize){
        if (pageSize>50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageN, pageSize);
        return this.indirizzoRepository.findAll(pageable);
    }

    //TODO: gestire eccezione not found exception
    public Indirizzo findById(UUID id){
        return indirizzoRepository.findById(id).orElseThrow();
    }

    public Indirizzo findByIdAndUpdate(UUID id, IndirizzoDTO payload){
        Indirizzo found = findById(id);
        found.setVia(payload.via());
        found.setNumeroCivico(payload.numeroCivico());
        found.setLocalita(payload.localita());
        found.setComuneProvincia(payload.comuneProvincia());
        return indirizzoRepository.save(found);
    }

    public void findAndDelete(UUID id){
        Indirizzo found = findById(id);
        indirizzoRepository.delete(found);
    }
}
