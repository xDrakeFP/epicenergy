package gruppo1.epicenergy.services;

import gruppo1.epicenergy.entities.Fattura;

import gruppo1.epicenergy.entities.StatoFattura;
import gruppo1.epicenergy.exceptions.NotFoundException;
import gruppo1.epicenergy.payloads.fatture.FatturaDTO;
import gruppo1.epicenergy.payloads.fatture.FatturaResponseDTO;
import gruppo1.epicenergy.payloads.fatture.StatoFatturaDTO;
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
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class FatturaService {

    private final FatturaRepository repository;


    @Autowired
    StatoFatturaRepository statoFatturaRepository;
    @Autowired
    StatoFatturaService statoFattura;
    @Autowired
    public FatturaService(FatturaRepository repository) {
        this.repository = repository;
    }

    private FatturaResponseDTO toDto(Fattura f) {
        return new FatturaResponseDTO(f.getId(), f.getData(), f.getImporto(), f.getNumero(), f.getStato(), f.getClienteId());
    }

    private Fattura fromDto(FatturaDTO dto) {
        Fattura f = new Fattura();
        f.setData(dto.data());
        f.setImporto(dto.importo());
        f.setNumero(dto.numero());
        f.setStato(dto.stato());
        f.setClienteId(dto.clienteId());
        return f;
    }

    public Page<FatturaResponseDTO> getAll(Pageable pageable) {
        Page<Fattura> page = repository.findAll(pageable);
        return page.map(this::toDto);
    }

    public FatturaResponseDTO getById(UUID id) {
        Optional<Fattura> f = repository.findById(id);
        return f.map(this::toDto).orElseThrow(() -> new IllegalArgumentException("Fattura non trovata: " + id));
    }

    public FatturaResponseDTO create(FatturaDTO dto) {
        Fattura f = fromDto(dto);
        Fattura saved = repository.save(f);
        return toDto(saved);
    }

    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Fattura non trovata: " + id);
        }
        repository.deleteById(id);
    }

    public FatturaResponseDTO update(UUID id, FatturaDTO dto) {
        Fattura existing = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Fattura non trovata: " + id));
        existing.setData(dto.data());
        existing.setImporto(dto.importo());
        existing.setNumero(dto.numero());
        existing.setStato(dto.stato());
        existing.setClienteId(dto.clienteId());
        Fattura saved = repository.save(existing);
        return toDto(saved);
    }

    public Page<FatturaResponseDTO> findByCliente(UUID clienteId, Pageable pageable) {
        Page<Fattura> page = repository.findByClienteId(clienteId, pageable);
        return page.map(this::toDto);
    }

    public Page<StatoFatturaDTO> findByStato(UUID id, int pageNumber, int pageSize, String sortBy) {
        StatoFattura foundFattura = statoFatturaRepository.findById(id).orElseThrow(()-> new NotFoundException("Fattura non trovata"));
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return statoFatturaRepository.findByStato(foundFattura.getStatoStr(), pageable);
    }

    public Page<FatturaResponseDTO> findByDate(LocalDate data, Pageable pageable) {
        Page<Fattura> page = repository.findByData(data, pageable);
        return page.map(this::toDto);
    }

    public Page<FatturaResponseDTO> findByYear(int anno, Pageable pageable) {
        LocalDate start = Year.of(anno).atDay(1);
        LocalDate end = Year.of(anno).atMonth(12).atEndOfMonth();
        Page<Fattura> page = repository.findByDataBetween(start, end, pageable);
        return page.map(this::toDto);
    }

    public Page<FatturaResponseDTO> findByRange(Double min, Double max, Pageable pageable) {
        if (min == null && max == null) {
            return getAll(pageable);
        } else if (min == null) {
            return repository.findByImportoLessThanEqual(max, pageable).map(this::toDto);
        } else if (max == null) {
            return repository.findByImportoGreaterThanEqual(min, pageable).map(this::toDto);
        } else {
            return repository.findByImportoBetween(min, max, pageable).map(this::toDto);
        }
    }
}
