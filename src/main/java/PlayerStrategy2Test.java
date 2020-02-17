import org.junit.Before;
import org.junit.Test;
import student.crazyeights.Card;

import java.util.*;

import static org.junit.Assert.*;

public class PlayerStrategy2Test {
  PlayerStrategy2 player1;

  @Before
  public void setUp() { ;
    List<Integer> opponentIds = new ArrayList<>(Arrays.asList(2, 3, 4));
    player1 = new PlayerStrategy2();
    player1.init(1, Arrays.asList(2, 3, 4));
  }

  @Test
  public void shouldDrawCardTestTrue() {
    player1.receiveInitialCards(Arrays.asList(new Card(Card.Suit.DIAMONDS, Card.Rank.ACE)));
    assertTrue(player1.shouldDrawCard(new Card(Card.Suit.SPADES, Card.Rank.TWO), null));
  }

  @Test
  public void shouldDrawCardTestFalse() {
    player1.receiveInitialCards(Arrays.asList(new Card(Card.Suit.DIAMONDS, Card.Rank.ACE)));
    player1.shouldDrawCard(new Card(Card.Suit.DIAMONDS, Card.Rank.TWO), Card.Suit.DIAMONDS);
    assertFalse(player1.shouldDrawCard(new Card(Card.Suit.DIAMONDS, Card.Rank.TWO),
            Card.Suit.DIAMONDS));
  }

  @Test
  public void playCardIdeal() {
    player1.receiveInitialCards(Arrays.asList(new Card(Card.Suit.SPADES, Card.Rank.SIX),
            new Card(Card.Suit.CLUBS, Card.Rank.SIX),
            new Card(Card.Suit.DIAMONDS, Card.Rank.QUEEN)));
    player1.shouldDrawCard(new Card(Card.Suit.CLUBS, Card.Rank.ACE), Card.Suit.DIAMONDS);
    player1.playerBeforePlayedCards = Arrays.asList(new Card(Card.Suit.DIAMONDS, Card.Rank.ACE),
            new Card(Card.Suit.SPADES, Card.Rank.TWO),
            new Card(Card.Suit.SPADES, Card.Rank.THREE));
    player1.playerAfterPlayedCards = Arrays.asList(new Card(Card.Suit.CLUBS, Card.Rank.FOUR),
            new Card(Card.Suit.SPADES, Card.Rank.FOUR),
            new Card(Card.Suit.HEARTS, Card.Rank.FOUR));
    player1.playerAcrossPlayedCards = Arrays.asList(new Card(Card.Suit.DIAMONDS, Card.Rank.FIVE),
            new Card(Card.Suit.DIAMONDS, Card.Rank.SIX),
            new Card(Card.Suit.DIAMONDS, Card.Rank.SEVEN));
    assertEquals(new Card(Card.Suit.CLUBS, Card.Rank.SIX), player1.playCard());
  }

  @Test
  public void playCardIdealSuit() {
    player1.receiveInitialCards(Arrays.asList(new Card(Card.Suit.CLUBS, Card.Rank.SEVEN),
            new Card(Card.Suit.CLUBS, Card.Rank.FOUR),
            new Card(Card.Suit.DIAMONDS, Card.Rank.NINE)));
    player1.shouldDrawCard(new Card(Card.Suit.DIAMONDS, Card.Rank.FOUR), Card.Suit.DIAMONDS);
    player1.playerBeforePlayedCards = Arrays.asList(new Card(Card.Suit.DIAMONDS, Card.Rank.ACE),
            new Card(Card.Suit.DIAMONDS, Card.Rank.TWO),
            new Card(Card.Suit.DIAMONDS, Card.Rank.THREE));
    player1.playerAfterPlayedCards = Arrays.asList(new Card(Card.Suit.CLUBS, Card.Rank.FOUR),
            new Card(Card.Suit.SPADES, Card.Rank.FOUR),
            new Card(Card.Suit.HEARTS, Card.Rank.FOUR));
    player1.playerAcrossPlayedCards = Arrays.asList(new Card(Card.Suit.DIAMONDS, Card.Rank.FIVE),
            new Card(Card.Suit.DIAMONDS, Card.Rank.SIX),
            new Card(Card.Suit.DIAMONDS, Card.Rank.SEVEN));
    assertEquals(new Card(Card.Suit.CLUBS, Card.Rank.FOUR), player1.playCard());
  }

  @Test
  public void playCardIdealRank() {
    player1.receiveInitialCards(Arrays.asList(new Card(Card.Suit.CLUBS, Card.Rank.KING),
            new Card(Card.Suit.SPADES, Card.Rank.SIX),
            new Card(Card.Suit.DIAMONDS, Card.Rank.KING)));
    player1.shouldDrawCard(new Card(Card.Suit.CLUBS, Card.Rank.ACE), Card.Suit.CLUBS);
    player1.playerBeforePlayedCards = Arrays.asList(new Card(Card.Suit.DIAMONDS, Card.Rank.ACE),
            new Card(Card.Suit.DIAMONDS, Card.Rank.TWO),
            new Card(Card.Suit.DIAMONDS, Card.Rank.THREE));
    player1.playerAfterPlayedCards = Arrays.asList(new Card(Card.Suit.CLUBS, Card.Rank.ACE),
            new Card(Card.Suit.SPADES, Card.Rank.TWO),
            new Card(Card.Suit.HEARTS, Card.Rank.ACE));
    player1.playerAcrossPlayedCards = Arrays.asList(new Card(Card.Suit.DIAMONDS, Card.Rank.FIVE),
            new Card(Card.Suit.DIAMONDS, Card.Rank.SIX),
            new Card(Card.Suit.DIAMONDS, Card.Rank.SEVEN));
    assertEquals(new Card(Card.Suit.CLUBS, Card.Rank.KING), player1.playCard());
  }

  @Test
  public void playCardEight() {
    player1.receiveInitialCards(Arrays.asList(new Card(Card.Suit.DIAMONDS, Card.Rank.EIGHT),
            new Card(Card.Suit.SPADES, Card.Rank.SIX),
            new Card(Card.Suit.DIAMONDS, Card.Rank.KING)));
    player1.shouldDrawCard(new Card(Card.Suit.CLUBS, Card.Rank.ACE), Card.Suit.CLUBS);
    player1.playerBeforePlayedCards = Arrays.asList(new Card(Card.Suit.DIAMONDS, Card.Rank.ACE),
            new Card(Card.Suit.DIAMONDS, Card.Rank.TWO),
            new Card(Card.Suit.DIAMONDS, Card.Rank.THREE));
    player1.playerAfterPlayedCards = Arrays.asList(new Card(Card.Suit.CLUBS, Card.Rank.ACE),
            new Card(Card.Suit.SPADES, Card.Rank.TWO),
            new Card(Card.Suit.HEARTS, Card.Rank.ACE));
    player1.playerAcrossPlayedCards = Arrays.asList(new Card(Card.Suit.DIAMONDS, Card.Rank.FIVE),
            new Card(Card.Suit.DIAMONDS, Card.Rank.SIX),
            new Card(Card.Suit.DIAMONDS, Card.Rank.SEVEN));
    assertEquals(new Card(Card.Suit.DIAMONDS, Card.Rank.EIGHT), player1.playCard());
  }
}
