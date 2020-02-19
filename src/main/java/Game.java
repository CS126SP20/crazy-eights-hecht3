import student.crazyeights.*;

import java.util.*;

public class Game {

  static final int DEAL_SIZE = 5;
  static final int PLAYER_1_ID = 1;
  static final int PLAYER_2_ID = 2;
  static final int PLAYER_3_ID = 3;
  static final int PLAYER_4_ID = 4;
  static final int NUM_PLAYERS = 4;

  Random rand = new Random();
  public List<Card> deck;
  public List<Card> draw;
  public List<Card> discard;
  public Card.Suit lastDeclaredSuit;
  public boolean isEndOfGame;
  public boolean cheater;
  public boolean scoreCalculated;
  private PlayerStrategyGameState player1;
  private PlayerStrategyGameState player2;
  private PlayerStrategyGameState player3;
  private PlayerStrategyGameState player4;
  List<PlayerStrategyGameState> playerList = new ArrayList<>();

  public Game(
      PlayerStrategy player1Init,
      PlayerStrategy player2Init,
      PlayerStrategy player3Init,
      PlayerStrategy player4Init) {
    deck = Card.getDeck();
    Collections.shuffle(deck, rand);
    this.player1 = new PlayerStrategyGameState(player1Init, PLAYER_1_ID);
    this.player2 = new PlayerStrategyGameState(player2Init, PLAYER_2_ID);
    this.player3 = new PlayerStrategyGameState(player3Init, PLAYER_3_ID);
    this.player4 = new PlayerStrategyGameState(player4Init, PLAYER_4_ID);
    playerList.add(player1);
    playerList.add(player2);
    playerList.add(player3);
    playerList.add(player4);
    for (PlayerStrategyGameState player : playerList) {
      List<Integer> otherPlayerIds = new ArrayList<>();
      for (Integer i = 1; i <= NUM_PLAYERS; i++) {
        if (i != player.selfId) {
          otherPlayerIds.add(i);
        }
      }
      player.getPlayerStrategy().init(player.selfId, otherPlayerIds);
      player.cardsInHand = deal(player.getPlayerStrategy());
    }
    draw = deck;
    while (draw.get(0).getRank().equals(Card.Rank.EIGHT)) {
      Collections.shuffle(draw, rand);
    }
    discard = new ArrayList<>();
    discard.add(0, draw.get(0));
    draw.remove(draw.get(0));
  }

  public void play() {
    PlayerStrategyGameState winner = null;
    while (winner == null && !cheater && !isEndOfGame) {
      winner = performRound();
    }
    if (cheater) {
      try {
        throw new Exception("A player cheated!");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    calculateScore(winner);
    scoreCalculated = true;
    System.out.println(player1.score);
    System.out.println(player2.score);
    System.out.println(player3.score);
    System.out.println(player4.score);
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
    player.receiveInitialCards(playerHand);
    return playerHand;
  }

  /*

  ////////// The scenario that a player wins and the draw pile is also empty is impossible because
  ////////// in order for the draw pile size to change, a player must draw, and if they had to draw
  ////////// then they could not play the last card in their hand.

     */
  // Returns the player that won
  private PlayerStrategyGameState performRound() {
    List<PlayerTurn> actions = new ArrayList<>();
    for (PlayerStrategyGameState player : playerList) {
      PlayerTurn turn = getPlayerTurn(player);
      actions.add(turn);
    }
    for (PlayerStrategyGameState player : playerList) {
      if (!isEndOfGame) {
        player.getPlayerStrategy().processOpponentActions(actions);
      }
    }
    PlayerStrategyGameState possibleWinner;
    possibleWinner = checkEndOfGame();
    if (possibleWinner == null) {
      return null;
    } else {
      return possibleWinner;
    }
  }

  private PlayerTurn getPlayerTurn(PlayerStrategyGameState player) {
    PlayerTurn turn = new PlayerTurn();
    turn.playerId = player.selfId;
    if (lastDeclaredSuit == null) {
      lastDeclaredSuit = discard.get(0).getSuit();
    }
    if (draw.size() == 0 || player.cardsInHand.size() == 0) {
      checkEndOfGame();
      return null;
    }
    if (player.getPlayerStrategy().shouldDrawCard(discard.get(0), lastDeclaredSuit)) {
      player.getPlayerStrategy().receiveCard(draw.get(0));
      player.cardsInHand.add(draw.get(0));
      turn.drewACard = true;
      draw.remove(draw.get(0));
    } else {
      turn.drewACard = false;
      Card cardPlayed = player.getPlayerStrategy().playCard();
      if (!isValidMove(cardPlayed, player)) {
        cheater = true;
        // A try-catch block in main should find if there is a cheater if we organize our functions
        // in this manner.
        return null;
      }
      turn.playedCard = cardPlayed;
      player.cardsInHand.remove(cardPlayed);
      if (cardPlayed.getRank() == Card.Rank.EIGHT) {
        turn.declaredSuit = player.getPlayerStrategy().declareSuit();
      }
    }
    return turn;
  }

  boolean isValidMove(Card cardPlayed, PlayerStrategyGameState player) {
    if ((discard.get(0).getSuit().equals(cardPlayed.getSuit())
            || discard.get(0).getRank().equals(cardPlayed.getRank())
            || cardPlayed.getRank().equals(Card.Rank.EIGHT))
        && player.cardsInHand.contains(cardPlayed)) {
      return true;
    } else {
      System.out.println("A player cheated! Player " + player.selfId + " broke the rules.");
      return false;
    }
  }

  void calculateScore(PlayerStrategyGameState winner) {
    if (winner == null) {
      for (PlayerStrategyGameState player : playerList) {
        for (PlayerStrategyGameState opposingPlayer : playerList) {
          if (!opposingPlayer.equals(player)) {
            for (Card card : opposingPlayer.cardsInHand) {
              player.score += card.getPointValue();
            }
          }
        }
      }
    } else {
      for (PlayerStrategyGameState player : playerList) {
        if (!player.equals(winner)) {
          for (Card card : player.cardsInHand) {
            winner.score += card.getPointValue();
          }
        }
      }
    }
  }

  PlayerStrategyGameState checkEndOfGame() {
    if (draw.size() == 0) {
      isEndOfGame = true;
      return null;
    } else {
      for (PlayerStrategyGameState player : playerList) {
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

  List<PlayerStrategyGameState> getPlayerList() {
    return playerList;
  }

  int getPlayer1Score() {
    return player1.score;
  }

  int getPlayer2Score() {
    return player2.score;
  }

  int getPlayer3Score() {
    return player3.score;
  }

  int getPlayer4Score() {
    return player4.score;
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
