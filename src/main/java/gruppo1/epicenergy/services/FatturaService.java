package gruppo1.epicenergy.services;

import gruppo1.epicenergy.entities.Cliente;
import gruppo1.epicenergy.entities.Fattura;

import gruppo1.epicenergy.entities.StatoFattura;
import gruppo1.epicenergy.exceptions.BadRequestException;
import gruppo1.epicenergy.exceptions.NotFoundException;
import gruppo1.epicenergy.payloads.fatture.*;
import gruppo1.epicenergy.repositories.FatturaRepository;
import gruppo1.epicenergy.repositories.StatoFatturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Year;
import java.util.UUID;

@Service
@Transactional
public class FatturaService {

    @Autowired
    private FatturaRepository fatturaRepository;

    @Autowired
    private StatoFatturaRepository statoFatturaRepository;

    @Autowired
    private ClienteService clienteService;

    public Fattura createFattura(FatturaDTO body){
        StatoFattura newStato = this.findStatoByString(body.stato());
        Cliente found = this.clienteService.getClienteById(body.clienteId());
        Fattura newFattura = new Fattura(body.data(),body.importo(), body.numero(), newStato,found);
        return this.fatturaRepository.save(newFattura);
    }

    public StatoFattura findStatoByString(String string){
        return this.statoFatturaRepository.findByStatoStr(string).orElseThrow(()-> new NotFoundException("Stato della fattura non esistente"));
    }

    public Fattura findById(UUID id){
        return this.fatturaRepository.findById(id).orElseThrow(()-> new NotFoundException("Nessuna fattura trovata con l'id inserito"));
    }

    public void delete(UUID id){
        this.fatturaRepository.delete(this.findById(id));
    }

    public Fattura update(UUID id, FatturaDTO body){
        Fattura found = this.findById(id);
        StatoFattura stato = this.findStatoByString(body.stato());
        found.setImporto(body.importo());
        found.setStato(stato);
        return this.fatturaRepository.save(found);
    }

    public Page<Fattura> getAll(int pageNumber, int pageSize, String sortBy){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return this.fatturaRepository.findAll(pageable);
    }

    public Page<Fattura> findByCliente(UUID id,int pageNumber, int pageSize, String sortBy){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return this.fatturaRepository.findByClienteId(id, pageable);
    }

    public Page<Fattura> findByStato(StatoFatturaDTO body, int pageNumber, int pageSize, String sortBy){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        StatoFattura found = this.findStatoByString(body.statoFattura());
        return this.fatturaRepository.findByStatoFatturaId(found.getId(),pageable);
    }

    public Page<Fattura> findByYear(FatturaAnnoDTO body, int pageNumber, int pageSize, String sortBy){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        LocalDate start = Year.of(body.anno()).atDay(1);
        LocalDate end = Year.of(body.anno()).atMonth(12).atEndOfMonth();
        return this.fatturaRepository.findByDataBetween(start,end, pageable);
    }

    public Page<Fattura> findByDate(FatturaDataDTO body, int pageNumber, int pageSize, String sortBy){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return this.fatturaRepository.findByData(body.data(), pageable);
    }

    public Page<Fattura> findByRange(FatturaRangeImporti body, int pageNumber, int pageSize, String sortBy)
    {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        if (body.min() > body.max()) throw new BadRequestException("Il valore minimo inserito è maggiore del valore massimo inserito");
        if (body.min() == body.max()) throw new BadRequestException("Il valore minimo inserito è uguale al valore massimo inserito");
        return this.fatturaRepository.findByImportoBetween(body.min(), body.max(), pageable);
    }

    public Page<Fattura> sortBy(UUID clienteId, StatoFatturaDTO stato, FatturaAnnoDTO anno, FatturaDataDTO data, FatturaRangeImporti importi,
                                 int pageNumber, int pageSize, String sortBy,  String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        if (clienteId!= null) {
            return this.findByCliente(clienteId, pageNumber,pageSize,sortBy);
        }
        if ( stato != null){
            return findByStato(stato,pageNumber,pageSize,sortBy);
        }
        if (anno != null) {
            return findByYear(anno, pageNumber, pageSize, sortBy);
        }
        if (data != null){
            return  findByDate(data,pageNumber, pageSize, sortBy);
        }
        if (importi != null){
            return  findByRange(importi, pageNumber, pageSize, sortBy);
        }
        return fatturaRepository.findAll(pageable);
    }


    }

