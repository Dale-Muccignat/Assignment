import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Dale Muccignat on 21/09/2016.
 * Player object class
 */

abstract class Player {
    private static final int NO_CARDS_IN_HAND = 8;
    String name;
    private int playerNo;
    private Boolean pass = false;
    private ArrayList<Card> cardsHand;
    enum Category {HARD, SPEC, CLEA, CRUS, ECON};

    Player() {}
    Player(String name,int playerNo) {
        this.name = name;
        this.playerNo = playerNo;
        System.out.println(name + playerNo);
    }

    private void setCards(ArrayList<Card> cards) {
        this.cardsHand = cards;
    }

    public abstract String runTurn();

    public String displayHand() {
        String hand = "";
        for (int x=0; x<cardsHand.size(); x++) {
            hand += "\n" + cardsHand.get(x).display(x+2);
        }
        return hand;
    }

    public void initializeHand() {
        ArrayList<Card> cards = Deck.dealCards(NO_CARDS_IN_HAND);               //get 8 cards
        setCards(cards);
    }

    public void removeCard(int index) {
        this.cardsHand.remove(index);
    }

    public void setCardsHand(ArrayList<Card> cardsHand) {
        this.cardsHand = cardsHand;
    }

    public ArrayList<Card> getCardsHand() {
        return cardsHand;
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

    public abstract Category askCategory();
}
