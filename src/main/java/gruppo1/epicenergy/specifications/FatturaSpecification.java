package gruppo1.epicenergy.specifications;

import gruppo1.epicenergy.entities.Cliente;
import gruppo1.epicenergy.entities.Fattura;
import gruppo1.epicenergy.entities.StatoFattura;
import gruppo1.epicenergy.repositories.FatturaRepository;
import gruppo1.epicenergy.repositories.ProvinciaRepository;
import gruppo1.epicenergy.repositories.StatoFatturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.UUID;

public class FatturaSpecification {

    @Autowired
    private FatturaRepository repo;
    @Autowired
    private StatoFatturaRepository repository;

    public static Specification<Fattura> clientIs(UUID cliente){
        return (root, query, criteriaBuilder) -> {
            if (cliente == null) return null;
            return criteriaBuilder.equal(root.get("cliente"), cliente);
        };
    }

    public static Specification<Fattura> fatturaStato(UUID idStato){
        return (root, query, criteriaBuilder) ->{
            if (idStato == null) return null;
            return criteriaBuilder.equal(root.get("stato").get("id"), idStato );
        };
    }

    public static Specification<Fattura> findPerAnno(LocalDate data){
        return (root, query, criteriaBuilder) ->{
            if (data == null) return null;
            int anno = data.getYear();
            LocalDate inizioAnno = LocalDate.of(anno, 1, 1);
            LocalDate fineAnno = LocalDate.of(anno, 12, 31);
            return criteriaBuilder.between(root.get("data"), inizioAnno, fineAnno);

        };
    }

    public static Specification<Fattura> findPerRange(Double min, Double max){
        return (root, query, criteriaBuilder) -> {
            if (min == null || max == null) return null;
            return criteriaBuilder.between(root.get("importo"), min, max);
        };
    }

}
