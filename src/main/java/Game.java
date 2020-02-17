import student.crazyeights.*;

import java.util.*;


public class Game {

  static final int DEAL_SIZE = 5;

  Random rand = new Random(0);
  public List<Card> deck;
  public List<Card> draw;
  public List<Card> discard;
  public boolean isEndOfGame;
  private PlayerStrategyGame player1;
  private PlayerStrategyGame player2;
  private PlayerStrategyGame player3;
  private PlayerStrategyGame player4;
  List<PlayerStrategyGame> playerList = new ArrayList<>();

  public Game(PlayerStrategy player1Init, PlayerStrategy player2Init,
              PlayerStrategy player3Init, PlayerStrategy player4Init) {
    deck = Card.getDeck();
    Collections.shuffle(deck, rand);
    this.player1 = new PlayerStrategyGame(player1Init);
    this.player2 = new PlayerStrategyGame(player2Init);
    this.player3 = new PlayerStrategyGame(player3Init);
    this.player4 = new PlayerStrategyGame(player4Init);
    playerList.add(player1);
    playerList.add(player2);
    playerList.add(player3);
    playerList.add(player4);
    player1.cardsInHand = deal(player1);
    player2.cardsInHand = deal(player2);
    player3.cardsInHand = deal(player3);
    player4.cardsInHand = deal(player4);
    draw = deck;
  }

  public void play() {

  }

  public List<Card> deal(PlayerStrategyGame player) {
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


  /*

////////// The scenario that a player wins and the draw pile is also empty is impossible because
////////// in order for the draw pile size to change, a player must draw, and if they had to draw
////////// then they could not play the last card in their hand.

   */
  // Returns the the player that won
  PlayerStrategy performRound() {
    ////////// should make a new playerturn for each turn that happens to that playerstrategies work
    ////////// correctly
    return null;
  }

  boolean isValidMove(Card cardPlayed, PlayerStrategyGame player) {
    if (    (draw.get(0).getSuit().equals(cardPlayed.getSuit())
          || draw.get(0).getRank().equals(cardPlayed.getRank())
          || cardPlayed.getRank().equals(Card.Rank.EIGHT))
          && player.cardsInHand.contains(cardPlayed)) {
      return true;
    } else {
      return false;
    }
  }
  void calculateScore(PlayerStrategyGame winner) {
    if (winner == null) {
      for (PlayerStrategyGame player : playerList) {
        for (PlayerStrategyGame opposingPlayer : playerList) {
          if (!opposingPlayer.equals(player)) {
            for (Card card : opposingPlayer.cardsInHand) {
              player.score += card.getPointValue();
            }
          }
        }
      }
    } else {
      for (PlayerStrategyGame player : playerList) {
        if (!player.equals(winner)) {
          for (Card card : player.cardsInHand) {
            winner.score += card.getPointValue();
          }
        }
      }
    }
  }

  PlayerStrategyGame checkEndOfGame() {
    if (draw.size() == 0) {
      isEndOfGame = true;
      return null;
    } else {
      for (PlayerStrategyGame player : playerList) {
        if (player.cardsInHand.size() == 0) {
          isEndOfGame = true;
          return player;
        }
      }
    }
    isEndOfGame = false;
    return null;
  }

  List<Card> getDeck() {
    return deck;
  }
  List<Card> getPlayer1CardsInHand() {
    return player1.cardsInHand;
  }
  List<Card> getPlayer2CardsInHand() {
    return player2.cardsInHand;
  }
  List<Card> getPlayer3CardsInHand() {
    return player3.cardsInHand;
  }
  List<Card> getPlayer4CardsInHand() {
    return player4.cardsInHand;
  }
  List<PlayerStrategyGame> getPlayerList() {
    return playerList;
  }
  int getPlayer1Score() {
    return player1.score;
  }
  void clearPlayer1CardsInHand() {
    player1.cardsInHand.clear();
  }
  void setPlayer1CardsInHand(List<Card> cards) {
    player1.cardsInHand.clear();
    player1.cardsInHand.addAll(cards);
  }
  void setPlayer2CardsInHand(List<Card> cards) {
    player2.cardsInHand.clear();
    player2.cardsInHand.addAll(cards);
  }
  void setPlayer3CardsInHand(List<Card> cards) {
    player3.cardsInHand.clear();
    player3.cardsInHand.addAll(cards);
  }
  void setPlayer4CardsInHand(List<Card> cards) {
    player4.cardsInHand.clear();
    player4.cardsInHand.addAll(cards);
  }
}
