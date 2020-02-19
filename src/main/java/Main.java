import student.crazyeights.PlayerStrategy;

import java.util.*;

public class Main {
  static final int WINNING_SCORE = 200;

  /**
   * The main method for the program. Orchestrates a tournament which ends when a player reaches the
   * winning score threshold, which is 200 as per the assignment documentation. Catches an exception
   * from play() which is thrown in the event that a player cheats.
   *
   * @param args an array of commandline arguments standard in an java main.
   */
  public static void main(String[] args) {

    // Initialize PlayerStrategies for a game
    PlayerStrategy player1 = new PlayerStrategy1();
    PlayerStrategy player2 = new PlayerStrategy1();
    PlayerStrategy player3 = new PlayerStrategy2();
    PlayerStrategy player4 = new PlayerStrategy2();

    // Keep track of each player's score for the tournament
    int player1Score = 0;
    int player2Score = 0;
    int player3Score = 0;
    int player4Score = 0;
    ArrayList<PlayerStrategy> strategies =
        new ArrayList<>(Arrays.asList(player1, player2, player3, player4));

    // Continue to play games as long as no player has reached a score of over 200
    while (player1Score < WINNING_SCORE
        && player2Score < WINNING_SCORE
        && player3Score < WINNING_SCORE
        && player4Score < WINNING_SCORE) {
      for (PlayerStrategy player : strategies) {
        player.reset();
      }
      Game game = new Game(player1, player2, player3, player4);
      try {
        while (!game.scoreCalculated) {
          game.play();
        }
        player1Score += game.getPlayer1Score();
        player2Score += game.getPlayer2Score();
        player3Score += game.getPlayer3Score();
        player4Score += game.getPlayer4Score();
      } catch (Exception e) {
        System.out.println(e);
      }
    }

    // Award the win to the player with the highest score over 200. A HashMap was used as an
    // efficient way of pairing the scores and the PlayerStrategies
    HashMap<PlayerStrategy, Integer> scores = new HashMap<>();
    scores.put(player1, player1Score);
    scores.put(player2, player2Score);
    scores.put(player3, player3Score);
    scores.put(player4, player4Score);
    Integer maxScore = Collections.max(scores.values());
    for (PlayerStrategy player : scores.keySet()) {
      if (scores.get(player).equals(maxScore)) {
        // The toString method of PlayerStrategy returns the PlayerStrategy and some kind of
        // reference to that Strategy's memory location. I decided to just print the PlayerStrategy
        // using substring. Each Player's score is also printed every game.
        System.out.println(
            player.toString().substring(0, player.toString().indexOf("@"))
                + " won with a score of "
                + maxScore);
      }
    }
  }
}
