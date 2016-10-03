
import xmlwise.Plist;
import xmlwise.XmlParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/**
 * Created by Dale on 21/09/2016.
 * Deck Object
 */
public class Deck {
    private static final int INIT_NO_CARDS = 60;
    private ArrayList<Card> cards;
    public Deck() {
        cards = new ArrayList<>();
    }

    void buildDeck() {
        String subtitle,chemistry,classification,cleavage,crustalAbundance,crystalSystem,economicValue,fileName,hardness,imageName,occurrence,specificGravity,title;

//        Path filePath = Paths.get("D:\\USB\\2P2\\CP2406\\Prac7\\Assignment\\dataPlay.txt");
        Path filePath = Paths.get("F:\\USB\\2P2\\CP2406\\Prac7\\Assignment\\Resources\\dataPlay.txt");
        InputStream input;
        try
        {
            input = Files.newInputStream(filePath);
            BufferedReader reader = new
                    BufferedReader(new InputStreamReader(input));
            for (int x=0; x < 54 ; x++) {                                       //Read cards from file
                reader.readLine();
                chemistry = reader.readLine();
                classification = reader.readLine();
                cleavage = reader.readLine();
                crustalAbundance = reader.readLine();
                crystalSystem = reader.readLine();
                economicValue = reader.readLine();
                fileName = reader.readLine();
                hardness = reader.readLine();
                imageName = reader.readLine();
                occurrence = reader.readLine();
                specificGravity = reader.readLine();
                title = reader.readLine();
                cards.add(new PlayCard(x,fileName,imageName,title,chemistry,classification,cleavage,crustalAbundance,crystalSystem,economicValue,hardness,occurrence,specificGravity));
            }
            for (int x = 54; x < 60; x++) {
                reader.readLine();
                fileName = reader.readLine();
                imageName = reader.readLine();
                subtitle = reader.readLine();
                title = reader.readLine();
                cards.add(new TrumpCard(x,fileName,imageName,title,subtitle));
            }
            input.close();
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
    }


    ArrayList<Card> dealCards(int noCardsDealt) {
        ArrayList<Card> hand = new ArrayList<>();
        for (int i = 0; i < noCardsDealt; i++) {
            Random rand = new Random();
            int randomNum = rand.nextInt(cards.size());
            Card card = cards.remove(randomNum);
            hand.add(card);
        }
    return hand;
    }

    public String display() {
        String ret = "";
        int x=0;
        for (Card card : cards) {
            ++x;
            ret += "\n" + card.display(x);
        }
        return ret;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void addCards(Deck field) {
        cards.addAll(field.getCards());
    }
}
