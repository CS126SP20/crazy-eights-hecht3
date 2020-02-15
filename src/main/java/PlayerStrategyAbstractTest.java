import org.junit.Before;
import org.junit.Test;
import student.crazyeights.Card;
import student.crazyeights.PlayerStrategy;
import student.crazyeights.PlayerTurn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import static org.junit.Assert.*;

public class PlayerStrategyAbstractTest {
  Random rand = new Random();
  PlayerStrategyAbstract abstractPlayerStrategy;
  Card randomCard;
  private static final int DECK_SIZE = Card.Suit.values().length * Card.Rank.values().length;

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
    randomCard = Card.getDeck().get(rand.nextInt(DECK_SIZE));
  }

  // The following are general tests for PlayerStrategyAbstract, which creates the methods that each
  // PlayerStrategy has in common.
  // By testing these methods and having playerStrategyAbstract.init() in the @Before section, I am
  // also indirectly testing init.
  @Test
  public void cardCountingTest() {
    PlayerTurn turn = new PlayerTurn();
    // playerId 2 should be playerAfter in the context of turn.
    turn.playerId = 2;
    turn.playedCard = randomCard;
    turn.drewACard = false;
    List<PlayerTurn> turns = new ArrayList<>();
    turns.add(turn);
    abstractPlayerStrategy.processOpponentActions(turns);
    assertEquals(randomCard, abstractPlayerStrategy.getPlayerAfterPlayedCards().get(0));
  }

  @Test
  public void drewACardTestTrue() {
    PlayerTurn turn = new PlayerTurn();
    // playerId 4 should be playerBefore in the context of turn.
    turn.playerId = 4;
    turn.drewACard = true;
    List<PlayerTurn> turns = new ArrayList<>();
    turns.add(turn);
    abstractPlayerStrategy.processOpponentActions(turns);
    assertTrue(abstractPlayerStrategy.getPlayerBeforeDrewCard());
  }

  @Test
  public void drewACardTestFalse() {
    PlayerTurn turn = new PlayerTurn();
    // playerId 4 should be playerBefore in the context of turn.
    turn.playerId = 4;
    turn.drewACard = false;
    List<PlayerTurn> turns = new ArrayList<>();
    turns.add(turn);
    abstractPlayerStrategy.processOpponentActions(turns);
    assertFalse(abstractPlayerStrategy.getPlayerBeforeDrewCard());
  }

  @Test
  public void declaredSuitTestTrue() {
    PlayerTurn turn = new PlayerTurn();
    // playerId 2 should be playerAfter in the context of turn.
    turn.playerId = 2;
    turn.playedCard = randomCard;
    turn.drewACard = false;
    turn.declaredSuit = Card.Suit.DIAMONDS;
    List<PlayerTurn> turns = new ArrayList<>();
    turns.add(turn);
    abstractPlayerStrategy.processOpponentActions(turns);
    assertEquals(Card.Suit.DIAMONDS, abstractPlayerStrategy.getPlayerAfterDeclaredSuit());
  }

  @Test
  public void declaredSuitTestFalse() {
    PlayerTurn turn = new PlayerTurn();
    // playerId 2 should be playerAfter in the context of turn.
    turn.playerId = 2;
    turn.playedCard = randomCard;
    turn.drewACard = false;
    List<PlayerTurn> turns = new ArrayList<>();
    turns.add(turn);
    abstractPlayerStrategy.processOpponentActions(turns);
    assertNull(abstractPlayerStrategy.getPlayerAfterDeclaredSuit());
  }
}
