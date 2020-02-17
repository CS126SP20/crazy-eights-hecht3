import org.junit.Before;
import org.junit.Test;
import student.crazyeights.Card;

import java.util.*;

import static org.junit.Assert.*;

public class PlayerStrategy3Test {
  PlayerStrategy3 player1;

  @Before
  public void setUp() { ;
    List<Integer> opponentIds = new ArrayList<>(Arrays.asList(2, 3, 4));
    player1 = new PlayerStrategy3();
    player1.init(1, Arrays.asList(2, 3, 4));
  }

  @Test
  public void shouldDrawCardTestAlwaysFalse() {
    player1.receiveInitialCards(Arrays.asList(new Card(Card.Suit.DIAMONDS, Card.Rank.ACE)));
    assertFalse(player1.shouldDrawCard(new Card(Card.Suit.SPADES, Card.Rank.TWO), null));
  }
  

  @Test
  public void playCardIdeal() {
    player1.receiveInitialCards(Arrays.asList(new Card(Card.Suit.CLUBS, Card.Rank.QUEEN),
            new Card(Card.Suit.CLUBS, Card.Rank.SIX),
            new Card(Card.Suit.SPADES, Card.Rank.THREE)));
    player1.shouldDrawCard(new Card(Card.Suit.DIAMONDS, Card.Rank.ACE), Card.Suit.DIAMONDS);
    player1.playerBeforePlayedCards = Arrays.asList(new Card(Card.Suit.DIAMONDS, Card.Rank.ACE),
            new Card(Card.Suit.DIAMONDS, Card.Rank.TWO),
            new Card(Card.Suit.DIAMONDS, Card.Rank.THREE));
    player1.playerAfterPlayedCards = Arrays.asList(new Card(Card.Suit.CLUBS, Card.Rank.FOUR),
            new Card(Card.Suit.SPADES, Card.Rank.FOUR),
            new Card(Card.Suit.HEARTS, Card.Rank.FOUR));
    player1.playerAcrossPlayedCards = Arrays.asList(new Card(Card.Suit.DIAMONDS, Card.Rank.FIVE),
            new Card(Card.Suit.DIAMONDS, Card.Rank.SIX),
            new Card(Card.Suit.DIAMONDS, Card.Rank.SEVEN));
    assertEquals(new Card(Card.Suit.DIAMONDS, Card.Rank.FOUR), player1.playCard());
  }
  
  @Test
  public void playCardEight() {
    player1.receiveInitialCards(Arrays.asList(new Card(Card.Suit.DIAMONDS, Card.Rank.EIGHT),
            new Card(Card.Suit.DIAMONDS, Card.Rank.NINE),
            new Card(Card.Suit.DIAMONDS, Card.Rank.KING)));
    player1.shouldDrawCard(new Card(Card.Suit.CLUBS, Card.Rank.TWO), Card.Suit.CLUBS);
    player1.playerBeforePlayedCards = Arrays.asList(new Card(Card.Suit.SPADES, Card.Rank.ACE),
            new Card(Card.Suit.SPADES, Card.Rank.TWO),
            new Card(Card.Suit.SPADES, Card.Rank.THREE));
    player1.playerAfterPlayedCards = Arrays.asList(new Card(Card.Suit.CLUBS, Card.Rank.ACE),
            new Card(Card.Suit.SPADES, Card.Rank.NINE),
            new Card(Card.Suit.HEARTS, Card.Rank.ACE));
    player1.playerAcrossPlayedCards = Arrays.asList(new Card(Card.Suit.SPADES, Card.Rank.FIVE),
            new Card(Card.Suit.SPADES, Card.Rank.SIX),
            new Card(Card.Suit.SPADES, Card.Rank.SEVEN));
    player1.playCard();
    assertEquals(new Card(Card.Suit.SPADES, Card.Rank.EIGHT), player1.playCard());
  }
}
