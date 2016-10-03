import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Dale Muccignat on 21/09/2016.
 * Player object class
 */

abstract class Player {

    String name;
    private int playerNo;
    private Boolean pass = false,won=false;
    private ArrayList<Card> cardsHand;

    Player() {}
    Player(String name,int playerNo) {
        this.name = name;
        this.playerNo = playerNo;
    }

    public abstract String runTurn(Category category, Deck field);

    public String displayHand() {
        String hand = "";
        for (int x = 0; x < cardsHand.size(); x++) {
            hand += "\n" + cardsHand.get(x).display(x + 2);
        }
        return hand;
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

    public Boolean getWon() {
        return won;
    }

    public void setWon(Boolean won) {
        this.won = won;
    }

    /* Scanner methods to reduce complexity */

    public static void displayMessage(String message) {
        System.out.println(message);
    }

    public static String askInput(String message)
    {
        System.out.println(message);
        Scanner inputDevice = new Scanner(System.in);
        return inputDevice.next();
    }

    public abstract Category askCategory();
}
