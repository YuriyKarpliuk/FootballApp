package yurii.karpliuk.crudapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yurii.karpliuk.crudapp.enums.FilterOperation;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OperationValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private FilterOperation name;
}
