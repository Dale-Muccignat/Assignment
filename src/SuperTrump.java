import javax.swing.*;

/**
 * Created by Dale Muccignat on 21/09/2016.
 * Super Trump (Game Object class)
 */


public class SuperTrump {
    private static Player[] players;
    private  int playersNo;
    public SuperTrump() {
        //displayWelcome();
        //displayHelp(); // TODO: Move these to main
        askPlayerNo();
        createPlayers();
    }

    private void createPlayers() {
        players = new Player[playersNo];
        //int x=1;
        for (int x=0; x < playersNo; x++) {                                   // Set player number
            players[x] = new Player();
            players[x].setPlayerNo(x);                                         // And name
            players[x].setName(askInput("Player " + (x+1) + "\nPlease Enter your" +
                    " name: "));
        }

    }

    private void askPlayerNo() {
        int playersNo = 0;
        Boolean confirm=false;
        while (3 > playersNo || playersNo > 5 || !confirm) {                   // Error checks the users
            String input = askInput("How many players are playing? " +         // input
                    "\n'Note: Must be between 3 and 5'");
            playersNo = Integer.parseInt(input);
            if (3 > playersNo || playersNo > 5) {
                displayMessage("Error: \nNumber must be between 3 and 5");
            }

            confirm = askConfirmation("You have indicated that there are " +   // Confirm selection
                    playersNo + " players.\nIs this correct?");
        }
        setPlayersNo(playersNo);
    }

    private static void displayHelp() {
        int selection;
        Boolean option;
        option = askConfirmation("Would you like to read how to play?");
        // TODO: Show how to play screen
    }

    private static void displayWelcome() {
        JOptionPane.showMessageDialog(null,"Welcome to SuperTrump!\n");
    }

    /* Getter/Setters */
    public int getPlayersNo() {
        return playersNo;
    }

    private void setPlayersNo(int playersNo) {
        this.playersNo = playersNo;
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
