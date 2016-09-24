import javax.swing.*;
import java.util.Random;

/**
 * Created by Dale Muccignat on 21/09/2016.
 * Super Trump (Game Object class)
 */

class TrumpGame {
    private static Player[] players;
    private Deck deck;
    private  int playersNo;
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

    void startRound() {
        Boolean roundWon = false;
        while (!roundWon) {
            for (int x=0; x < playersNo; x++) {                                 //interate through players
                if (!players[x].getPass()) {
                    players[x].runTurn();                                       //todo return selection such as card/add to field if card
                }
            }
            roundWon = true;
        }
    }

    private void dealCards() {
        for (Player player : players) {
            player.initializeHand();
        }
//        for (int x=0; x < deck.getCards().size();x++) {
//            deck.getCards().get(x).display(x);
//        }
    }

    /* Getter/Setters */

    public int getPlayersNo() {
        return playersNo;
    }

    private void setPlayersNo(int playersNo) {
        this.playersNo = playersNo;
    }

}
