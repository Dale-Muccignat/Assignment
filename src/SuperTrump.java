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
//        String input = askInput("Welcome to SUPERTRUMPP!!!!\nMenu: \n(1) New Game\n(2) How To Play\n(3) Quit");
        String input = "1";
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
        int playersNo;
        String name = "";
        while (name.isEmpty()) {
//            name = askInput("Please input your name:");                         //Gets userName
            name = "Dale";
        }
        playersNo = askPlayersNo();
        TrumpGame newGame = new TrumpGame(name, playersNo);                     //Create game
        newGame.startGame();
    }

    private static int askPlayersNo() {
        Boolean confirm=false,correct=false;
        int playersNo=0;
        while (!confirm) {
        while (3 > playersNo || playersNo > 5 || !correct) {                // Error checks the users input
                try {
                    correct = false;
                    String input = askInput("How many players are playing? " +
                            "\n'Note: Must be between 3 and 5'");
                    playersNo = Integer.parseInt(input);
                    correct = true;
                    if (3 > playersNo || playersNo > 5) {
                        displayMessage("Error: \nNumber must be between 3 and 5");
                    }
                } catch (NumberFormatException e) {
                    displayMessage("Error, please input a number.");
                }
            }
            confirm = askConfirmation("You have indicated that there are " +    // Confirm selection
                    playersNo + " players.\nIs this correct?");
        }
        return playersNo;
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
