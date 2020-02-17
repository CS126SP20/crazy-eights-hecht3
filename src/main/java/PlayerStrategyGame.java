import student.crazyeights.Card;
import student.crazyeights.PlayerStrategy;

import java.util.List;

public class PlayerStrategyGame {
  List<Card> cardsInHand;
  public int score;
  PlayerStrategy player;

  PlayerStrategyGame(PlayerStrategy player) {
    this.player = player;
  }
}
