package yurii.karpliuk.crudapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import yurii.karpliuk.crudapp.model.Footballer;
import yurii.karpliuk.crudapp.model.Position;
import yurii.karpliuk.crudapp.model.Team;


public interface FootballerRepository extends JpaRepository<Footballer,Long> , JpaSpecificationExecutor<Footballer> {
    Page<Footballer> findByTeam(Team team, Pageable pageable);
    Page<Footballer> findByPosition(Position position, Pageable pageable);

    Page<Footballer> findAllByFirstNameLike(String firstName,Pageable pageable);
}
