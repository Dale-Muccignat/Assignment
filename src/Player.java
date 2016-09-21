/**
 * Created by Dale Muccignat on 21/09/2016.
 * Player object class
 */
public class Player {
    private String name="hi";
    private int playerNo;
    public Player() {}
    public Player(String name) {
        this.name = name;
    }

    public int getPlayerNo() {
        return playerNo;
    }

    public void setPlayerNo(int playerNo) {
        this.playerNo = playerNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
