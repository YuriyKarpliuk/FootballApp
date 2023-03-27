package yurii.karpliuk.crudapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yurii.karpliuk.crudapp.enums.PlayerPosition;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Footballer extends Person {
    @Column(name = "priceValue",nullable = false)
    private BigDecimal priceValue;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Team team;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Position position;
}
