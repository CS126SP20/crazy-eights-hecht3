import org.junit.Before;
import org.junit.Test;
import student.crazyeights.Card;
import student.crazyeights.PlayerStrategy;
import student.crazyeights.PlayerTurn;

import java.util.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertThrows;

public class GameCheatingTest {
  Random rand = new Random(0);
  PlayerStrategy player1;
  PlayerStrategy player2;
  PlayerStrategy player3;
  PlayerStrategy player4;
  Card randomCard;
  Game game;
  List<Card> deck;
  private static final int DECK_SIZE = 52;
  private static final int NUM_PLAYERS = 4;

  @Before
  public void setUp() {
    List<Integer> opponentIds = new ArrayList<>(Arrays.asList(2, 3, 4));
    player1 = new PlayerStrategy1();
    player2 = new PlayerStrategy2();
    player3 = new PlayerStrategy1();
    player4 = new PlayerStrategy3();
    player1.init(1, Arrays.asList(2, 3, 4));
    player2.init(2, Arrays.asList(1, 3, 4));
    player3.init(3, Arrays.asList(2, 4, 1));
    player4.init(2, Arrays.asList(1, 2, 3));
    deck = Card.getDeck();
    Collections.shuffle(deck, rand);
    game = new Game(player1, player2, player3, player4);
  }

  // Needed a separate class for this test because I want to use the PlayerStrategy3 in setup.
  @Test
  public void cheatingTest() {
    assertThrows(Exception.class, () -> {game.play();});
  }
}
