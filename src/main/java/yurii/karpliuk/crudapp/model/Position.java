package yurii.karpliuk.crudapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yurii.karpliuk.crudapp.enums.PlayerPosition;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private PlayerPosition name;

    @OneToMany(mappedBy = "position", cascade = CascadeType.REMOVE)
    private Set<Footballer> footballers = new HashSet<>();


}
