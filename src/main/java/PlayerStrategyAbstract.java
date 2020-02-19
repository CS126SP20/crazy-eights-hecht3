import student.crazyeights.Card;
import student.crazyeights.PlayerStrategy;
import student.crazyeights.PlayerTurn;

import java.util.ArrayList;
import java.util.List;

public abstract class PlayerStrategyAbstract implements PlayerStrategy {
  static final int NUM_PLAYERS = 4;

  Integer playerSelfId;
  Integer playerBeforeId;
  Integer playerAfterId;
  Integer playerAcrossId;
  List<Card> cardsInHand;
  List<Card> playerBeforePlayedCards;
  List<Card> playerAfterPlayedCards;
  List<Card> playerAcrossPlayedCards;
  boolean playerBeforeDrewCard;
  boolean playerAfterDrewCard;
  boolean playerAcrossDrewCard;
  Card.Suit playerBeforeDeclaredSuit;
  Card.Suit playerAfterDeclaredSuit;
  Card.Suit playerAcrossDeclaredSuit;
  Card topCard;
  Card.Suit idealSuit;
  Card.Rank idealRank;

  /**
   * Gives the player their assigned id, as well as a list of the opponents' assigned ids.
   *
   * <p>This method will be called by the game engine once at the very beginning (before any games
   * are started), to allow the player to set up any initial state.
   *
   * @param playerId The id for this player, assigned by the game engine
   * @param opponentIds A list of ids for this player's opponents
   */
  public void init(int playerId, List<Integer> opponentIds) {
    for (Integer oppId : opponentIds) {
      if (oppId == playerId + 1 || oppId == playerId - NUM_PLAYERS + 1) {
        playerAfterId = oppId;
      } else if (oppId == playerId - 1 || oppId == NUM_PLAYERS - playerId + 1) {
        playerBeforeId = oppId;
      } else {
        playerAcrossId = oppId;
      }
    }
    playerSelfId = playerId;
    playerBeforePlayedCards = new ArrayList<>();
    playerAfterPlayedCards = new ArrayList<>();
    playerAcrossPlayedCards = new ArrayList<>();
  }

  /**
   * Called once at the beginning of o game to deal the player their initial cards.
   *
   * @param cards The initial list of cards dealt to this player
   */
  public void receiveInitialCards(List<Card> cards) {
    cardsInHand = new ArrayList<>(cards);
  }

  /**
   * Called to ask whether the player wants to draw this turn. Gives this player the top card of the
   * discard pile at the beginning of their turn, as well as an optional suit for the pile in case a
   * "8" was played, and the suit was changed.
   *
   * <p>By having this return true, the game engine will then call receiveCard() for this player.
   * Otherwise, playCard() will be called.
   *
   * @param topPileCard The card currently at the top of the pile
   * @param changedSuit The suit that the pile was changed to as the result of an "8" being played.
   *     Will be null if no "8" was played.
   * @return whether or not the player wants to draw
   */
  public abstract boolean shouldDrawCard(Card topPileCard, Card.Suit changedSuit);

  /**
   * Called when this player has chosen to draw a card from the deck.
   *
   * @param drawnCard The card that this player has drawn
   */
  public void receiveCard(Card drawnCard) {
    cardsInHand.add(drawnCard);
  }

  /**
   * Called when this player is ready to play a card (will not be called if this player drew on
   * their turn).
   *
   * <p>This will end this player's turn.
   *
   * @return The card this player wishes to put on top of the pile
   */
  public abstract Card playCard();

  /**
   * Called if this player decided to play a "8" card to ask the player what suit they would like to
   * declare.
   *
   * <p>This player should then return the Card.Suit enum that it wishes to set for the discard
   * pile.
   */
  public abstract Card.Suit declareSuit();

  /**
   * Called at the very beginning of this player's turn to give it context of what its opponents
   * chose to do on each of their turns.
   *
   * @param opponentActions A list of what the opponents did on each of their turns
   */
  public void processOpponentActions(List<PlayerTurn> opponentActions) {
    for (PlayerTurn turn : opponentActions) {
      if (playerAfterId == null) {
        break;
      }
      if ((turn.playerId == playerBeforeId) && turn.drewACard) {
        playerBeforeDrewCard = true;
      } else if (turn.playerId == playerBeforeId) {
        playerBeforePlayedCards.add(turn.playedCard);
        if (turn.declaredSuit != null) {
          playerBeforeDeclaredSuit = turn.declaredSuit;
        }
      }
      if ((turn.playerId == playerAfterId) && turn.drewACard) {
        playerAfterDrewCard = true;
      } else if (turn.playerId == playerAfterId) {
        playerAfterPlayedCards.add(turn.playedCard);
        if (turn.declaredSuit != null) {
          playerAfterDeclaredSuit = turn.declaredSuit;
        }
      }

      if ((turn.playerId == playerAcrossId) && turn.drewACard) {
        playerAcrossDrewCard = true;
      } else if (turn.playerId == playerAcrossId) {
        playerAcrossPlayedCards.add(turn.playedCard);
        if (turn.declaredSuit != null) {
          playerAcrossDeclaredSuit = turn.declaredSuit;
        }
      }
    }
  }

  List<Card> getPlayerBeforePlayedCards() {
    return playerBeforePlayedCards;
  }

  List<Card> getPlayerAfterPlayedCards() {
    return playerAfterPlayedCards;
  }

  List<Card> getPlayerAcrossPlayedCards() {
    return playerAcrossPlayedCards;
  }

  boolean getPlayerBeforeDrewCard() {
    return playerBeforeDrewCard;
  }

  boolean getPlayerAfterDrewCard() {
    return playerAfterDrewCard;
  }

  boolean getPlayerAcrossDrewCard() {
    return playerAcrossDrewCard;
  }

  Card.Suit getPlayerBeforeDeclaredSuit() {
    return playerBeforeDeclaredSuit;
  }

  Card.Suit getPlayerAfterDeclaredSuit() {
    return playerAfterDeclaredSuit;
  }

  Card.Suit getPlayerAcrossDeclaredSuit() {
    return playerAcrossDeclaredSuit;
  }

  /** Called before a game begins, to allow for resetting any state between games. */
  public void reset() {
    playerAfterId = null;
    playerBeforeId = null;
    playerAcrossId = null;
    playerSelfId = null;
    Card topCard = null;
    Card.Suit idealSuit = null;
    Card.Rank idealRank = null;
    playerBeforeDeclaredSuit = null;
    playerAfterDeclaredSuit = null;
    playerAcrossDeclaredSuit = null;
    cardsInHand = new ArrayList<>();
  }
}
