import javax.swing.*;
import java.util.Random;

/**
 * Created by Dale Muccignat on 21/09/2016.
 * Super Trump (Game Object class)
 */

class TrumpGame {
    private static Player[] players;
    private  int playersNo;
    private String userName;
    TrumpGame(String name, int playersNo) {
        this.playersNo = playersNo;
        this.userName = name;
    }

    private void createDeck() {

    }

    public void createPlayers() {
        Random rand = new Random();
        int randomNum = rand.nextInt(playersNo + 1);                            //Randomly assign the user to a player no
        players = new Player[playersNo];                                        //Dealer is always the last player
        players[randomNum] = new User(userName,randomNum);
        for (int x=0; x<playersNo; x++) {                                       //Assign the rest as Ai
            if (x != randomNum) {
                players[x] = new Ai("Player " + x,x);
            }
        }

    }

    //todo finish
    public void startRound() {
        Boolean roundWon = false;
        do {
            for (int x=0; x < playersNo; x++) {                                 //interate through players
                if (!players[x].getPass()) {
                    players[x].runTurn();
                }
            }
            //todo round class
        } while (!roundWon);
    }

    public void dealCards() {
        //todo deal cards
        for (Player player : players) {
            player.initializeHand();
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
