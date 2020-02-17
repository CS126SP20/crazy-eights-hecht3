import student.crazyeights.Card;

public class PlayerStrategy1 extends PlayerStrategyAbstract{
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
}
