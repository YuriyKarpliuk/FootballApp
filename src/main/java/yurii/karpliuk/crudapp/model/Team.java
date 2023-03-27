package yurii.karpliuk.crudapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false, length = 50)

    private String name;
    @Column(name = "country", nullable = false, length = 50)

    private String country;
    @Column(name = "city", nullable = false, length = 50)

    private String city;
    @OneToMany(mappedBy = "team", cascade = CascadeType.REMOVE)
    private Set<Footballer> footballers = new HashSet<>();
    @OneToOne(mappedBy = "team",cascade = CascadeType.REMOVE)
    private Manager manager;

    private String imgUrl;

}
