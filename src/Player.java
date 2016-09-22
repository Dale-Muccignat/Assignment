import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Dale Muccignat on 21/09/2016.
 * Player object class
 */
abstract class Player {
    private static final int NO_CARDS_IN_HAND = 8;
    private String name;
    private int playerNo;
    private Boolean dealer = false,pass = false;
    private ArrayList<Card> cardsHand;

    Player() {}
    Player(String name,int playerNo) {
        this.name = name;
        this.playerNo = playerNo;
        System.out.println(name + playerNo);
    }

    private void setCards(ArrayList<Card> cards) {
        this.cardsHand = cards;
    }

    public abstract void runTurn();

    public void displayHand() {
        System.out.println(name);
        for (Card card : cardsHand) {
            System.out.println(card.getTitle());
        }
    }

    public void initializeHand() {
        ArrayList<Card> cards = Deck.dealCards(NO_CARDS_IN_HAND);               //get 8 cards
        setCards(cards);
        displayHand();
    }

    public Boolean getPass() {
        return pass;
    }

    public void setPass(Boolean pass) {
        this.pass = pass;
    }

    public int getPlayerNo() {
        return playerNo;
    }

    public void setPlayerNo(int playerNo) {
        this.playerNo = playerNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDealer(Boolean dealer) {
        this.dealer = dealer;
    }

    public Boolean getDealer() {
        return dealer;
    }

    /* JOptionPane methods to reduce complexity */

    static void displayMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    static Boolean askConfirmation(String message) {
        return JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, message);
    }

    static String askInput(String message) {
        return JOptionPane.showInputDialog(null, message);
    }

}
