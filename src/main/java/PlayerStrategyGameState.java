import student.crazyeights.Card;
import student.crazyeights.PlayerStrategy;

import java.util.List;

public class PlayerStrategyGameState {
  List<Card> cardsInHand;
  public int score;
  public int selfId;
  PlayerStrategy player;

  PlayerStrategyGameState(PlayerStrategy player, int playerId) {
    this.player = player;
    this.selfId = playerId;
  }

  PlayerStrategy getPlayerStrategy() {
    return player;
  }
}
