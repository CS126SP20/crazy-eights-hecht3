import student.crazyeights.Card;

import java.util.*;

public class PlayerStrategy1 extends PlayerStrategyAbstract {

  /**
   * Called every time the player takes a turn to see if that player needs to draw a card or has
   * a possible move in its hand.
   *
   * @param topPileCard The card on the top of the discard pile
   * @param changedSuit The suit of the card on the top of the discard pile or the suit declared
   *                    by a player that played an eight.
   * @return whether or not the player needs to draw a card
   */
  @Override
  public boolean shouldDrawCard(Card topPileCard, Card.Suit changedSuit) {
    topCard = topPileCard;
    boolean drawCard = true;
    for (Card card : cardsInHand) {
      if (card.getSuit().equals(changedSuit) || card.getRank().equals(topPileCard.getRank())) {
        drawCard = false;
        break;
      }
    }
    return drawCard;
  }

  /**
   * Called when the player is able to play card. In this PlayerStrategy, playCard looks for the
   * suit and rank most played by the other players using the helper function findIdealSuitAndRank
   * and tries to play that card in order to increase the chances of the other players having to
   * draw on their next turn.
   *
   * @return the card to be played.
   */
  @Override
  public Card playCard() {
    findIdealSuitAndRank();

    // Create lists for each category of play. rank1 is an eight, rank2 is an ideal card, rank3 is
    // an ideal rank or suit, rank4 is none of the above.
    List<Card> rank1 = new ArrayList<>();
    List<Card> rank2 = new ArrayList<>();
    List<Card> rank3 = new ArrayList<>();
    List<Card> rank4 = new ArrayList<>();

    for (Card card : cardsInHand) {
      if (card.getRank().equals(Card.Rank.EIGHT)) {
        rank1.add(card);
        break;
      } else if (card.getSuit().equals(idealSuit)
          && card.getRank().equals(idealRank)
          && (topCard.getSuit().equals(card.getSuit())
              || topCard.getRank().equals(card.getRank()))) {
        rank2.add(card);
      } else if ((card.getSuit().equals(idealSuit) || card.getRank().equals(idealRank))
          && (topCard.getSuit().equals(card.getSuit())
              || topCard.getRank().equals(card.getRank()))) {
        rank3.add(card);
      } else if (topCard.getSuit().equals(card.getSuit())
          || topCard.getRank().equals(card.getRank())) {
        rank4.add(card);
      }
    }

    // Iterate through the list of playPossiblities (which is a list of ranks) and play the Card in
    // the highest rank.
    List<List<Card>> playPossibilities = new ArrayList<>(Arrays.asList(rank1, rank2, rank3, rank4));
    for (List<Card> list : playPossibilities) {
      if (list.size() > 0) {
        cardsInHand.remove(list.get(0));
        return list.get(0);
      }
    }
    // This method will never return null if shouldDrawCard() is called beforehand.
    return null;
  }

  /**
   * Finds the ideal suit and rank to play. The ideal suit and rank is the suit and rank most played
   * by others so far in the game.
   */
  private void findIdealSuitAndRank() {
    Map<Card.Suit, Integer> suitsPlayed = new HashMap<>();
    Map<Card.Rank, Integer> ranksPlayed = new HashMap<>();

    // Use the enumerable methods in Card class to put all the keys in the HashMaps
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

    // Place all cards played in their respective locations in the HashMap
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
