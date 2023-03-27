package yurii.karpliuk.crudapp.service.impl;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import yurii.karpliuk.crudapp.enums.PlayerPosition;
import yurii.karpliuk.crudapp.model.Position;
import yurii.karpliuk.crudapp.repository.PositionRepository;
import yurii.karpliuk.crudapp.service.PositionService;


@Service
public class PositionServiceImpl implements PositionService {
    private PositionRepository positionRepository;

    public PositionServiceImpl(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @Override
    public Position getByName(PlayerPosition playerPosition) {
        return positionRepository.findByName(playerPosition);
    }

    @PostConstruct
    public void init() {
        if (positionRepository.findAll().isEmpty()) {
            for (PlayerPosition playerPosition : PlayerPosition.values()) {
                Position position = new Position();
                position.setName(playerPosition);
                positionRepository.save(position);
            }
        }
    }
}
