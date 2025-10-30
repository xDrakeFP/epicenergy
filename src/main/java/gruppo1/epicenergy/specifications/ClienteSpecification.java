package gruppo1.epicenergy.specifications;

import gruppo1.epicenergy.entities.Cliente;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.lang.ref.Cleaner;

public class ClienteSpecification {
    public static Specification<Cliente> nameContains(String nome){
        Specification<Cliente> specification = (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get("ragione_sociale"), nome);
        };
        return specification;
    }



}
