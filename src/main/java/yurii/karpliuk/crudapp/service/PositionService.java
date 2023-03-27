package yurii.karpliuk.crudapp.service;

import yurii.karpliuk.crudapp.enums.PlayerPosition;
import yurii.karpliuk.crudapp.model.Position;

import java.util.List;

public interface PositionService {
    Position getByName(PlayerPosition playerPosition);
}
