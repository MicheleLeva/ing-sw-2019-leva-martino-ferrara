package utils.update;

import model.events.*;

public interface PowerUpUpdate {
    void update(ChoosePowerUpMessage choosePowerUpMessage);
    void update(ChooseTeleporterSquareMessage chooseTeleporterSquareMessage);
    void update(ChooseNewtonOpponentMessage chooseNewtonOpponentMessage);
    void update(ChooseNewtonSquareMessage chooseNewtonSquareMessage);
}
