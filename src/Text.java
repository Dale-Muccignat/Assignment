import java.nio.file.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by mirage_neos on 22/09/2016.
 * reads file
 */
public class Text {
    public static void main(String[] args) {
        int count=0;
        String cardType,chemistry,classification,cleavage,crystalAbundance,crystalSystem,economicValue,fileName,hardness,imageName,occurrence,specificGravity,title;
        ArrayList<Card> cards = new ArrayList<>();
        Path filePath = Paths.get("D:\\USB\\2P2\\CP2406\\Prac7\\Assignment\\dataPlay.txt");
        InputStream input = null;
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
                Card card = cards.get(x);
                card.display();
            }
            input.close();
        }
        catch (IOException e)
        {
            System.out.println(e);
        }

        //return cards;
    }
}
