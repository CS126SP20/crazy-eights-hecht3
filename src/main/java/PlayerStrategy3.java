import student.crazyeights.Card;

import java.util.*;

public class PlayerStrategy3 extends PlayerStrategyAbstract{

  /**
   * Called every time the player takes a turn to see if that player needs to draw a card or has
   * a possible move in its hand.
   *
   * @param topPileCard The card on the top of the discard pile
   * @param changedSuit The suit of the card on the top of the discard pile or the suit declared
   *                    by a player that played an eight.
   * @return whether or not the player needs to draw a card. Will always be false because this
   * PlayerStrategy always cheats.
   */
  @Override
  public boolean shouldDrawCard(Card topPileCard, Card.Suit changedSuit) {
    topCard = topPileCard;
    return false;
  }

  /**
   * Called every time this player has a turn. In this PlayerStrategy, playCard looks for the
   * suit and rank most played by the other players using the helper function findIdealSuitAndRank
   * and creates and plays that card in order to increase the chances of the other players having to
   * draw on their next turn. The card is played regardless of whether or not the user has the card
   * in its hand. Additionally, if the topCard is not the ideal suit, this PlayerStrategy plays an
   * eight to make it the ideal suit.
   *
   * @return the ideal card to play.
   */
  @Override
  public Card playCard() {
    if (idealSuit.equals(topCard.getSuit()) || idealRank.equals(topCard.getRank())) {
      return new Card(idealSuit, idealRank);
    } else {
      return new Card(idealSuit, Card.Rank.EIGHT);
    }
  }

  /**
   * Finds the ideal suit and rank to play. The ideal suit and rank is the suit and rank most played
   * by others so far in the game.
   */
  private void findIdealSuitAndRank() {
    Map<Card.Suit, Integer> suitsPlayed = new HashMap<>();
    Map<Card.Rank, Integer> ranksPlayed = new HashMap<>();

    for (Card.Suit suit : Card.Suit.values()) {
      suitsPlayed.put(suit, 0);
    }
    for (Card.Rank rank : Card.Rank.values()) {
      ranksPlayed.put(rank, 0);
    }

    List<Card> allPlayedCards = new ArrayList<>();
    allPlayedCards.addAll(playerBeforePlayedCards);
    allPlayedCards.addAll(playerAfterPlayedCards);
    allPlayedCards.addAll(playerAcrossPlayedCards);

    for (Card card : allPlayedCards) {
      suitsPlayed.put(card.getSuit(), suitsPlayed.get(card.getSuit()) + 1);
      ranksPlayed.put(card.getRank(), ranksPlayed.get(card.getRank()) + 1);
    }
    /**
     * The following for loop was derived from
     * https://stackoverflow.com/questions/5911174/finding-key-associated-with-max-value-in-a-java-map
     * for finding the key with the max value in a HashMap.
     */
    Map.Entry<Card.Suit, Integer> maxEntrySuit = null;
    for (Map.Entry<Card.Suit, Integer> entry : suitsPlayed.entrySet()) {
      if (maxEntrySuit == null || entry.getValue().compareTo(maxEntrySuit.getValue()) > 0) {
        maxEntrySuit = entry;
      }
    }
    idealSuit = maxEntrySuit.getKey();

    Map.Entry<Card.Rank, Integer> maxEntryRank = null;
    for (Map.Entry<Card.Rank, Integer> entry : ranksPlayed.entrySet()) {
      if (maxEntryRank == null || entry.getValue().compareTo(maxEntryRank.getValue()) > 0) {
        maxEntryRank = entry;
      }
    }
    idealRank = maxEntryRank.getKey();
  }

  /**
   * @return the suit the player wants to declare (called if they play an eight).
   */
  @Override
  public Card.Suit declareSuit() {
    return idealSuit;
  }
}
