import student.crazyeights.*;

import java.util.*;

public class Game {

  /** The number of cards each player should be dealt */
  static final int DEAL_SIZE = 5;
  /** The ID of each player as a constant */
  static final int PLAYER_1_ID = 1;

  static final int PLAYER_2_ID = 2;
  static final int PLAYER_3_ID = 3;
  static final int PLAYER_4_ID = 4;
  /** The total number of players as a constant */
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

  /**
   * The constructor for the Game class. Takes in four implementations of PlayerStrategies and
   * initializes them for a new game. Also calls on the deal() method to give the players their]
   * initial hands. Also makes the PlayerStrategy instances into PlayerStrategyGameState instances
   * in order to store variables specifically associated with each player. The reason this extra
   * class was created is Game is not supposed to have any access to the internal state of the
   * PlayerStrategies but still needs to store information associated with each PlayerStrategy
   * instance.
   *
   * @param player1Init the PlayerStrategy for player1
   * @param player2Init the PlayerStrategy for player2
   * @param player3Init the PlayerStrategy for player3
   * @param player4Init the PlayerStrategy for player4
   */
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

  /**
   * Deals cards from deck for a given player.
   *
   * @param player the player that the hand is being dealt for
   * @return the hand dealt for a player
   */
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

  /**
   * Continues gameplay until a winner is found or checkEndOfGame has figured out that it is the end
   * of the game. Loops performRound() until isEndOfGame is true. Also throws an exception if a
   * player makes an invalid move i.e. cheating occurs.
   */
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

  /**
   * Manages the helper functions necessary for performing a round of moves.
   *
   * @return the player that has won the game or null if no player has won.
   */
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

  /**
   * Creates the playerTurn object so that the PlayerStrategies can process the last set of moves
   * that occurred. Is called by the performRound() method for each player in playerList.
   *
   * @param player the player that is making a move.
   * @return the PlayerTurn object with the relevant information for this move.
   */
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

    // Check if a player should draw a card and if so, give them the card from the top of draw
    if (player.getPlayerStrategy().shouldDrawCard(discard.get(0), lastDeclaredSuit)) {
      player.getPlayerStrategy().receiveCard(draw.get(0));
      player.cardsInHand.add(draw.get(0));
      turn.drewACard = true;
      draw.remove(draw.get(0));
    } else {
      turn.drewACard = false;
      // Allow player to play their card of choice and then check to make sure that move was valid
      // and that they actually hold the card they played.
      Card cardPlayed = player.getPlayerStrategy().playCard();
      if (!isValidMove(cardPlayed, player)) {
        cheater = true;
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

  /**
   * Checks to see if a player has cheated. If the player has not made a valid move, that is
   * considered cheating and the tournament and game end. An exception is thrown in the case of
   * cheating in the play() method. The scenario that a player wins and the draw pile is empty is
   * impossible because if a player draws that counts as their turn.
   *
   * @param cardPlayed the card that was played
   * @param player the player that played the card
   */
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

  /**
   * Calculates the score of each player. Is called once the game is over. In the case that game is
   * over because the draw pile is empty, this method loops through the playerList and sums up all
   * the point values of the cards that the opposing players have.
   *
   * @param winner the player (if any) that has emptied their hand. Is null if no player has emptied
   *     its hand.
   */
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

  /**
   * Checks to see if the draw pile is empty or any player has emptied their hand, in which case the
   * game is over and scores need to be calculated.
   *
   * @return the player (if any) that emptied their hand. If the game has ended because the draw
   *     pile is empty, this method returns null.
   */
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

  /**
   * The following are methods that were used for testing. They provide no functional purpose to the
   * Game class, main class, or any of the PlayerStrategy implementations.
   */
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
