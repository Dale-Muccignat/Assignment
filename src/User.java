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
}
