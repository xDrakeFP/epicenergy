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
        return (root, query, criteriaBuilder) ->
                ( nome == null || nome.isBlank()) ? null:
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("nome_contatto")),"%" + nome.toLowerCase() + "%");

    }



}
