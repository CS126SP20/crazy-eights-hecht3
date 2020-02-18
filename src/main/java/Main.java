import student.crazyeights.PlayerStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class Main {
    public static void main(String[] args) {
        PlayerStrategy player1 = new PlayerStrategy1();
        PlayerStrategy player2 = new PlayerStrategy2();
        PlayerStrategy player3 = new PlayerStrategy3();
        PlayerStrategy player4 = new PlayerStrategy1();
        ArrayList<PlayerStrategy> strategies =
                new ArrayList<>(Arrays.asList(player1, player2, player3, player4));
        Game game = new Game(player1, player2, player3, player4);
        while(!game.scoreCalculated) {
            try {
                game.play();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}