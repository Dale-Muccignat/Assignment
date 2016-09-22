/**
 * Created by Dale Muccignat on 21/09/2016.
 * Player object class
 */
public abstract class Player {
    private String name;
    private int playerNo;
    private Boolean dealer = false,pass = false;
    public Player() {}
    public Player(String name,int playerNo) {
        this.name = name;
        this.playerNo = playerNo;
        System.out.println(name + playerNo);
    }

    public Boolean getPass() {
        return pass;
    }

    public void setPass(Boolean pass) {
        this.pass = pass;
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

    public void setDealer(Boolean dealer) {
        this.dealer = dealer;
    }

    public Boolean getDealer() {
        return dealer;
    }

    public abstract void runTurn();
}
