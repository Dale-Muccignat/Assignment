import javax.swing.*;

/**
 * Created by Dale on 21/09/2016.
 *
 */
public class SuperTrump {
    public static void main(String[] args) {
        displayMenu();
    }

    private static void displayMenu() {
        String input = askInput("Welcome to Super-Trump!\nMenu: \n(1) New Game\n(2) How To Play\n(3) Quit");
//        String input = "1";
        switch (input) {
            case "1": startGame();
                break;
            case "2": displayHelp();
                break;
            case "3":
                break;
            default: displayMessage("Invalid selection");
                displayMenu();
                break;
        }
    }

    private static void startGame() {
        TrumpGame newGame = new TrumpGame();                     //Create game
        newGame.startGame();
    }


    private static void displayHelp() {
        // TODO: Show how to play screen
        displayMenu();                                                          //Returns to menu
    }

    /* JOptionPane methods to reduce complexity */

    private static void displayMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    private static Boolean askConfirmation(String message) {
        return JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, message);
    }

    private static String askInput(String message) {
        return JOptionPane.showInputDialog(null, message);
    }
}
