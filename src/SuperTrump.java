import javax.swing.*;
import java.util.Scanner;

/**
 * Created by Dale on 21/09/2016.
 *
 */
public class SuperTrump {
    public static void main(String[] args) {
        startGame();
    }



    private static void startGame() {
        TrumpGame newGame = new TrumpGame();                     //Create game
        newGame.displayMenu();
    }




    /* JOptionPane methods to reduce complexity */

    private static void displayMessage(String message) {
        System.out.println(message);
    }

    private static String askInput(String message)
    {
        System.out.println(message);
        Scanner inputDevice = new Scanner(System.in);
        return inputDevice.next();
    }
}
