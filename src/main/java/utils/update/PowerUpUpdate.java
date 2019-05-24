package utils.update;

import model.exchanges.messages.*;

public interface PowerUpUpdate {
    void update(ChoosePowerUpMessage choosePowerUpMessage);
    void update(ChooseTeleporterSquareMessage chooseTeleporterSquareMessage);
    void update(ChooseNewtonOpponentMessage chooseNewtonOpponentMessage);
    void update(ChooseNewtonSquareMessage chooseNewtonSquareMessage);
    void update(TargetingScopeMessage targetingScopeMessage);

    void update(TargetingScopeSelectionMessage targetingScopeSelectionMessage);
}
