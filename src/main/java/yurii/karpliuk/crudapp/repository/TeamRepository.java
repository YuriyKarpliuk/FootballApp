package yurii.karpliuk.crudapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yurii.karpliuk.crudapp.model.Team;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team,Long> {
    Optional<Team> findByName(String name);
}
