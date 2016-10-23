import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Dale Muccignat on 21/09/2016.
 * Player object class
 */

abstract class Player {

    String name;
    private Boolean pass = false,won=false;
    private ArrayList<Card> cardsHand;

    Player() {}
    Player(String name) {
        this.name = name;
    }

    public abstract String runTurn(Category category, Deck field);

    String displayHand() {
        String hand = "";
        for (int x = 0; x < cardsHand.size(); x++) {
            hand += "\n" + cardsHand.get(x).display(x + 2);
        }
        return hand;
    }

    void removeCard(Card card) {
        this.cardsHand.remove(card);
    }

    void setCardsHand(ArrayList<Card> cardsHand) {
        this.cardsHand = cardsHand;
    }

    ArrayList<Card> getCardsHand() {
        return cardsHand;
    }

    Boolean getPass() {
        return pass;
    }

    void setPass(Boolean pass) {
        this.pass = pass;
    }

    String getName() {
        return name;
    }

    Boolean getWon() {
        return won;
    }

    void setWon(Boolean won) {
        this.won = won;
    }

    /* Scanner methods to reduce complexity */

    static void displayMessage(String message) {
        System.out.println(message);
    }

    static String askInput(String message)
    {
        System.out.println(message);
        Scanner inputDevice = new Scanner(System.in);
        return inputDevice.next();
    }

    public abstract Category askCategory();
}
