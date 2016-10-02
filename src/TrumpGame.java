import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Dale Muccignat on 21/09/2016.
 * Super Trump (Game Object class)
 */

class TrumpGame {
    private static final int NO_CARDS_IN_HAND = 8;
    private ArrayList<Player> players,playersWon;
    private static Deck deck;
    private int playersNo;
    private String userName;
    private Deck storedCards = new Deck();
    private String[] economicValueValues = {"trivial", "low", "moderate", "high", "very high", "I'm rich!"},
            crustalAbundanceValues = {"ultratrace", "trace", "low", "moderate", "high", "very high"},
            cleavageValues = {"none", "poor/none", "1 poor", "2 poor", "1 good", "1 good, 1 poor", "2 good",
            "3 good", "1 perfect", "1 perfect, 1 good", "1 perfect, 2 good", "2 perfect, 1 good", "3 perfect",
            "4 perfect", "6 perfect"};

    TrumpGame(String name, int playersNo) {
        this.playersNo = playersNo;
        this.userName = name;
        playersWon = new ArrayList<>();
    }

    public void startGame() {
        int roundNo=0;
        createPlayers();
        createDeck();
        dealCards();
//        while (players.size() != 1) {
            ++roundNo;
            System.out.println("ROUND " + roundNo);
            Category category = players.get(0).askCategory();
            // error checking
            while (category == null) {
                category = players.get(0).askCategory();
            }
            // start round which returns the winning player of that round
            Player playerWon = startRound(category,false);
            System.out.println(playerWon.getName() + "winning player");
            // shift array so that winning player is first while retaining order
            shiftArray(players, players.size() - (players.indexOf(playerWon))); //shift array so player won is the first of the next round
//        }
        // display winning players
//        String message = "The winning players in order are: ";
//        for (int x=0; x < playersWon.size(); ++x) {
//            message += "/n(" + x + ") " + playersWon.get(x-1).getName();
//        }
//        displayMessage(message);
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

    private Player startRound(Category category,Boolean roundEnd) {
        Boolean confirm;
        Deck field = new Deck();
        Player playerWonRound = null, currentPlayer;
        Card lastCard, currentCard;
        String input, index;
        // set players pass to false
        setPlayersPass(false);
        lastCard = null;
        displayMessage("Category is: " + category.toString().toLowerCase());
        while (!roundEnd) {
            //iterate through players
//            System.out.println("#####\nnew sweep\n######");
            for (int x = 0; x < players.size(); x++) {
                currentPlayer = players.get(x);
                confirm = false;
                // if player hasn't passed and round hasn't been won
                if (!roundEnd) {
                    if (!currentPlayer.getPass()) {
//                        System.out.println("--------------\nRound end: " + roundEnd);
//                        System.out.println(currentPlayer.getName() + "'s Turn");
//                        System.out.println("Passed? " + currentPlayer.getPass());
                        while (!confirm) {
                            //todo display ranking system for categories
                            input = currentPlayer.runTurn(category, field);
                            if (input.equals("1")) {
                                // 1 is PASS, the rest are cards.
                                confirm = true;
                                // if there's one player player left, return the player
                                pass(currentPlayer);
                                switch (checkPassed()) {
                                    case "1":   //all but one player has passed, return winner
                                        playerWonRound = getRoundWinner();
                                        //todo null error
//                                        System.out.println(playerWonRound.getName() + " won the round!");
                                        displayMessage(playerWonRound.getName() + " won the round!");
                                        roundEnd = true;
                                        break;
                                    case "2":   //all players have passed, return current.
                                        //this shouldn't be reached??
                                        System.out.println("WAS REACHED");
                                        playerWonRound = currentPlayer;
                                        displayMessage(playerWonRound.getName() + " won the round!");
                                        roundEnd = true;
                                        break;
                                    default:
                                        break;
                                }
                            } else {
                                for (int i = 2; i <= (currentPlayer.getCardsHand().size() + 1); i++) {
                                    //iterate through rest of selection
                                    index = Integer.toString(i);
                                    if (input.equals(index)) {
                                        //store selected card
                                        currentCard = currentPlayer.getCardsHand().get(i - 2);
                                        if (currentCard instanceof TrumpCard) {
                                            Category category1 = ((TrumpCard) currentCard).getCategory();
                                            //change category unless its the geologist
                                            if (category1 != Category.GEOLOGIST) {
                                                category = category1;
                                            } else {
                                                //if geologist ask category
                                                category = currentPlayer.askCategory();
                                            }
                                            displayMessage("Category has been changed to: " + category.toString().toLowerCase());
                                            //remove card from hand
                                            currentPlayer.removeCard(i - 2);
                                            //add card to stored cards
                                            storedCards.addCard(currentCard);
                                            //shift array so player is the first of the next round
                                            shiftArray(players, players.size() - (players.indexOf(currentPlayer)));
                                            //start new round when trump is played
                                            startRound(category, false);
                                        } else {
                                            if (lastCard != null) {                         //if there is a last card
                                                Boolean indicator = compareCards((PlayCard) lastCard, (PlayCard) currentCard, category); //compare cards
                                                if (indicator) {                            //if valid placement
                                                    currentPlayer.removeCard(i - 2);                     //remove card from hand
                                                    field.addCard(currentCard);                            //add card to field
                                                    confirm = true;
                                                    displayMessage(currentPlayer.getName() + " has played: " +
                                                            declareCard((PlayCard) currentCard, category));
                                                    lastCard = currentCard;
                                                } else if (currentPlayer instanceof User) {
                                                    displayMessage("Error, card has lower value than the previous card.");
                                                }
                                            } else {                                        //if there is no last card
                                                currentPlayer.removeCard(i - 2);                     //remove card from hand
                                                field.addCard(currentCard);                            //add card to field
                                                confirm = true;
                                                displayMessage(currentPlayer.getName() + " has played: " +
                                                        declareCard((PlayCard) currentCard, category));
                                                lastCard = currentCard;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (currentPlayer.getCardsHand().size() == 0) {
                    displayMessage(currentPlayer.getName() + " HAS WON");
                    playersWon.add(currentPlayer);
                    players.remove(currentPlayer);
                }
            }
            // Should now be invalid
//            if (noPlayersPassed >= (players.size() - 1)) {
//                roundEnd = true;
//                for (Player player : players) {
//                    if (!player.getPass()) {
//                        playerWonRound = player;
//                        displayMessage(playerWonRound.getName() + " won the round");
//                    }
//                }
//            }
        }
        if (!field.getCards().isEmpty()) {
            storedCards.addCards(field);
        }
        return playerWonRound;
    }

    private String declareCard(PlayCard currentCard, Category category) {
        return currentCard.getTitle() + ", " + category.toString().toLowerCase() +
                ", (" + currentCard.getCategoryValue(category) + ")";
    }

    private String checkPassed() {
        // Checks to see if there is one player left to pass, returns true if there is
        int count = 0;
        for (Player player : players) {
            if (player.getPass()) {
                ++count;
            }
        }
        if (count == (players.size() - 1)) {
            return "1";
        } else if (count == players.size()) {
            return "2";
        } else { return "3"; }
    }

    private void pass(Player player) {
        player.setPass(true);
        //Player is dealt card when passing
        System.out.println(player.getName() + " Has decided to pass");
        displayMessage(player.getName() + " Has decided to pass");
        player.getCardsHand().addAll(deck.dealCards(1));
    }


    private Boolean compareCards(PlayCard lastCard, PlayCard card, Category category) {
        int lastint=0, thisint=0;
        double lastDouble,thisDouble;
//        System.out.println(lastCard.display(1));
//        System.out.println(card.display(1));
        switch (category) {
            case HARDNESS:
                lastDouble = lastCard.getHigherValue(2);
                thisDouble = card.getHigherValue(2);
//                System.out.println("LAST: " + lastDouble + " THIS: " + thisDouble);
//                System.out.println(lastDouble < thisDouble);
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
