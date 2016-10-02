import java.util.Random;

/**
 * Created by mirage_neos on 22/09/2016.
 * Ai class, extends player
 */
public class Ai extends Player {
    public Ai() {
    }

    public Ai(String name,int playerNo) {
        super(name,playerNo);
    }

    @Override
    public String runTurn(Category category, Deck field) {
//        displayMessage(name + "'s Turn");
        Random rand = new Random();
        return Integer.toString(rand.nextInt(getCardsHand().size())+1);
//        return "1";
    }

    @Override
    public Category askCategory() {
        Random rand = new Random();
        int input = rand.nextInt(4)+1;
        switch (input) {
            case 1: return Category.HARDNESS;
            case 2: return Category.SPECIFICGRAVITY;
            case 3: return Category.CLEAVAGE;
            case 4: return Category.CRUSTALABUNDANCE;
            case 5: return Category.ECONOMICVALUE;
            default: askCategory();
                return null;
        }
    }
}
