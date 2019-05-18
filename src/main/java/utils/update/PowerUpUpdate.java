package utils.update;

import model.exchanges.messages.ChooseNewtonOpponentMessage;
import model.exchanges.messages.ChooseNewtonSquareMessage;
import model.exchanges.messages.ChoosePowerUpMessage;
import model.exchanges.messages.ChooseTeleporterSquareMessage;

public interface PowerUpUpdate {
    void update(ChoosePowerUpMessage choosePowerUpMessage);
    void update(ChooseTeleporterSquareMessage chooseTeleporterSquareMessage);
    void update(ChooseNewtonOpponentMessage chooseNewtonOpponentMessage);
    void update(ChooseNewtonSquareMessage chooseNewtonSquareMessage);
}
