
import xmlwise.Plist;
import xmlwise.XmlParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    private static ArrayList<Card> cards;
    public Deck() {
        //D:\USB\2P2\CP2406\Prac7\Assignment\MstCards_151021.plist
        //todo read cards from file
    }

    void buildDeck() {
        String cardType,chemistry,classification,cleavage,crystalAbundance,crystalSystem,economicValue,fileName,hardness,imageName,occurrence,specificGravity,title;
        cards = new ArrayList<>();
        Path filePath = Paths.get("F:\\USB\\2P2\\CP2406\\Prac7\\Assignment\\dataPlay.txt");
        InputStream input;
        try
        {
            input = Files.newInputStream(filePath);
            BufferedReader reader = new
                    BufferedReader(new InputStreamReader(input));
            for (int x=0; x < 54 ; x++) {
                cardType = reader.readLine();
                chemistry = reader.readLine();
                classification = reader.readLine();
                cleavage = reader.readLine();
                crystalAbundance = reader.readLine();
                crystalSystem = reader.readLine();
                economicValue = reader.readLine();
                fileName = reader.readLine();
                hardness = reader.readLine();
                imageName = reader.readLine();
                occurrence = reader.readLine();
                specificGravity = reader.readLine();
                title = reader.readLine();
                cards.add(new Card(cardType,chemistry,classification,cleavage,crystalAbundance,crystalSystem,economicValue,fileName,hardness,imageName,occurrence,specificGravity,title));
            }
            input.close();
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
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
