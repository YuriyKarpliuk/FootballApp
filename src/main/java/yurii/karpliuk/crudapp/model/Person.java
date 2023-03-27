package yurii.karpliuk.crudapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "firstName",nullable = false,length = 50)
    private String firstName;
    @Column(name = "lastName",nullable = false,length = 50)

    private String lastName;

    @Column(name = "age",nullable = false)
    private Integer age;
    @Column(name = "weight",nullable = false)

    private Integer weight;
    @Column(name = "height",nullable = false)

    private Integer height;

    private String imgUrl;

}
