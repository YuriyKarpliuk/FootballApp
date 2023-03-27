package yurii.karpliuk.crudapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yurii.karpliuk.crudapp.enums.PlayerPosition;
import yurii.karpliuk.crudapp.model.Position;

public interface PositionRepository extends JpaRepository<Position,Long> {
    Position findByName(PlayerPosition playerPosition);
}
