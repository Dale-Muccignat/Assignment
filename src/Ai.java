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
        //todo auto turn
        Random rand = new Random();
        return Integer.toString(rand.nextInt(getCardsHand().size())+1);
//        return "1";
    }

    @Override
    public Category askCategory() {
        Random rand = new Random();
        int input = rand.nextInt(4)+1;
        switch (input) {
            case 1: return Category.HARD;
            case 2: return Category.SPEC;
            case 3: return Category.CLEA;
            case 4: return Category.CRUS;
            case 5: return Category.ECON;
            default: askCategory();
                break;
        }
        return null;
    }
}
