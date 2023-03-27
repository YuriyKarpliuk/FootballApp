package yurii.karpliuk.crudapp.service;

import yurii.karpliuk.crudapp.enums.PlayerPosition;
import yurii.karpliuk.crudapp.model.Position;


public interface PositionService {
    Position getByName(PlayerPosition playerPosition);
}
