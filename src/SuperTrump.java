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
        String input = askInput("Menu: \n(1) New Game\n(2) How To Play\n(3) Quit");
        switch (input) {
            case "1": startGame();
                break;
            case "2": displayHelp();
                break;
            case "3":
                break;
            default: displayMessage("Invalid selection");
                break;
        }
    }

    private static void startGame() {
        int playersNo;
        String name = askInput("Please input your name:");                      //Gets userName
        playersNo = askPlayersNo();
        TrumpGame newGame = new TrumpGame(name, playersNo);                     //Create game
        newGame.initialize();
        newGame.startRound();
    }

    private static int askPlayersNo() {
        Boolean confirm=false;
        int playersNo=0;
        while (3 > playersNo || playersNo > 5 || !confirm) {                    // Error checks the users input
            String input = askInput("How many players are playing? " +
                    "\n'Note: Must be between 3 and 5'");
            playersNo = Integer.parseInt(input);
            if (3 > playersNo || playersNo > 5) {
                displayMessage("Error: \nNumber must be between 3 and 5");
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
