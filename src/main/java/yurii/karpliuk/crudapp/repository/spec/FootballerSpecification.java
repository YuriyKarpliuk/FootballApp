package yurii.karpliuk.crudapp.repository.spec;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import yurii.karpliuk.crudapp.enums.FilterOperation;
import yurii.karpliuk.crudapp.enums.PlayerPosition;
import yurii.karpliuk.crudapp.model.Footballer;
import yurii.karpliuk.crudapp.model.OperationValue;
import yurii.karpliuk.crudapp.model.Position;
import yurii.karpliuk.crudapp.model.Team;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FootballerSpecification implements Specification<Footballer> {
    private BigDecimal priceValue;
    private String firstName;

    private String lastName;

    private Integer age;

    private Integer weight;

    private Integer height;

    private Position position;

    private Team team;

    private OperationValue ageOperation;
    private OperationValue weightOperation;
    private OperationValue heightOperation;
    private OperationValue priceOperation;

    private void predicatedByFirstName(Root<Footballer> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (!StringUtils.isBlank(this.firstName)) {
            predicates.add(criteriaBuilder.like(root.get("firstName"), this.firstName));
        }
    }

    private void predicatedByPosition(Root<Footballer> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (this.position != null) {
            Join<Footballer, Position> positionJoin = root.join("position");
            predicates.add(criteriaBuilder.equal(positionJoin.<PlayerPosition>get("name"), this.position.getName()));

        }
    }

    private void predicateByAge(Root<Footballer> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
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


    private void predicateByWeight(Root<Footballer> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
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

    private void predicateByHeight(Root<Footballer> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
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

    private void predicateByPrice(Root<Footballer> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (this.priceValue != null) {
            if (!StringUtils.isBlank(this.priceOperation.toString())) {
                if (this.priceOperation.getName().toString().equals("GREATER")) {
                    predicates.add(criteriaBuilder.greaterThan(root.get("priceValue"), this.priceValue));
                    return;
                } else if (this.priceOperation.getName().toString().equals("LESS")) {
                    predicates.add(criteriaBuilder.lessThan(root.get("priceValue"), this.priceValue));
                    return;
                } else if (this.priceOperation.getName().toString().equals("EQUAL")) {
                    predicates.add(criteriaBuilder.equal(root.get("priceValue"), this.priceValue));
                    return;
                }
                predicates.add(criteriaBuilder.equal(root.get("priceValue"), this.priceValue));
            }
        }
    }

    private void predicatedByTeam(Root<Footballer> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (this.team != null) {
            Join<Footballer, Team> teamJoin = root.join("team");
            predicates.add(criteriaBuilder.equal(teamJoin.<PlayerPosition>get("name"), this.team.getName()));

        }
    }

    @Override
    public Predicate toPredicate(Root<Footballer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        predicatedByFirstName(root, criteriaBuilder, predicates);
        predicatedByPosition(root, criteriaBuilder, predicates);
        predicatedByTeam(root, criteriaBuilder, predicates);
        predicateByAge(root, criteriaBuilder, predicates);
        predicateByHeight(root, criteriaBuilder, predicates);
        predicateByWeight(root, criteriaBuilder, predicates);
        predicateByPrice(root, criteriaBuilder, predicates);
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
