import student.crazyeights.Card;
import student.crazyeights.PlayerStrategy;

import java.util.List;

public class PlayerStrategyGameState {
  List<Card> cardsInHand;
  public int score;
  PlayerStrategy player;

  PlayerStrategyGameState(PlayerStrategy player) {
    this.player = player;
  }
}
