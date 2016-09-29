import javax.swing.*;
import java.util.Random;

/**
 * Created by Dale Muccignat on 21/09/2016.
 * Super Trump (Game Object class)
 */

class TrumpGame {
    private static Player[] players;
    private Deck deck;
    private int playersNo;
    private String userName;
    private Deck field;
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
        //todo categories
        //todo re-arrange players to so that last player starts
        //todo check if hands are empty
        Category category = players[0].askCategory();
        Player playerWon = startRound(category);
    }

    private void createDeck() {
        deck = new Deck();
        deck.buildDeck();
    }

    private void createPlayers() {
        Random rand = new Random();
        int randomNum = rand.nextInt(playersNo);                            //Randomly assign the user to a player no
        players = new Player[playersNo];                                        //Dealer is always the last player
        players[randomNum] = new User(userName,randomNum+1);
        for (int x=0; x < playersNo; x++) {                                       //Assign the rest as Ai
            if (x != randomNum) {
                players[x] = new Ai("Player " + x+1,x+1);
            }
        }
    }

    Player startRound(Category category) {
        System.out.println("Category: " + category);
        Boolean roundEnd= false,confirm;
        field = new Deck();
        Player playerWon = null;
        int roundNo=0;
        while (!roundEnd) {
            ++roundNo;
            System.out.println("Round Number: " + roundNo);
            int noPlayersPassed=0;
            Card lastCard = null;
            for (int x=0; x < playersNo; x++) {                                 //iterate through players
                confirm = false;
                System.out.println("Before: " + players[x].getPass() + " " + players[x].getName());
                if (!players[x].getPass()) {
                    playerWon = players[x];
                    while (!confirm) {
                        String input = players[x].runTurn(category,field);
                        System.out.println(input);
                        if (input.equals("1")) {                               // 1 is PASS, the rest are cards.
                            players[x].setPass(true);
                            noPlayersPassed += 1;
                            confirm = true;
                        }
                        for (int i = 2; i < players[x].getCardsHand().size(); i++) {
                            String index = Integer.toString(i);
                            if (input.equals(index)) {
                                Card card = players[x].getCardsHand().get(i - 2); //store selected card
                                if (card instanceof TrumpCard) {
                                    //todo change category
                                } else {
                                    if (lastCard != null) {                         //if there is a last card
                                        Boolean indicator = compareCards((PlayCard)lastCard, (PlayCard)card, category); //compare cards
                                        if (indicator) {                            //if valid placement
                                            players[x].removeCard(i - 2);                     //remove card from hand
                                            field.addCard(card);                            //add card to field
                                            confirm = true;
                                            lastCard = card;
                                        }
                                    } else {                                        //if there is no last card
                                        players[x].removeCard(i - 2);                     //remove card from hand
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
                System.out.println("After: " + players[x].getPass() + " " + players[x].getName());
            }
            System.out.println("No Passed: " + noPlayersPassed);
            if (noPlayersPassed >= (playersNo - 1)) {
                roundEnd = true;
                displayMessage(playerWon.getName() + " won the round");
            }
            displayMessage("Field: \n" + field.display());                      //display field
        }
        return playerWon;
    }



    private Boolean compareCards(PlayCard lastCard, PlayCard card, Category category) {
        int lastint=0, thisint=0;
        double lastDouble,thisDouble;
        System.out.println("Last card: " + lastCard.display(1));
        System.out.println("This card: " + card.display(1));
        switch (category) {
            case HARD:
                lastDouble = lastCard.getHigherValue(2);
                thisDouble = card.getHigherValue(2);
                if (lastDouble < thisDouble) { return true; } else { return false; }
            case SPEC:
                lastDouble = lastCard.getHigherValue(1);
                thisDouble = card.getHigherValue(1);
                if (lastDouble < thisDouble) { return true; } else { return false; }
            case CLEA:
                for (int x=0; x < cleavageValues.length; x++) {
                    if (cleavageValues[x].equals(lastCard.getCleavage())) {
                        lastint = x;
                    }
                    if (cleavageValues[x].equals(card.getCleavage())) {
                        thisint = x;
                    }
                }
                if (lastint < thisint) {
                    return true;
                } else {
                    return false;
                }
            case CRUS:
                for (int x=0; x < crustalAbundanceValues.length; x++) {
                    if (crustalAbundanceValues[x].equals(lastCard.getCrustalAbundance())) {
                        lastint = x;
                    }
                    if (crustalAbundanceValues[x].equals(card.getCrustalAbundance())) {
                        thisint = x;
                    }
                }
                if (lastint < thisint) {
                    return true;
                } else { return false; }
            case ECON:
                for (int x=0; x < economicValueValues.length; x++) {
                    if (economicValueValues[x].equals(lastCard.getEconomicValue())) {
                        lastint = x;
                    }
                    if (economicValueValues[x].equals(card.getEconomicValue())) {
                        thisint = x;
                    }
                }
                if (lastint < thisint) {
                    return true;
                } else { return false; }
            default: return false;
            }
    }


    private void dealCards() {
        for (Player player : players) {
            player.initializeHand();
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
