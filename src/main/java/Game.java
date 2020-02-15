import student.crazyeights.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Random;


public class Game {

  public List<Card> deck;
  private PlayerStrategy player1;
  private PlayerStrategy player2;
  private PlayerStrategy player3;
  private PlayerStrategy player4;

  public Game(PlayerStrategy player1, PlayerStrategy player2, PlayerStrategy player3,
              PlayerStrategy player4) {
    deck = Card.getDeck();
    Collections.shuffle(deck);
    this.player1 = player1;
    this.player2 = player2;
    this.player3 = player3;
    this.player4 = player4;
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

}
