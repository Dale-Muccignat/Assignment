import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Dale Muccignat on 21/09/2016.
 * Super Trump (Game Object class)
 */

class TrumpGame {
    private static final int NO_CARDS_IN_HAND = 8;
    private ArrayList<Player> players,playersWon = new ArrayList<>();
    private static Deck deck;
    private int playersNo;
    private String userName;
    private Boolean roundEnd,confirm;
    private Deck storedCards = new Deck(),field = new Deck();
    private Card currentCard,lastCard=null;
    private Player playerWonRound,currentPlayer;
    private Category currentCategory;
    private String[] economicValueValues = {"trivial", "low", "moderate", "high", "very high", "I'm rich!"},
            crustalAbundanceValues = {"ultratrace", "trace", "low", "moderate", "high", "very high"},
            cleavageValues = {"none", "poor/none", "1 poor", "2 poor", "1 good", "1 good, 1 poor", "2 good",
            "3 good", "1 perfect", "1 perfect, 1 good", "1 perfect, 2 good", "2 perfect, 1 good", "3 perfect",
            "4 perfect", "6 perfect"};

    TrumpGame() {

    }

    public void startGame() {
        //creates game, plays rounds
        createPlayers();
        createDeck();
        dealCards();
        while (players.size() != 1) {
            //while there is not one player left
            roundEnd = false;
            currentCategory = players.get(0).askCategory();
            // error checking
            while (currentCategory == null) {
                currentCategory = players.get(0).askCategory();
            }
            // start round which returns the winning player of that round
            startRound();
            // shift array so that winning player is first while retaining order
            shiftArray(players, players.size() - (players.indexOf(playerWonRound))); //shift array so player won is the first of the next round
        }
        // display winning players
        displayWinners();
    }

    private void displayWinners() {
        //displays winners in order
        String message = "The winning players in order are: ";
        int x=0;
        for (Player player : playersWon) {
            ++x;
            message += "\n(" + x + ") " + player.getName();
        }
        displayMessage(message);
    }

    private void createDeck() {
        //builds the deck
        deck = new Deck();
        deck.buildDeck();
    }

    private void createPlayers() {
        //create's players array, custom amount of humans/ai
        Boolean confirm =false;
        int noHumans=0,noAI=0;
        while (!confirm) {
            String input = askInput("How many human players are playing?" + "\nMust be between 0 and 5 inclusive");
            switch (input) {
                case "1": noHumans = 1;
                    break;
                case "2": noHumans = 2;
                    break;
                case "3": noHumans = 3;
                    break;
                case "4": noHumans = 4;
                    break;
                case "5": noHumans = 5;
                    break;
                case "0": noHumans = 0;
                    break;
                default:
                    displayMessage("Error, please enter a valid selection (single integer).");
                    break;
            }
            confirm = askConfirmation("You have indicated that there are " + noHumans +
                    " humans.\nIs this Correct?");
        }
        confirm = false;
        while (!confirm) {
            Boolean asked = false;
            String input = askInput("How many AI players are playing?\nMust be between " + (3-noHumans) +
                    " and " + (5 - noHumans) + " inclusive.");
            for (int x = (3-noHumans); x <= (5-noHumans); x++) {
                System.out.println(x);
                String option = Integer.toString(x);
                if (option.equals(input)) {
                    noAI = Integer.parseInt(option);
                    confirm = true;
                }
            }
            if (!confirm) {
                displayMessage("Error, please enter a valid selection (single integer).");
            } else {
                confirm = askConfirmation("You have indicated that there are " + noAI +
                        " AI.\nIs this Correct?");
            }
        }
        players = new ArrayList<>();                                        //Dealer is always the last player
        int playerCount=0;
        for (int x=0;x<noHumans;x++) {
            ++playerCount;
            String name="";
            while (name.isEmpty()) {
                name = askInput("Player " + (x+1) + "\nPlease input your name:");                         //Gets userName
            }
            Player player = new User(name,playerCount+1);
            players.add(player);
        }
        for (int i=0;i<noAI;i++) {
            Player player = new Ai("Ai " + (i+1),playerCount+1);
            players.add(player);
        }
        for (Player player : players) {
            System.out.println(player.name);
        }
        Collections.shuffle(players);
        for (Player player : players) {
            System.out.println(player.name);
        }
    }

    private void startRound() {
        String input;
        // set players pass to false
        setPlayersPass(false);
        lastCard = null;
        while (!roundEnd) {
            //iterate through players
            System.out.println("#####\nnew sweep\n######");
            for (Player player : players) {
                currentPlayer = player;
                confirm = false;
                // if player hasn't passed and round hasn't been won
                if (!currentPlayer.getPass() && !roundEnd) {
                    System.out.println(currentPlayer.getName() + "'s Turn");
                    while (!confirm) {
                        //todo display ranking system for categories
                        //todo enums for categories
                        input = currentPlayer.runTurn(currentCategory, field);
                        switch (input) {
                            case "1": {
                                // 1 is PASS, the rest are cards.
                                confirm = true;
                                // pass player, check if winner
                                pass(currentPlayer);
                                checkWinner();
                                break;
                            }
                            default: {
                                processCard(input);
                            }
                        }
                    }
                }
                checkGameWinner();
            }
        }
        storeCards();
    }

    private void storeCards() {
        if (!field.getCards().isEmpty()) {
            storedCards.addCards(field);
        }
    }

    private void processCard(String input) {
        String index;
        for (int i = 2; i <= (currentPlayer.getCardsHand().size() + 1); i++) {
            //iterate through rest of selection
            index = Integer.toString(i);
            if (input.equals(index)) {
                //store selected card
                currentCard = currentPlayer.getCardsHand().get(i - 2);
                if (currentCard instanceof TrumpCard) {
                    //if trump
                    playCard(i);
                    System.out.println("TRUMP PLAYED");
                    confirm = true;
                    roundEnd = true;
                    //ends the round and starts with new player
                    playerWonRound = currentPlayer;
                } else {
                    //if playcard
                    if (lastCard != null) {                         //if there is a last card
                        //if no last card
                        Boolean indicator = compareCards((PlayCard) lastCard,
                                (PlayCard) currentCard); //compare cards
                        if (indicator) {                            //if valid placement
                            playCard(i);
                            confirm = true;
                        } else if (currentPlayer instanceof User) {
                            displayMessage("Error, card has lower value than the previous card.");
                        }
                    } else {                                        //if there is no last card
                        playCard(i);
                        confirm = true;
                    }
                }
            }
        }
    }

    private void checkGameWinner() {
        if (currentPlayer.getCardsHand().size() == 0) {
            displayMessage(currentPlayer.getName() + " HAS WON");
            playersWon.add(currentPlayer);
            players.remove(currentPlayer);
        }
    }

    private void playCard(int i) {
        currentPlayer.removeCard(i - 2);                     //remove card from hand
        //seperate methods for trump/play
        if (currentCard instanceof PlayCard) {
            field.addCard(currentCard);                            //add card to field
            displayMessage(currentPlayer.getName() + " has played: " +
                    declareCard((PlayCard) currentCard));
            lastCard = currentCard;
        } else {
            storedCards.addCard(currentCard);
            displayMessage(currentPlayer.getName() + " Played trump: " +
                    currentCard.getTitle() + "\nCategory has been changed to: " +
                    currentCategory.toString());
            Category category1 = ((TrumpCard) currentCard).getCategory();
            //change category unless its the geologist
            if (category1 != Category.GEOLOGIST) {
                currentCategory = category1;
            } else {
                //if geologist ask category
                currentCategory = currentPlayer.askCategory();
            }
        }
    }

    private void checkWinner() {
        //check for winner
        if (checkPassed()) {
            playerWonRound = getRoundWinner();
            //todo null error
            System.out.println(playerWonRound.getName() + " won the round!");
            displayMessage(playerWonRound.getName() + " won the round!");
            roundEnd = true;
        }
    }

    private String declareCard(PlayCard currentCard) {
        return currentCard.getTitle() + ", " + currentCategory.toString().toLowerCase() +
                ", (" + currentCard.getCategoryValue(currentCategory) + ")";
    }

    private Boolean checkPassed() {
        // Checks to see if there is one player left to pass, returns true if there is
        int count = 0;
        for (Player player : players) {
            if (player.getPass()) {
                ++count;
            }
        }
        return count == (players.size() - 1);
    }

    private void pass(Player player) {
        player.setPass(true);
        //Player is dealt card when passing
        System.out.println(player.getName() + " Has decided to pass");
        displayMessage(player.getName() + " Has decided to pass");
        //if deck isn't empty, deal card, else fill deck up with stored cards
        if (!deck.getCards().isEmpty()) {
            player.getCardsHand().addAll(deck.dealCards(1));
        } else {
            deck.getCards().addAll(storedCards.getCards());
            storedCards.getCards().clear();
        }

    }


    private Boolean compareCards(PlayCard lastCard, PlayCard card) {
        int lastint=0, thisint=0;
        double lastDouble,thisDouble;
        switch (currentCategory) {
            case HARDNESS:
                lastDouble = lastCard.getHigherValue(2);
                thisDouble = card.getHigherValue(2);
                return lastDouble < thisDouble;
            case SPECIFICGRAVITY:
                lastDouble = lastCard.getHigherValue(1);
                thisDouble = card.getHigherValue(1);
                return lastDouble < thisDouble;
            case CLEAVAGE:
                for (int x=0; x < cleavageValues.length; x++) {
                    if (cleavageValues[x].equals(lastCard.getCleavage())) {
                        lastint = x;
                    }
                    if (cleavageValues[x].equals(card.getCleavage())) {
                        thisint = x;
                    }
                }
                return lastint < thisint;
            case CRUSTALABUNDANCE:
                for (int x=0; x < crustalAbundanceValues.length; x++) {
                    if (crustalAbundanceValues[x].equals(lastCard.getCrustalAbundance())) {
                        lastint = x;
                    }
                    if (crustalAbundanceValues[x].equals(card.getCrustalAbundance())) {
                        thisint = x;
                    }
                }
                return lastint < thisint;
            case ECONOMICVALUE:
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
            player.setCardsHand(cards);
        }
    }

    public void setPlayersPass(boolean playersPass) {
        for (Player player : players) {
            player.setPass(playersPass);
        }
    }

    public Player getRoundWinner() {
        // returns player that hasn't passed
            for (Player player : players) {
                if (!player.getPass()) {
                    return player;
                }
            }
        // Should never be reached
        System.out.println("NULL WAS REACHED ABORT ABORT");
        return null;
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
