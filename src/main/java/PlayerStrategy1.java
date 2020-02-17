import student.crazyeights.Card;

import java.util.*;

public class PlayerStrategy1 extends PlayerStrategyAbstract{
  @Override
  public boolean shouldDrawCard(Card topPileCard, Card.Suit changedSuit) {
    topCard = topPileCard;
    boolean drawCard = true;
    for (Card card : cardsInHand) {
      if (card.getSuit().equals(changedSuit) || card.getRank().equals(topPileCard.getRank())) {
        drawCard = false;
      }
    }
    return drawCard;
  }

  @Override
  public Card playCard() {
    Map<Card.Suit, Integer> suitsPlayed = new HashMap<Card.Suit, Integer>();
    Map<Card.Rank, Integer> ranksPlayed = new HashMap<Card.Rank, Integer>();

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

    for (Card card : cardsInHand) {
      if (card.getSuit().equals(idealSuit) && card.getRank().equals(idealRank) &&
              (topCard.getSuit().equals(card.getSuit())
               || topCard.getRank().equals(card.getRank()))) {
        return card;
      } else if (card.getSuit().equals(idealSuit) || card.getRank().equals(idealRank) &&
              (topCard.getSuit().equals(card.getSuit())
               || topCard.getRank().equals(card.getRank()))) {
        return card;
      } else if (topCard.getSuit().equals(card.getSuit())
                 || topCard.getRank().equals(card.getRank())) {
        return card;
      }
    }
    // This method will never return null if shouldDrawCard() is called beforehand.
    return null;
  }

  @Override
  public Card.Suit declareSuit() {
    return idealSuit;
  }
}
