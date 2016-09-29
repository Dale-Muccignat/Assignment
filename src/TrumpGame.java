import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Dale Muccignat on 21/09/2016.
 * Super Trump (Game Object class)
 */

class TrumpGame {
    private static Player[] players;
    private Deck deck;
    private int playersNo;
    private String userName;
    private Deck field;

    TrumpGame(String name, int playersNo) {
        this.playersNo = playersNo;
        this.userName = name;
    }

    void initialize() {
        createPlayers();
        createDeck();
        dealCards();
    }

    private void createDeck() {
        deck = new Deck();
        deck.buildDeck();
    }

    private void createPlayers() {
        Random rand = new Random();
        int randomNum = rand.nextInt(playersNo);                            //Randomly assign the user to a player no
        players = new Player[playersNo];                                        //Dealer is always the last player
        players[randomNum] = new User(userName,randomNum);
        for (int x=0; x<playersNo; x++) {                                       //Assign the rest as Ai
            if (x != randomNum) {
                players[x] = new Ai("Player " + x,x);
            }
        }
    }

    Player startRound(int catagory) {
        //todo put in catagory
        //todo store who won, perhaps return
        Boolean roundEnd= false,confirm=false;
        field = new Deck();
        Player playerWon = new User();
        while (!roundEnd) {
            int noPlayersPassed=0;
            for (int x=0; x < playersNo; x++) {                                 //interate through players
                confirm = false;
                System.out.println(players[x].getPass() + players[x].getName());
                if (!players[x].getPass()) {
                    playerWon = players[x];                                     //Last player that played is stored
                    while (!confirm) {
                        String input = players[x].runTurn();
                        if (input.equals("1")) {                               // 1 is PASS, the rest are cards.
                            players[x].setPass(true);
                            noPlayersPassed += 1;
                            confirm = true;
                        }
                        for (int i = 2; i < players[x].getCardsHand().size(); i++) {
                            String index = Integer.toString(i);
                            if (input.equals(index)) {
                                Card card = players[x].getCardsHand().get(i - 2); //store selected card
                                players[x].removeCard(i - 2);                     //remove card from hand
                                field.addCard(card);                            //add card to field
                                confirm = true;
                            }
                        }
                    }
                } else {
                    noPlayersPassed += 1;
                }
            }
            if (noPlayersPassed == (playersNo - 1)) {
                roundEnd = true;
                displayMessage(playerWon.getName() + " won the round");
            }
            displayMessage("Field: \n" + field.display());                      //display field
        }
    }

    private void dealCards() {
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
