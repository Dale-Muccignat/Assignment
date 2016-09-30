import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Dale Muccignat on 21/09/2016.
 * Super Trump (Game Object class)
 */

class TrumpGame {
    private static final int NO_CARDS_IN_HAND = 8;
    private static ArrayList<Player> players,playersWon;
    private Deck deck;
    private int playersNo;
    private String userName;
    private Deck storedCards;
    private String[] economicValueValues = {"trivial", "low", "moderate", "high", "very high", "I'm rich!"},
            crustalAbundanceValues = {"ultratrace", "trace", "low", "moderate", "high", "very high"},
            cleavageValues = {"none", "poor/none", "1 poor", "2 poor", "1 good", "1 good, 1 poor", "2 good",
            "3 good", "1 perfect", "1 perfect, 1 good", "1 perfect, 2 good", "2 perfect, 1 good", "3 perfect",
            "4 perfect", "6 perfect"};

    TrumpGame(String name, int playersNo) {
        this.playersNo = playersNo;
        this.userName = name;
    }

    public void startGame() {
        createPlayers();
        createDeck();
        dealCards();
        //todo check if hands are empty
        while (players.size() != 1) {
            Category category = players.get(0).askCategory();
            for (int x=0; x < players.size(); ++x) {
                Player player = players.get(x);
                player.setPass(false);
                players.set(x,player);
            }
            Player playerWon = startRound(category);
            System.out.println("WON PLAYER: " + playerWon.getName());
            shiftArray(players, players.size() - (players.indexOf(playerWon))); //shift array so player won is the first of the next round
        }
        System.out.println(players.get(0).getName() +" Won");
    }

    private void createDeck() {
        deck = new Deck();
        deck.buildDeck();
    }

    private void createPlayers() {
        Random rand = new Random();
        int randomNum = rand.nextInt(playersNo);                            //Randomly assign the user to a player no
        players = new ArrayList<>();                                        //Dealer is always the last player
        for (int x=0; x < playersNo; x++) {                                       //Assign the rest as Ai
            if (x != randomNum) {
                Player ai = new Ai("Player " + (x+1),x+1);
                players.add(ai);
            } else {
                Player player = new User(userName,randomNum+1);
                players.add(player);
            }
        }
    }

    private Player startRound(Category category) {
        System.out.println("Category: " + category);
        Boolean roundEnd= false,confirm;
        Deck field = new Deck();
        Player playerWonRound = null;
        while (!roundEnd) {
//            System.out.println("Round Number: " + roundNo);
            int noPlayersPassed=0;
            Card lastCard = null;
            for (int x=0; x < playersNo; x++) {                                 //iterate through players
                Player player = players.get(x);
                confirm = false;
                if (!player.getPass() && !player.getWon()) {
                    playerWonRound = player;
                    while (!confirm) {
                        String input = player.runTurn(category,field);
                        if (input.equals("1")) {
                            // 1 is PASS, the rest are cards.
                            player.setPass(true);
                            noPlayersPassed += 1;
                            confirm = true;
                        }
                        for (int i = 2; i <= (player.getCardsHand().size()+1); i++) {
                            String index = Integer.toString(i);
                            if (input.equals(index)) {
                                Card card = player.getCardsHand().get(i - 2); //store selected card
                                if (card instanceof TrumpCard) {
                                    Category category1 = ((TrumpCard) card).getCategory();
                                    if (category != Category.GEOL) {            //change category unless its the geologist
                                        category = category1;
                                    } else {                                    //if geologist ask category
                                        player.askCategory();
                                    }
                                    player.removeCard(i - 2);                     //remove card from hand
                                    field.addCard(card);                            //add card to field
                                } else {
                                    if (lastCard != null) {                         //if there is a last card
                                        Boolean indicator = compareCards((PlayCard)lastCard, (PlayCard)card, category); //compare cards
                                        if (indicator) {                            //if valid placement
                                            player.removeCard(i - 2);                     //remove card from hand
                                            field.addCard(card);                            //add card to field
                                            confirm = true;
                                            lastCard = card;
                                        }
                                    } else {                                        //if there is no last card
                                        player.removeCard(i - 2);                     //remove card from hand
                                        field.addCard(card);                            //add card to field
                                        confirm = true;
                                        lastCard = card;
                                    }
                                }

                            }
                        }
                    }
                } else {
                    noPlayersPassed += 1;
                }
                if (player.getCardsHand().size() == 0) {
                    playersWon.add(player);
                    players.remove(x);
                } else {
                    players.set(x,player);
                }
            }
            if (noPlayersPassed >= (playersNo - 1)) {
                roundEnd = true;
                for (Player player : players) {
                    if (!player.getPass()) {
                        System.out.println(player.displayHand());
                        playerWonRound = player;
                        displayMessage(playerWonRound.getName() + " won the round");
                    }
                }
            }
        }
        storedCards.addCards(field);
        return playerWonRound;
    }



    private Boolean compareCards(PlayCard lastCard, PlayCard card, Category category) {
        int lastint=0, thisint=0;
        double lastDouble,thisDouble;
        switch (category) {
            case HARD:
                lastDouble = lastCard.getHigherValue(2);
                thisDouble = card.getHigherValue(2);
                return lastDouble < thisDouble;
            case SPEC:
                lastDouble = lastCard.getHigherValue(1);
                thisDouble = card.getHigherValue(1);
                return lastDouble < thisDouble;
            case CLEA:
                for (int x=0; x < cleavageValues.length; x++) {
                    if (cleavageValues[x].equals(lastCard.getCleavage())) {
                        lastint = x;
                    }
                    if (cleavageValues[x].equals(card.getCleavage())) {
                        thisint = x;
                    }
                }
                return lastint < thisint;
            case CRUS:
                for (int x=0; x < crustalAbundanceValues.length; x++) {
                    if (crustalAbundanceValues[x].equals(lastCard.getCrustalAbundance())) {
                        lastint = x;
                    }
                    if (crustalAbundanceValues[x].equals(card.getCrustalAbundance())) {
                        thisint = x;
                    }
                }
                return lastint < thisint;
            case ECON:
                for (int x=0; x < economicValueValues.length; x++) {
                    if (economicValueValues[x].equals(lastCard.getEconomicValue())) {
                        lastint = x;
                    }
                    if (economicValueValues[x].equals(card.getEconomicValue())) {
                        thisint = x;
                    }
                }
                return lastint < thisint;
            default: return false;
            }
    }
    public static <Player> ArrayList<Player> shiftArray(ArrayList<Player> array, int shift)
    {
        if (array.size() == 0)
            return array;
        for(int i = 0; i < shift; i++)
        {
            // remove last element, add it to front of the ArrayList
            Player element = array.remove( array.size() - 1 );
            array.add(0, element);
        }

        return array;
    }

    private void dealCards() {
        for (Player player : players) {
            ArrayList<Card> cards = deck.dealCards(NO_CARDS_IN_HAND);               //get 8 cards
            player.setCards(cards);
        }
    }

    /* Getter/Setters */

    public int getPlayersNo() {
        return playersNo;
    }

    private void setPlayersNo(int playersNo) {
        this.playersNo = playersNo;
    }

    /* JOptionPane methods to reduce complexity */

    private static void displayMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    private static Boolean askConfirmation(String message) {
        return JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, message);
    }

    private static String askInput(String message) {
        return JOptionPane.showInputDialog(null, message);
    }

}
