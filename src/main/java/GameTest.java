import org.junit.Before;
import org.junit.Test;
import student.crazyeights.Card;
import student.crazyeights.PlayerTurn;

import java.util.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class GameTest {
  Random rand = new Random(0);
  PlayerStrategyAbstract player1;
  PlayerStrategyAbstract player2;
  PlayerStrategyAbstract player3;
  PlayerStrategyAbstract player4;
  Card randomCard;
  Game game;
  List<Card> deck;
  private static final int DECK_SIZE = 52;
  private static final int NUM_PLAYERS = 4;

  @Before
  public void setUp() { ;
    List<Integer> opponentIds = new ArrayList<>(Arrays.asList(2, 3, 4));
    player1 = new PlayerStrategyAbstract() {
      @Override
      public boolean shouldDrawCard(Card topPileCard, Card.Suit changedSuit) {
        return false;
      }

      @Override
      public Card playCard() {
        return null;
      }

      @Override
      public Card.Suit declareSuit() {
        return null;
      }
    };
    player2 = new PlayerStrategyAbstract() {
      @Override
      public boolean shouldDrawCard(Card topPileCard, Card.Suit changedSuit) {
        return false;
      }

      @Override
      public Card playCard() {
        return null;
      }

      @Override
      public Card.Suit declareSuit() {
        return null;
      }
    };
    player1.init(1, Arrays.asList(2, 3, 4));
    player2.init(2, Arrays.asList(1, 3, 4));
    deck = Card.getDeck();
    Collections.shuffle(deck, rand);
    game = new Game(player1, player2, player3, player4);
    /*



      CHANGE THESE PLAYER STRATEGIES AFTER THEY HAVE BEEN COMPLETED TO ACTUAL PLAYERSTRATEGIES.




     */
  }
  // Getter functions are trivial and do not need to be tested (even getPlayerHand because it just
  // iterates through a list and checks for equality.
  // Test for the deal() function. The function is called 4 times when the constructor is called
  // (in @Before in this test class).
  // Also tests the constructor to see if it dealt cards to players by calling getPlayer1CardsInHand
  @Test
  public void dealTest() {
    assertEquals(5, game.getPlayer1CardsInHand().size());
    assertEquals(DECK_SIZE - Game.DEAL_SIZE * game.playerList.size(), game.getDeck().size());
  }

  @Test
  public void constructorTest() {
    assertEquals(NUM_PLAYERS, game.playerList.size());
  }

  @Test
  public void isValidMoveTestTrueRegular() {
    game.setDraw(game.getPlayer1CardsInHand().get(0));
    assertTrue(game.isValidMove(game.getPlayer1CardsInHand().get(0), game.getPlayerList().get(0)));
  }

  @Test
  public void isValidMoveTestFalse() {
    assertFalse(game.isValidMove(game.getPlayer2CardsInHand().get(0), game.getPlayerList().get(0)));
  }

}
