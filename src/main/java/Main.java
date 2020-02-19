import student.crazyeights.PlayerStrategy;

import java.util.*;

public class Main {
  static final int WINNING_SCORE = 200;
  public static void main(String[] args) {
    PlayerStrategy player1 = new PlayerStrategy1();
    PlayerStrategy player2 = new PlayerStrategy1();
    PlayerStrategy player3 = new PlayerStrategy2();
    PlayerStrategy player4 = new PlayerStrategy2();
    int player1Score = 0;
    int player2Score = 0;
    int player3Score = 0;
    int player4Score = 0;
    ArrayList<PlayerStrategy> strategies =
        new ArrayList<>(Arrays.asList(player1, player2, player3, player4));
    for (PlayerStrategy player : strategies) {
      player.reset();
    }
    while (player1Score < WINNING_SCORE
        && player2Score < WINNING_SCORE
        && player3Score < WINNING_SCORE
        && player4Score < WINNING_SCORE) {
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
    HashMap<PlayerStrategy, Integer> scores = new HashMap<>();
    scores.put(player1, player1Score);
    scores.put(player2, player2Score);
    scores.put(player3, player3Score);
    scores.put(player4, player4Score);
    Integer maxScore = Collections.max(scores.values());
    for (PlayerStrategy player : scores.keySet()) {
      if (scores.get(player).equals(maxScore)) {
        System.out.println(player.toString().substring(0, player.toString().indexOf("@"))
                + " won with a score of " + maxScore);
      }
    }
  }
}
