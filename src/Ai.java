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
        // play a card with a higher value, if can't, play trump, if can't, pass
        // look for a valid play card to play
        int i = 0;
        //if there is no previous card, just play first card
        if (field.getCards().isEmpty()) {
            return "2";
        } else {
            while (i < (getCardsHand().size())) {
                ++i;
                Card card = getCardsHand().get(i-1);
                if (card instanceof PlayCard) {
                    //if valid play
                    if (((PlayCard) (card)).compareCards(
                            (PlayCard) field.getCards().get((field.getCards().size() - 1)), category)) {
                        // return selected card
                        return Integer.toString(i+1);
                    }
                }
            }
        }
        i=0;
        // look for a trump card to play
        while (i < (getCardsHand().size()))  {
            ++i;
            if (getCardsHand().get(i-1) instanceof TrumpCard)  {
                // put the card into play
                return Integer.toString(i+1);
            }
        }
        // if no card was found to play, player must pass
        return Integer.toString(1);
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
