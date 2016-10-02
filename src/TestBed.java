import java.util.ArrayList;

/**
 * Created by Dale on 1/10/2016.
 */
public class TestBed {
    public static void main(String[] args) {
        Deck deck = new Deck();
        deck.buildDeck();
        System.out.println(deck.display());
        deck.getCards().clear();
        System.out.println("DECK: " + deck.display());

    }
}
