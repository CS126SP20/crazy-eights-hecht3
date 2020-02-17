import org.junit.Before;
import org.junit.Test;
import student.crazyeights.Card;
import student.crazyeights.PlayerTurn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class GameTest {
  Random rand = new Random();
  PlayerStrategyAbstract abstractPlayerStrategy;
  Card randomCard;
  Game game;
  private static final int DECK_SIZE = 52;
  private static final int NUM_PLAYERS = 4;

  @Before
  public void setUp() {
    abstractPlayerStrategy = new PlayerStrategyAbstract() {
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
    List<Integer> opponentIds = new ArrayList<>(Arrays.asList(2, 3, 4));
    abstractPlayerStrategy.init(1, opponentIds);
    List<Card> deck = Card.getDeck();
    randomCard = deck.get(deck.size() - 1);
    game = new Game(abstractPlayerStrategy, abstractPlayerStrategy,
                    abstractPlayerStrategy, abstractPlayerStrategy);
    /*



      CHANGE THESE PLAYER STRATEGIES AFTER THEY HAVE BEEN COMPLETED TO ACTUAL PLAYERSTRATEGIES.




     */
  }

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
}
