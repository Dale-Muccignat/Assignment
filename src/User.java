/**
 * Created by mirage_neos on 22/09/2016.
 * user class
 */
public class User extends Player {
    public User() {
    }

    public User(String name,int playerNo) {
        super(name,playerNo);
    }

    @Override
    public String runTurn() {
        String input;
        input = askInput(name + "'s Turn!\nType card number if you would like to play that card or pass: " +
                "\nYour options:\n(1) Pass" + displayHand());
        return input;
    }

    @Override
    public Category askCategory() {
        String message = name + "\nPlease select a Category:\n(1) Hardness\n(2) Specific Gravity\n(3) Cleavage" +
                "\n(4) Crustal Abundance\n(5) Economic Value";
        String input = askInput(message);
        switch (input) {
            case "1": return Category.HARD;
            case "2": return Category.SPEC;
            case "3": return Category.CLEA;
            case "4": return Category.CRUS;
            case "5": return Category.ECON;
            default: displayMessage("Invalid Selection");
                askCategory();
                break;
        }
        return null;
    }
}
