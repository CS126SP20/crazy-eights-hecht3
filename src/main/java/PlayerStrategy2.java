import student.crazyeights.Card;

import java.util.*;

public class PlayerStrategy2 extends PlayerStrategyAbstract{
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
    Map<Card.Suit, Integer> suitsInHand = new HashMap<Card.Suit, Integer>();
    Map<Card.Rank, Integer> ranksInHand = new HashMap<Card.Rank, Integer>();

    for (Card.Suit suit : Card.Suit.values()) {
      suitsInHand.put(suit, 0);
    }
    for (Card.Rank rank : Card.Rank.values()) {
      ranksInHand.put(rank, 0);
    }

    for (Card card : cardsInHand) {
      suitsInHand.put(card.getSuit(), suitsInHand.get(card.getSuit()) + 1);
      ranksInHand.put(card.getRank(), ranksInHand.get(card.getRank()) + 1);
    }
    /**
     * The following for loop was derived from
     * https://stackoverflow.com/questions/5911174/finding-key-associated-with-max-value-in-a-java-map
     * for finding the key with the max value in a HashMap.
     */
    Map.Entry<Card.Suit, Integer> maxEntrySuit = null;
    for (Map.Entry<Card.Suit, Integer> entry : suitsInHand.entrySet()) {
      if (maxEntrySuit == null || entry.getValue().compareTo(maxEntrySuit.getValue()) > 0) {
        maxEntrySuit = entry;
      }
    }
    idealSuit = maxEntrySuit.getKey();

    Map.Entry<Card.Rank, Integer> maxEntryRank = null;
    for (Map.Entry<Card.Rank, Integer> entry : ranksInHand.entrySet()) {
      if (maxEntryRank == null || entry.getValue().compareTo(maxEntryRank.getValue()) > 0) {
        maxEntryRank = entry;
      }
    }
    idealRank = maxEntryRank.getKey();

    List<Card> rank1 = new ArrayList<>();
    List<Card> rank2 = new ArrayList<>();
    List<Card> rank3 = new ArrayList<>();
    List<Card> rank4 = new ArrayList<>();
    for (Card card : cardsInHand) {
      if (card.getRank().equals(Card.Rank.EIGHT)) {
        rank1.add(card);
        break;
      } else if (card.getSuit().equals(idealSuit) && card.getRank().equals(idealRank)
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
    List<List<Card>> playPossibilities = new ArrayList<>(Arrays.asList(rank1, rank2, rank3, rank4));
    for (List<Card> list : playPossibilities) {
      if (list.size() > 0) {
        return list.get(0);
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
