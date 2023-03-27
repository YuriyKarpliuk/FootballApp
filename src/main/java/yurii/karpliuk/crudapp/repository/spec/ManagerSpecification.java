package yurii.karpliuk.crudapp.repository.spec;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import yurii.karpliuk.crudapp.enums.PlayerPosition;
import yurii.karpliuk.crudapp.model.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ManagerSpecification implements Specification<Manager> {
    private String firstName;

    private String lastName;

    private Integer age;

    private Integer weight;

    private Integer height;


    private Team team;

    private OperationValue ageOperation;
    private OperationValue weightOperation;
    private OperationValue heightOperation;
    private OperationValue priceOperation;

    private void predicatedByLastName(Root<Manager> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (!StringUtils.isBlank(this.lastName)) {
            predicates.add(criteriaBuilder.like(root.get("lastName"), this.lastName));
        }
    }


    private void predicateByAge(Root<Manager> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (this.age != null) {
            if (!StringUtils.isBlank(this.ageOperation.getName().toString())) {
                if (this.ageOperation.getName().toString().equals("GREATER")) {
                    predicates.add(criteriaBuilder.greaterThan(root.get("age"), this.age));
                    return;
                } else if (this.ageOperation.getName().toString().equals("LESS")) {
                    predicates.add(criteriaBuilder.lessThan(root.get("age"), this.age));
                    return;
                } else if (this.ageOperation.getName().toString().equals("EQUAL")) {
                    predicates.add(criteriaBuilder.equal(root.get("age"), this.age));
                    return;
                }
                predicates.add(criteriaBuilder.equal(root.get("age"), this.age));
            }
        }
    }


    private void predicateByWeight(Root<Manager> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (this.weight != null) {
            if (!StringUtils.isBlank(this.weightOperation.getName().toString())) {
                if (this.weightOperation.getName().toString().equals("GREATER")) {
                    predicates.add(criteriaBuilder.greaterThan(root.get("weight"), this.weight));
                    return;
                } else if (this.weightOperation.getName().toString().equals("LESS")) {
                    predicates.add(criteriaBuilder.lessThan(root.get("weight"), this.weight));
                    return;
                } else if (this.weightOperation.getName().toString().equals("EQUAL")) {
                    predicates.add(criteriaBuilder.equal(root.get("weight"), this.weight));
                    return;
                }
                predicates.add(criteriaBuilder.equal(root.get("weight"), this.weight));
            }
        }
    }

    private void predicateByHeight(Root<Manager> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (this.height != null) {
            if (!StringUtils.isBlank(this.heightOperation.toString())) {
                if (this.heightOperation.getName().toString().equals("GREATER")) {
                    predicates.add(criteriaBuilder.greaterThan(root.get("height"), this.height));
                    return;
                } else if (this.heightOperation.getName().toString().equals("LESS")) {
                    predicates.add(criteriaBuilder.lessThan(root.get("height"), this.height));
                    return;
                } else if (this.heightOperation.getName().toString().equals("EQUAL")) {
                    predicates.add(criteriaBuilder.equal(root.get("height"), this.height));
                    return;
                }
                predicates.add(criteriaBuilder.equal(root.get("height"), this.height));
            }
        }
    }


    private void predicatedByTeam(Root<Manager> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (this.team != null) {
            Join<Manager, Team> teamJoin = root.join("team");
            predicates.add(criteriaBuilder.equal(teamJoin.<PlayerPosition>get("name"), this.team.getName()));

        }
    }

    @Override
    public Predicate toPredicate(Root<Manager> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        predicatedByLastName(root, criteriaBuilder, predicates);
        predicatedByTeam(root, criteriaBuilder, predicates);
        predicateByAge(root, criteriaBuilder, predicates);
        predicateByHeight(root, criteriaBuilder, predicates);
        predicateByWeight(root, criteriaBuilder, predicates);
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
