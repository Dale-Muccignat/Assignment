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
        initilizePlayers();
    }

    private static void initilizePlayers() {
        int playerNo=0;
        while (3 > playerNo || playerNo > 5) { // Error checks the users input
            String input = getInput("How many players are playing? \n'Note: Must be between 3 and 5'");
            playerNo = Integer.parseInt(input);
            if (3 >= playerNo || playerNo >= 5) {
                displayMessege("Error: \nNumber must be between 3 and 5");
            }
        }
        getConfirmation("You have indicated that there are " + playerNo + " players.\nIs this correct?");
        //Player[] players = new Player[playerNo];
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
    private static void displayMessege(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    private static Boolean getConfirmation(String message) {
        return JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, "Would you like to read how to play?");
    }

    public static String getInput(String messege) {
        return JOptionPane.showInputDialog(null, messege);
    }
}
