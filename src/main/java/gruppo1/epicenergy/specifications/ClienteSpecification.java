package gruppo1.epicenergy.specifications;

import gruppo1.epicenergy.entities.Cliente;
import gruppo1.epicenergy.entities.Provincia;
import gruppo1.epicenergy.repositories.ComuneRepository;
import gruppo1.epicenergy.repositories.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDate;
import java.util.UUID;


public class ClienteSpecification {

    @Autowired

    private ProvinciaRepository repo;

    @Autowired
    private ComuneRepository comuneRepository;


    public static Specification<Cliente> nameContains(String nome){
        return (root, query, criteriaBuilder) ->
                ( nome == null || nome.isBlank()) ? null:
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("ragioneSociale")),"%" + nome.toLowerCase() + "%");
    }

    public static Specification<Cliente> dataInserimentoDb(LocalDate data){
        return (root, query, criteriaBuilder) ->
                data == null? null : criteriaBuilder.lessThan(root.get("dataInserimento"), data);
    }

    public static Specification<Cliente> dataUltimoContattoDb(LocalDate data){
        return (root, query, criteriaBuilder) ->
                data == null? null : criteriaBuilder.lessThan(root.get("dataUltimoContatto"), data);
    }

    public static Specification<Cliente> fatturatoAnnuo(Double fatturato){
        return (root, query, criteriaBuilder) ->
        fatturato == null? null: criteriaBuilder.lessThan(root.get("fatturatoAnnuale"), fatturato);
    }

     public Specification<Cliente> provinciaSedeLegale(UUID provincia){
       return (root, query, criteriaBuilder) -> {
           if (provincia == null) return null;
           return criteriaBuilder.equal(
                   root.get("sedeLegale").get("comune").get("provincia").get("id"), provincia);
       };
    }

}
