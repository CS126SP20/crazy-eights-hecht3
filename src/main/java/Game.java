import student.crazyeights.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Random;


public class Game {

  static final int DEAL_SIZE = 5;

  Random rand = new Random(0);
  public List<Card> deck;
  public List<Card> draw;
  public List<Card> discard;
  private PlayerStrategy player1;
  private PlayerStrategy player2;
  private PlayerStrategy player3;
  private PlayerStrategy player4;
  private List<Card> player1CardsInHand;
  private List<Card> player2CardsInHand;
  private List<Card> player3CardsInHand;
  private List<Card> player4CardsInHand;
  List<PlayerStrategy> playerList = new ArrayList<>();

  public Game(PlayerStrategy player1, PlayerStrategy player2,
              PlayerStrategy player3, PlayerStrategy player4) {
    deck = Card.getDeck();
    Collections.shuffle(deck, rand);
    this.player1 = player1;
    this.player2 = player2;
    this.player3 = player3;
    this.player4 = player4;
    playerList.add(player1);
    playerList.add(player2);
    playerList.add(player3);
    playerList.add(player4);
    player1CardsInHand = deal(player1);
    player2CardsInHand = deal(player2);
    player3CardsInHand = deal(player3);
    player4CardsInHand = deal(player4);
    draw = deck;
  }

  public void play() {

  }

  public List<Card> deal(PlayerStrategy player) {
    List<Card> playerHand = new ArrayList<>();
    for (int i = 0; i < DEAL_SIZE; i++) {
      // We can just take the card in position 0 of the deck to simulate taking the top card off
      // of a deck. The deck was already shuffled in the constructor.
      Card toDeal = deck.get(0);
      playerHand.add(toDeal);
      deck.remove(toDeal);
    }
//    player.receiveInitialCards(playerHand);
    return playerHand;
  }

  // Returns the the player that won
  PlayerStrategy performRound() {
    return null;
  }
  boolean isValidMove(Card cardPlayed, PlayerStrategy player) {
    if (    (draw.get(0).getSuit().equals(cardPlayed.getSuit())
          || draw.get(0).getRank().equals(cardPlayed.getRank())
          || cardPlayed.getRank().equals(Card.Rank.EIGHT))
          && getPlayerHand(player).contains(cardPlayed)) {
      return true;
    } else {
      return false;
    }
  }
  void calculateScore() {

  }
  boolean isEndOfGame() {
    return false;
  }
  List<Card> getPlayerHand(PlayerStrategy givenPlayer) {
    if (player1.equals(givenPlayer)) {
      return player1CardsInHand;
    } else if (player2.equals(givenPlayer)) {
      return player2CardsInHand;
    } else if (player3.equals(givenPlayer)) {
      return player3CardsInHand;
    } else if (player4.equals(givenPlayer)) {
        return player4CardsInHand;
    } else {
      return null;
    }
  }
  List<Card> getDeck() {
    return deck;
  }
  List<Card> getPlayer1CardsInHand() {
    return player1CardsInHand;
  }
  List<Card> getPlayer2CardsInHand() {
    return player2CardsInHand;
  }
  List<Card> getPlayer3CardsInHand() {
    return player3CardsInHand;
  }
  List<Card> getPlayer4CardsInHand() {
    return player4CardsInHand;
  }
  List<PlayerStrategy> getPlayerList() {
    return playerList;
  }
  void setDraw(Card card) {
    draw.set(0, card);
  }
}
