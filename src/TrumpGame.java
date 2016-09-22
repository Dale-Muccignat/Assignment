import javax.swing.*;

/**
 * Created by Dale Muccignat on 21/09/2016.
 * Super Trump (Game Object class)
 */


public class TrumpGame {
    private static Player[] players;
    private  int playersNo;
    private String name;
    public TrumpGame(String name, int playersNo) {
        this.playersNo = playersNo;
        this.name = name;
        createPlayers();
        //createDeck();
    }

    private void createDeck() {

    }

    private void createPlayers() {
        players = new Player[playersNo];
        //todo set up player in random slot
        //todo set up ai in random slot
        for (int x=0; x < playersNo; x++) {                                     // Set player number
            players[x]  = new Ai();                                          // And name
            players[x].setPlayerNo(x);
        }

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
