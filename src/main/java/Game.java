import student.crazyeights.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Random;


public class Game {

  static final int DEAL_SIZE = 5;

  Random rand = new Random();
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
    Collections.shuffle(deck);
    this.player1 = player1;
    this.player2 = player2;
    this.player3 = player3;
    this.player4 = player4;
    playerList.add(player1);
    playerList.add(player2);
    playerList.add(player3);
    playerList.add(player4);
    player1CardsInHand = deal();
    player2CardsInHand = deal();
    player3CardsInHand = deal();
    player4CardsInHand = deal();
    draw = deck;
  }

  public void play() {

  }

  public List<Card> deal() {
    List<Card> playerHand = new ArrayList<>();
    for (int i = 0; i < DEAL_SIZE; i++) {
      // We can just take the card in position 0 of the deck to simulate taking the top card off
      // of a deck. The deck was already shuffled in the constructor.
      Card toDeal = deck.get(0);
      playerHand.add(toDeal);
      deck.remove(toDeal);
    }
    return playerHand;
  }

  // Returns the ID of the player that won
  int performRound() {
    return 0;
  }
  boolean isValidMove(Card cardPlayed) {
    return false;
  }
  void calculateScore() {

  }
  boolean isEndOfGame() {
    return false;
  }
  List<Card> getDeck() {
    return deck;
  }
  List<Card> getPlayer1CardsInHand() {
    return player1CardsInHand;
  }
}
