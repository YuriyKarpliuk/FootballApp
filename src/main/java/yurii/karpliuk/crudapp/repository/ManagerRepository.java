package yurii.karpliuk.crudapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import yurii.karpliuk.crudapp.model.Manager;
import yurii.karpliuk.crudapp.model.Team;

public interface ManagerRepository extends JpaRepository<Manager,Long>, JpaSpecificationExecutor<Manager> {
    Page<Manager> findByTeam(Team team, Pageable pageable);

    Page<Manager> findAllByLastNameLike(String lastName, Pageable pageable);

}
