import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Created by Dale Muccignat on 21/09/2016.
 * Super Trump (Game Object class)
 */
    //todo reset field after play
    //todo make rounds
    //todo trump card functionality
class TrumpGame extends JFrame implements MouseListener,ActionListener {
    private static final int NO_CARDS_IN_HAND = 8;
    private ArrayList<Player> players,playersWon;
    private String[] playerNames;
    private Boolean[] playerAiCheck;
    private static Deck deck;
    private Boolean roundEnd,confirm;
    private Deck storedCards,field;
    private Card currentCard,lastCard;
    private Player playerWonRound,currentPlayer;
    private Category currentCategory = Category.HARDNESS;
    private int helpCount = 0, turnNo = 0, noHumans=0, noAI=0;
    private ImageIcon[] helpCards = new ImageIcon[4];

    private JFrame helpFrame = new JFrame();
    private Container con;
    private JMenuBar menuBar = new JMenuBar();
    private JMenuItem startMenu = new JMenuItem("Start New Game");
    private JMenuItem helpMenu = new JMenuItem("How to play");
    private JMenuItem quitMenu = new JMenuItem("quit");
    private JPanel gamePanel = new JPanel();
    private JPanel gamePanel2 = new JPanel();
    private JPanel fieldPanel = new JPanel();
    private JPanel selectionPanel = new JPanel();
    private JPanel gameInfoPanel = new JPanel();
    private JPanel handPanel = new JPanel();
    private JPanel cardViewPanel = new JPanel();
    private JLabel inputLabel = new JLabel("Please Input:");
    private JLabel aiLabel = new JLabel("Ai?");
    private JLabel nameLabel = new JLabel("Name");
    private JLabel errorLabel = new JLabel("");
    private JLabel infoLabel = new JLabel("");
    private JLabel playerLabel = new JLabel("");
    private JLabel categoryLabel = new JLabel("");
    private JLabel helpLabel = new JLabel();
    private JLabel cardDisplayLabel = new JLabel();
    private JLabel spacerLabel = new JLabel();

    private JTextField[] inputTexts = new JTextField[5];
    private JCheckBox[] aiCheck = new JCheckBox[5];
    private JButton confirmInputButton = new JButton("Confirm Input");
    private JButton selectionButton = new JButton("Confirm Input");
    private JButton nextHelp = new JButton("Next Page.");
    private JButton playCardButton = new JButton("Play Selected Card");
    private JButton passButton = new JButton("Pass");
    private JComboBox<Category> selectionBox = new JComboBox<>();
    private JComboBox<String> playerHandBox = new JComboBox<>();

    TrumpGame() {
        playersWon = new ArrayList<>();
        storedCards = new Deck();
        field = new Deck();
        getHelpCards();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(400,400);
        initialiseMenuGui();
    }

    private void initialiseSetupGui() {
        con = getContentPane();
        // input panel
        gamePanel.setLayout(new GridLayout(8,2));
        infoLabel.setText("Blank fields will be ignored.");
        inputLabel.setText("<html>Please enter player information.<br>" +
                "Must be at least 3 players.</html>");
        gamePanel.add(inputLabel);
        gamePanel.add(confirmInputButton);
        gamePanel.add(nameLabel);
        gamePanel.add(aiLabel);
        for (int i=0;i<5;i++) {
            inputTexts[i] = new JTextField();
            gamePanel.add(inputTexts[i]);
            aiCheck[i] = new JCheckBox();
            gamePanel.add(aiCheck[i]);
        }
        gamePanel.add(infoLabel);
        gamePanel.add(errorLabel);
        confirmInputButton.addActionListener(this);
        con.add(gamePanel);
        invalidate();
        validate();
        repaint();
    }

    private void initialiseMenuGui() {
        setJMenuBar(menuBar);
        menuBar.add(startMenu);
        menuBar.add(helpMenu);
        menuBar.add(quitMenu);
        startMenu.addActionListener(this);
        helpMenu.addActionListener(this);
        quitMenu.addActionListener(this);
    }

    private void initialiseGameGui() {
        gamePanel.removeAll();
        gamePanel.setLayout(new GridLayout(1,2));
        setSize(640,500);
        gamePanel2.setLayout(new GridLayout(4,1));
        gameInfoPanel.setLayout(new GridLayout(4,1));
        handPanel.setLayout(new BorderLayout());
        cardViewPanel.setLayout(new BorderLayout());
        selectionPanel.setLayout(new BorderLayout());
        // add spacer as menu overlays the card
        cardViewPanel.add(spacerLabel, BorderLayout.NORTH);
        cardViewPanel.add(cardDisplayLabel);
        gamePanel.add(gamePanel2);
        gamePanel.add(cardViewPanel);
        gamePanel2.add(gameInfoPanel);
        gamePanel2.add(fieldPanel);
        gamePanel2.add(selectionPanel);
        gamePanel2.add(handPanel);
        gameInfoPanel.add(playerLabel);
        gameInfoPanel.add(infoLabel);
        gameInfoPanel.add(categoryLabel);
        gameInfoPanel.add(passButton);
        infoLabel.setText("Please select a card");
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        handPanel.add(playerHandBox, BorderLayout.NORTH);
        handPanel.add(playCardButton, BorderLayout.SOUTH);
        passButton.addActionListener(this);
        playCardButton.addActionListener(this);
        playerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        startRound();
        invalidate();
        validate();
        repaint();

    }

    private void startRound() {
        currentPlayer = players.get(0);
        roundEnd = false;
        askCategory();
        if (currentPlayer instanceof User) {
            displayUserData();
            playCardButton.setEnabled(false);
        } else {
            runTurnAi();
        }
    }

    private void initialiseSelectionGui() {
        /* makes the selection panel gui */
        selectionButton.setEnabled(true);
        selectionBox.addItem(Category.HARDNESS);
        selectionBox.addItem(Category.CLEAVAGE);
        selectionBox.addItem(Category.SPECIFICGRAVITY);
        selectionBox.addItem(Category.CRUSTALABUNDANCE);
        selectionBox.addItem(Category.ECONOMICVALUE);
        selectionPanel.add(selectionBox, BorderLayout.NORTH);
        selectionPanel.add(selectionButton, BorderLayout.SOUTH);
        selectionButton.addActionListener(this);
        invalidate();
        validate();
        repaint();
    }

    private void displayUserData() {
        playerHandBox.removeAll();
        playerLabel.setText(currentPlayer.getName() + "'s Turn.");
        fieldPanel.setLayout(new GridLayout(field.getCards().size(),1));
        for (Card card : field.getCards()) {
            JLabel label = new JLabel(declareCard(card));
            fieldPanel.add(label);
        }
        for (Card card : currentPlayer.getCardsHand()) {
            playerHandBox.addItem(declareCard(card));
        }
        playerHandBox.addActionListener(this);
        invalidate();
        validate();
        repaint();
    }

    private void runTurnAi() {
        confirm = false;
        //todo work on this
        // only ai players go through this method
        String input;
        if (!currentPlayer.getPass() && !roundEnd && !currentPlayer.getWon()) {
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
                            }
                            confirm = checkMagnetite();
                            // sets confirm equal to true if card played
                            if (!confirm) {
                                confirm = processCard(currentPlayer.getCardsHand().get(i - 2));
                            }
                        }
                    }
                }
            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == confirmInputButton) {
            createPlayers();
        } else if (source == startMenu) {
            gamePanel.removeAll();
            initialiseSetupGui();
        } else if (source == helpMenu) {
            displayHelp();
        } else if (source == quitMenu) {
            dispose();
        } else if (source == nextHelp) {
            ++helpCount;
            if (helpCount > 3) {
                helpCount = 0;
            }
            helpLabel.setIcon(helpCards[helpCount]);
        } else if (source == playCardButton) {
            if (!checkGameEnd()) {
                runTurnHuman();
                checkIfGameWinner(currentPlayer);
                currentPlayer = players.get(turnNo);
                displayUserData();
                invalidate();
                validate();
                repaint();
                //todo if next player is ai, run auto turns
//                runAutoTurn();
            } else {
                infoLabel.setText("Game has Ended");
                invalidate();
                validate();
                repaint();
            }
        } else if (source == passButton) {
            if (!checkGameEnd()) {
                currentPlayer = players.get(turnNo);
                checkIfGameWinner(currentPlayer);
                pass(currentPlayer);
                checkRoundWinner();
                ++turnNo;
                displayUserData();
            } else {
                infoLabel.setText("Game has Ended");
            }
        } else if (source == selectionButton) {
            currentCategory = (Category) selectionBox.getSelectedItem();
            categoryLabel.setText("Category is: " + currentCategory);
            playCardButton.setEnabled(true);
            selectionButton.setEnabled(false);
            invalidate();
            validate();
            repaint();
        }
        /* sets selected card */
        if (source == playerHandBox) {
            int index = playerHandBox.getSelectedIndex();
            currentCard = players.get(turnNo).getCardsHand().get(index);
            cardDisplayLabel.setIcon(currentCard.getImage());
        }
//        for (int i = 0; i< currentPlayer.getCardsHand().size(); i++) {
//            if (e.getSource() == playerHandButtons.get(i)) {
//                System.out.println(players.get(turnNo).getName());
//                currentCard = players.get(turnNo).getCardsHand().get(i);
//                cardDisplayLabel.setIcon(currentCard.getImage());
//            }
//        }
        invalidate();
        validate();
        repaint();
    }

    private void runTurnHuman() {
        confirm = false;
        // if player hasn't passed, player hasn't won game and round hasn't been won
        if (!currentPlayer.getPass() && !roundEnd && !currentPlayer.getWon()) {
            //store selected card
            confirm = checkMagnetite();
            if (!confirm) {
                processCard(currentCard);
            }
        }
    }

    private Boolean checkMagnetite() {
        /* if player is first to play after a trump card (meaning they played it) */
        if ((turnNo == 0) && (lastCard instanceof TrumpCard)) {
            if (lastCard.getTitle().equals("The Geophysicist")) {
                if (currentCard.getTitle().equals("Magnetite")) {
                    //todo
//                    displayMessage(player.getName() + " has won!" +
//                            "\nThey played the trump card: 'The Geophysicist' " +
//                            "with the play card 'Magnetite'");
                    playersWon.add(currentPlayer);
                    players.remove(currentPlayer);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        /* sets selected card */
//        for (int i = 0; i< playerHandButtons.size(); i++) {
//            if (e.getSource() == playerHandButtons.get(i)) {
//                System.out.println(players.get(turnNo).getName());
//                currentCard = players.get(turnNo).getCardsHand().get(i);
//                cardDisplayLabel.setIcon(currentCard.getImage());
//            }
//        }
        invalidate();
        validate();
        repaint();
    }

    private void createPlayers() {
        playerNames = new String[5];
        playerAiCheck = new Boolean[5];
        // store inputs
        for (int i=0;i<5;i++) {
            playerNames[i] = inputTexts[i].getText();
            playerAiCheck[i] = aiCheck[i].isSelected();
        }
        players = new ArrayList<>();                                        //Dealer is always the last player
        for (int i=0;i<5;i++) {
            if (playerAiCheck[i] && !playerNames[i].equals("")) {
                Ai ai = new Ai(playerNames[i]);
                players.add(ai);
            } else if (!playerNames[i].equals("")) {
                User player = new User(playerNames[i]);
                players.add(player);
            }
        }
        Collections.shuffle(players);
        for (Player player : players) {
            System.out.println(player.getName());
        }
        if (players.size() > 2) {
            System.out.println("wellDOne");
            createDeck();
            dealCards();
            initialiseGameGui();
        } else {
            errorLabel.setText("Invalid number of players.");
        }
    }

    private void displayHelp() {
        helpFrame.setVisible(true);
        helpFrame.setLayout(new BorderLayout());
        helpFrame.setSize(700,700);
        helpFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        helpLabel.setIcon(helpCards[0]);
        helpFrame.add(helpLabel);
        helpFrame.add(nextHelp, BorderLayout.EAST);
        nextHelp.addActionListener(this);
        invalidate();
        validate();
        repaint();
        // How to play is images, will be implimented in GUI
    }

    private void getHelpCards() {
        for (int i = 0; i < 4; i++) {
            helpCards[i] = new ImageIcon("imagesc3\\Slide6" + (i+1) + ".jpg");
            Image image = helpCards[i].getImage(); // transform it
            Image newimg = image.getScaledInstance(600, 700,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
            helpCards[i] = new ImageIcon(newimg);  // transform it back
        }
    }

//    private void startGame() {
//        //creates game, plays rounds
//        createDeck();
//        dealCards();
//        currentCategory = null;
//        while (!checkGameEnd()) {
//            //while there is not one player left
//            roundEnd = false;
//            // error checking
//            while (currentCategory == null) {
//                currentCategory = players.get(0).askCategory();
//            }
//            // start round which returns the winning player of that round
//            playerLabel.setText("Category is: " + currentCategory);
//            startRound();
//            // shift array so that winning player is first while retaining order
//            shiftArray(players, players.size() - (players.indexOf(playerWonRound))); //shift array so player won is the first of the next round
//        }
//        // display winning players
//        displayMessage("----------\nThe game has ended!");
//        displayWinners();
//        displayMessage("----------\nPlay again?");
//    }

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

//    private void startRound() {
//        String input;
//        playerWonRound=null;
//        int playerNoRound = 0;
//        // set players pass to false
//        setPlayersPass(false);
//        while (!roundEnd) {
//            //iterate through players
//            for (Player player : players) {
//                ++playerNoRound;
//                confirm = false;
//                currentPlayer = player;
//                checkIfGameWinner(player);
//                checkRoundWinner();^
//                // if player hasn't passed, player hasn't won game and round hasn't been won
//                if (!currentPlayer.getPass() && !roundEnd && !currentPlayer.getWon()) {
//                    displayMessage("----------");
//                    displayMessage(currentPlayer.getName() + "'s Turn");
//                    while (!confirm) {
//                        input = currentPlayer.runTurn(currentCategory, field);
//                        switch (input) {
//                            case "1": {
//                                // 1 is PASS, the rest are cards.
//                                confirm = true;
//                                // pass player, check if winner
//                                pass(currentPlayer);
//                                break;
//                            }
//                            default: {
//                                String index;
//                                for (int i = 2; i <= (currentPlayer.getCardsHand().size() + 2); i++) {
//                                    //iterate through rest of selection
//                                    index = Integer.toString(i);
//                                    if (input.equals(index)) {
//                                        //store selected card
//                                        currentCard = currentPlayer.getCardsHand().get(i - 2);
//                                        switch (playerNoRound) {
//                                            //if player is first to play after a trump card (meaning they played it)
//                                            case 1: {
//                                                if (trumpCard != null) {
//                                                    if (trumpCard.getTitle().equals("The Geophysicist")) {
//                                                        if (currentCard.getTitle().equals("Magnetite")) {
//                                                            confirm = true;
//                                                            displayMessage(player.getName() + " has won!" +
//                                                                    "\nThey played the trump card: 'The Geophysicist' " +
//                                                                    "with the play card 'Magnetite'");
//                                                            playersWon.add(currentPlayer);
//                                                            players.remove(currentPlayer);
//                                                        }
//                                                    }
//                                                }
//                                                break;
//                                            }
//                                            //else erase the trumpcard
//                                            default: {
//                                                trumpCard=null;
//                                            }
//                                        }
//                                        processCard(i);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//                checkIfGameWinner(player);
//            }
//            if (checkGameEnd()) {
//                roundEnd = true;
//            }
//        }
//        displayMessage(playerWonRound.getName() + " won the round!");
//        storeCards();
//    }

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

    private Boolean processCard(Card card) {
        /* Processes the card, decides whether to play it */
        if (currentCard instanceof TrumpCard) {
            //if trump
            playCard(card);
            roundEnd = true;
            //ends the round and starts with new player
            playerWonRound = currentPlayer;
            return true;
        } else {
            //if playcard
            if (!field.getCards().isEmpty()) {                         //if there is a last play card
                //if no last card
                Boolean indicator = ((PlayCard) currentCard).compareCards(
                        (PlayCard) field.getCards().get(field.getCards().size()-1),
                        currentCategory); //compare cards
                if (indicator) {                            //if valid placement
                    playCard(card);
                    return true;
                } else {
                    if (currentPlayer instanceof User) {
                        infoLabel.setText("Error, card has lower value than the previous card.");
                    }
                    return false;
                }
            } else {                                        //if there is no last card
                playCard(card);
                return true;
            }
        }
    }

    private void playCard(Card card) {
        /* Plays the card */
        currentPlayer.removeCard(card);                     //remove card from hand
        cardDisplayLabel.setIcon(new ImageIcon());
        //seperate methods for trump/play
        if (currentCard instanceof PlayCard) {
            field.addCard(currentCard);                            //add card to field
            lastCard = currentCard;
            //todo
            infoLabel.setText(currentPlayer.getName() + " played: " +
                    declareCard((PlayCard) currentCard));
        } else {
            storedCards.addCard(currentCard);
            Category category1 = ((TrumpCard) currentCard).getCategory();
            //change category unless its the geologist
            if (category1 != Category.GEOLOGIST) {
                currentCategory = category1;
            } else {
                //if geologist ask category
                askCategory();

            }
            //todo
//            displayMessage(currentPlayer.getName() + " Played trump: " +
//                    currentCard.getTitle() + "\nCategory has been changed to: " +
//                    currentCategory.toString());
        }
        currentCard = null;
        ++turnNo;
    }

    private void askCategory() {
        if (currentPlayer instanceof Ai) {
            System.out.println("Ai");
            currentCategory = currentPlayer.askCategory();
            while (currentCategory == null) {
                currentCategory = currentPlayer.askCategory();
            }
        } else {
            System.out.println("human");
            playCardButton.setEnabled(false);
            initialiseSelectionGui();
        }
    }


    private void storeCards() {
        if (!field.getCards().isEmpty()) {
            storedCards.addCards(field);
            field.getCards().clear();
        }
    }

    private void checkIfGameWinner(Player player) {
        if (player.getCardsHand().size() == 0) {
            confirm = true;
            //if player has not already won
            if (!player.getWon()) {
                displayMessage("#####\n" + player.getName() + " has won!\n#####");
                playersWon.add(player);
                player.setWon(true);
            }
        }
    }

    private void checkRoundWinner() {
        //check for winner
        if (checkPassed()) {
            playerWonRound = getRoundWinner();
            shiftArray(players, players.size() - (players.indexOf(playerWonRound)));
            turnNo = 0;
            roundEnd = true;
            startRound();
        }
    }

    private String declareCard(Card currentCard) {
        if (currentCard instanceof PlayCard) {
            return currentCard.getTitle() + ", " + currentCategory.toString().toLowerCase() +
                    ", (" + ((PlayCard) currentCard).getCategoryValue(currentCategory) + ")";
        } else {
            return currentCard.getTitle() + ", changes category to: " + ((TrumpCard) currentCard).getCategory().toString();
        }
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
        //todo
        displayMessage(player.getName() + " has decided to pass");
        //if deck isn't empty, deal card, else fill deck up with stored cards
        if (deck.getCards().isEmpty()) {
            deck.getCards().addAll(storedCards.getCards());
            storedCards.getCards().clear();
        }
        player.getCardsHand().addAll(deck.dealCards(1));

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

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
