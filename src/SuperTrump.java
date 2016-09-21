import javax.swing.*;

/**
 * Created by Dale Muccignat on 21/09/2016.
 * Super Trump
 */

// TODO: Set up github

public class SuperTrump {
    public static void main(String[] args) {
        displayWelcome();
        displayHelp();
        initializePlayers();
    }

    private static void initializePlayers() {
        int playersNo = 0;
        Boolean confirm=false;
        while (3 > playersNo || playersNo > 5 || !confirm) { // Error checks the users input
            String input = getInput("How many players are playing? \n'Note: Must be between 3 and 5'");
            playersNo = Integer.parseInt(input);
            if (3 > playersNo || playersNo > 5) {
                displayMessage("Error: \nNumber must be between 3 and 5");
            }
            // Confirm selection
            confirm = getConfirmation("You have indicated that there are " + playersNo + " players.\nIs this correct?");
        }
        // Player[] players = new Player[playersNo];
        // TODO: create player class
    }

    private static void displayHelp() {
        int selection;
        Boolean option;
        option = getConfirmation("Would you like to read how to play?");
        // TODO: Show how to play screen
    }

    private static void displayWelcome() {
        JOptionPane.showMessageDialog(null,"Welcome to SuperTrump!\n");
    }

    /* JOptionPane methods to reduce complexity */
    private static void displayMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    private static Boolean getConfirmation(String message) {
        return JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, message);
    }

    public static String getInput(String messege) {
        return JOptionPane.showInputDialog(null, messege);
    }
}
