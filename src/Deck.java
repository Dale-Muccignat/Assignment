
import xmlwise.Plist;
import xmlwise.XmlParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/**
 * Created by Dale on 21/09/2016.
 * Deck Object
 */
public class Deck {
    private static final int INIT_NO_CARDS = 60;
    private static ArrayList<Card> cards;
    public Deck() {
        //D:\USB\2P2\CP2406\Prac7\Assignment\MstCards_151021.plist
        cards = new ArrayList<>();
        for (int x = 0; x < INIT_NO_CARDS; x++) {
             cards.add(new Card("A","B","C","D",x,1.0));
        }
        //todo read cards from file
    }


    static ArrayList<Card> dealCards(int noCardsInHand) {
        ArrayList<Card> hand = new ArrayList<>();
        for (int i = 0; i < noCardsInHand; i++) {
            Random rand = new Random();
            int randomNum = rand.nextInt(cards.size());
            Card card = cards.remove(randomNum);
            hand.add(card);
        }
    return hand;
    }
}
