import student.crazyeights.Card;
import student.crazyeights.PlayerStrategy;

import java.util.List;

public class PlayerStrategyGameState {
  List<Card> cardsInHand;
  public int score;
  public int selfId;
  PlayerStrategy player;

  /**
   * The constructor for the PlayerStrategyGameState class. Associates the PlayerStrategy with this
   * instance of the class and establishes the playerId for this instance.
   *
   * @param player the PlayerStrategy of the given player
   * @param playerId the ID of the given player
   */
  PlayerStrategyGameState(PlayerStrategy player, int playerId) {
    this.player = player;
    this.selfId = playerId;
  }

  /**
   * A getter made so that there isn't excessive typecasting in Game
   *
   * @return the PlayerStrategy associated with the given instance.
   */
  PlayerStrategy getPlayerStrategy() {
    return player;
  }
}
