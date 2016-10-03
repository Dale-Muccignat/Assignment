import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Created by Dale Muccignat on 21/09/2016.
 * Super Trump (Game Object class)
 */

class TrumpGame {
    private static final int NO_CARDS_IN_HAND = 8;
    private ArrayList<Player> players,playersWon;
    private static Deck deck;
    private Boolean roundEnd,confirm;
    private Deck storedCards,field;
    private Card currentCard,trumpCard;
    private Player playerWonRound,currentPlayer;
    private Category currentCategory;

    TrumpGame() {
        playersWon = new ArrayList<>();
        storedCards = new Deck();
        field = new Deck();
        trumpCard=null;
    }
    void displayMenu() {
        String input = askInput("Welcome to Super-Trump!\nBy: Dale Muccignat" +
                "\nMenu: \n(1) New Game\n(2) How To Play\n(3) Quit");
        switch (input) {
            case "1": startGame();
                break;
            case "2": displayHelp();
                break;
            case "3":
                break;
            default: displayMessage("Invalid selection");
                displayMenu();
                break;
        }
    }

    private void displayHelp() {
        // How to play is images, will be implimented in GUI
        displayMenu();       //Returns to menu
    }

    private void startGame() {
        //creates game, plays rounds
        createPlayers();
        createDeck();
        dealCards();
        currentCategory = null;
        while (!checkGameEnd()) {
            //while there is not one player left
            roundEnd = false;
            // error checking
            while (currentCategory == null) {
                currentCategory = players.get(0).askCategory();
            }
            // start round which returns the winning player of that round
            displayMessage("Category is: " + currentCategory);
            startRound();
            // shift array so that winning player is first while retaining order
            shiftArray(players, players.size() - (players.indexOf(playerWonRound))); //shift array so player won is the first of the next round
        }
        // display winning players
        displayMessage("----------\nThe game has ended!");
        displayWinners();
        displayMessage("----------\nPlay again?");
        displayMenu();
    }

    private boolean checkGameEnd() {
        int count=0;
        for (Player player : players) {
            //count number of players that have one
            if (player.getWon()) {
                ++count;
            }
        }
        //if there is only one player that hasn't won, return true
        return count == (players.size()-1);
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
            String input = askInput("How many non-Ai players are playing?" + "\nMust be between 0 and 5 inclusive");
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
                    " non-Ai player/s.\nIs this Correct?");
        }
        confirm = false;
        while (!confirm) {
            String input = askInput("How many AI players are playing?\nMust be between " + (3-noHumans) +
                    " and " + (5 - noHumans) + " inclusive.");
            for (int x = (3-noHumans); x <= (5-noHumans); x++) {
                String option = Integer.toString(x);
                if (option.equals(input)) {
                    noAI = Integer.parseInt(option);
                    confirm = true;
                }
            }
            if (!confirm) {
                displayMessage("Error, please enter a valid selection (single integer).");
            } else {
                confirm = askConfirmation("You have indicated that there is/are " + noAI +
                        " AI player/s.\nIs this Correct?");
            }
        }
        players = new ArrayList<>();                                        //Dealer is always the last player
        for (int x=0;x<noHumans;x++) {
            //create human players
            String name;
            name = askInput("Player " + (x+1) + "\nPlease input your name:");   //Gets userName
            Player player = new User(name);
            players.add(player);
        }
        for (int i=0;i<noAI;i++) {
            //create ai
            Player player = new Ai("Ai " + (i+1));
            players.add(player);
        }
        Collections.shuffle(players);
        displayMessage("Dealer is: " + players.get(players.size()-1).getName());
    }

    private void startRound() {
        String input;
        playerWonRound=null;
        int playerNoRound = 0;
        // set players pass to false
        setPlayersPass(false);
        while (!roundEnd) {
            //iterate through players
            for (Player player : players) {
                ++playerNoRound;
                confirm = false;
                currentPlayer = player;
                checkIfWinner(player);
                checkWinner();
                // if player hasn't passed, player hasn't won game and round hasn't been won
                if (!currentPlayer.getPass() && !roundEnd && !currentPlayer.getWon()) {
                    displayMessage("----------");
                    displayMessage(currentPlayer.getName() + "'s Turn");
                    while (!confirm) {
                        input = currentPlayer.runTurn(currentCategory, field);
                        switch (input) {
                            case "1": {
                                // 1 is PASS, the rest are cards.
                                confirm = true;
                                // pass player, check if winner
                                pass(currentPlayer);
                                break;
                            }
                            default: {
                                String index;
                                for (int i = 2; i <= (currentPlayer.getCardsHand().size() + 2); i++) {
                                    //iterate through rest of selection
                                    index = Integer.toString(i);
                                    if (input.equals(index)) {
                                        //store selected card
                                        currentCard = currentPlayer.getCardsHand().get(i - 2);
                                        switch (playerNoRound) {
                                            //if player is first to play after a trump card (meaning they played it)
                                            case 1: {
                                                if (trumpCard != null) {
                                                    if (trumpCard.getTitle().equals("The Geophysicist")) {
                                                        if (currentCard.getTitle().equals("Magnetite")) {
                                                            confirm = true;
                                                            displayMessage(player.getName() + " has won!" +
                                                                    "\nThey played the trump card: 'The Geophysicist' " +
                                                                    "with the play card 'Magnetite'");
                                                            playersWon.add(currentPlayer);
                                                            players.remove(currentPlayer);
                                                        }
                                                    }
                                                }
                                                break;
                                            }
                                            //else erase the trumpcard
                                            default: {
                                                trumpCard=null;
                                            }
                                        }
                                        processCard(i);
                                    }
                                }
                            }
                        }
                    }
                }
                checkIfWinner(player);
            }
            if (checkGameEnd()) {
                roundEnd = true;
            }
        }
        displayMessage(playerWonRound.getName() + " won the round!");
        storeCards();
    }

    private void processCard(int i) {
        if (currentCard instanceof TrumpCard) {
            //if trump
            playCard(i);
            confirm = true;
            roundEnd = true;
            //ends the round and starts with new player
            trumpCard = currentCard;
            playerWonRound = currentPlayer;
        } else {
            //if playcard
            if (!field.getCards().isEmpty()) {                         //if there is a last play card
                //if no last card
                Boolean indicator = ((PlayCard) currentCard).compareCards(
                        (PlayCard) field.getCards().get(field.getCards().size()-1),
                        currentCategory); //compare cards
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

    private void playCard(int i) {
        currentPlayer.removeCard(i - 2);                     //remove card from hand
        //seperate methods for trump/play
        if (currentCard instanceof PlayCard) {
            field.addCard(currentCard);                            //add card to field
            displayMessage(currentPlayer.getName() + " has played: " +
                    declareCard((PlayCard) currentCard));
        } else {
            storedCards.addCard(currentCard);
            Category category1 = ((TrumpCard) currentCard).getCategory();
            if (category1 != Category.GEOLOGIST) {
                currentCategory = category1;
            } else {
                //if geologist ask category
                currentCategory = currentPlayer.askCategory();
                while (currentCategory == null) {
                    currentCategory = currentPlayer.askCategory();
                }
            }
            displayMessage(currentPlayer.getName() + " Played trump: " +
                    currentCard.getTitle() + "\nCategory has been changed to: " +
                    currentCategory.toString());
            //change category unless its the geologist
        }
    }

    private void storeCards() {
        if (!field.getCards().isEmpty()) {
            storedCards.addCards(field);
            field.getCards().clear();
        }
    }

    private void checkIfWinner(Player player) {
        if (player.getCardsHand().size() == 0) {
            confirm = true;
            //if player has not allready won
            if (!player.getWon()) {
                displayMessage("#####\n" + player.getName() + " has won!\n#####");
                playersWon.add(player);
                player.setWon(true);
            }
        }
    }

    private void checkWinner() {
        //check for winner
        if (checkPassed()) {
            playerWonRound = getRoundWinner();
            roundEnd = true;
            currentCategory = null;
        }
    }

    private String declareCard(PlayCard currentCard) {
        return currentCard.getTitle() + ", " + currentCategory.toString().toLowerCase() +
                ", (" + currentCard.getCategoryValue(currentCategory) + ")";
    }

    private Boolean checkPassed() {
        // Checks to see if there is one player left to pass, returns true if there is
        //account for players winning the game
        ArrayList<Player> playersLeft = new ArrayList<>();
        for (Player player : players) {
            if (!player.getWon()) {
                playersLeft.add(player);
            }
        }
        int count = 0;
        for (Player player : playersLeft) {
            if (player.getPass()) {
                ++count;
            }
        }
        return count == (playersLeft.size() - 1);
    }

    private void pass(Player player) {
        player.setPass(true);
        //Player is dealt card when passing
        displayMessage(player.getName() + " has decided to pass");
        //if deck isn't empty, deal card, else fill deck up with stored cards
        if (!deck.getCards().isEmpty()) {
            player.getCardsHand().addAll(deck.dealCards(1));
        } else {
            deck.getCards().addAll(storedCards.getCards());
            storedCards.getCards().clear();
        }

    }



    private static <Player> ArrayList<Player> shiftArray(ArrayList<Player> array, int shift)
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

    private void setPlayersPass(boolean playersPass) {
        for (Player player : players) {
            player.setPass(playersPass);
        }
    }

    private Player getRoundWinner() {
        // returns player that hasn't passed or won the game before
        ArrayList<Player> playersLeft = new ArrayList<>();
        // account for players already won
        for (Player player : players) {         //unsure of collect call method
            if (!player.getWon()) {
                playersLeft.add(player);
            }
        }
        for (Player player : playersLeft) {
            if (!player.getPass()) {
                return player;
            }
        }
        // Should never be reached as it is made sure that there is one player that hasn't passed
        return null;
    }
    /* scanner methods to reduce complexity */

    private static void displayMessage(String message) {
        System.out.println(message);
    }

    private static Boolean askConfirmation(String message) {
        System.out.println(message);
        System.out.println(" (1) yes \n (2) no");
        Scanner inputDevice = new Scanner(System.in);
        String input = inputDevice.next();
        switch (input) {
            case "1": return true;
            case "2": return false;
            default: displayMessage("Invalid input");
                askConfirmation(message);
                //is never reached
                return false;
        }
    }

    private static String askInput(String message)
    {
        System.out.println(message);
        Scanner inputDevice = new Scanner(System.in);
        return inputDevice.next();
    }
}
