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
    public void runTurn() {
        //todo menu choices for turn
        askInput("What would you like to do? \n(1) ");
    }
}