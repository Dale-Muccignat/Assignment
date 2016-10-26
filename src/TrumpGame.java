import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Dale Muccignat on 21/09/2016.
 * Super Trump (Game Object class with gui)
 */

class TrumpGame extends JFrame implements ActionListener {
    private static final int NO_CARDS_IN_HAND = 8;
    private ArrayList<Player> players,playersWon;
    private static Deck deck;
    private Boolean roundEnd,confirm;
    private Deck storedCards,field;
    private Card currentCard,lastCard;
    private Player playerWonRound,currentPlayer;
    private Category currentCategory;
    private int helpCount = 0, turnNo = 0;
    private JMenu helpMenu;
    private JMenuItem startMenu;
    private JMenuItem howMenu;
    private JMenuItem quitMenu;
    private JLabel infoLabel;
    private JLabel helpLabel;
    private JLabel cardDisplayLabel;
    private JLabel categoryLabel;
    private JPanel gamePanel;
    private JPanel fieldPanel;
    private JPanel selectionPanel;
    private JPanel logPanel;
    private ImageIcon[] helpCards;
    private JCheckBox[] aiCheck;
    private JTextField[] inputTexts;
    private JComboBox<Category> selectionBox;
    private JComboBox<String> playerHandBox;
    private ArrayList<JLabel> playerLabels,loglist;
    private JButton selectionButton,playCardButton,passButton,confirmInputButton,nextHelp;
    Container con = getContentPane();

    TrumpGame() {
        playersWon = new ArrayList<>();
        storedCards = new Deck();
        field = new Deck();
        getHelpCards();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        initialiseMenuGui();
        setResizable(false);
    }

    private void initialiseSetupGui() {
        setSize(400,400);
        gamePanel = new JPanel();
        infoLabel = new JLabel("");
        confirmInputButton = new JButton("Confirm Input");
        inputTexts = new JTextField[5];
        aiCheck = new JCheckBox[5];
        JLabel inputLabel = new JLabel("Please Input:");
        JLabel nameLabel = new JLabel("Name");
        JLabel aiLabel = new JLabel("Ai?");
        JLabel errorLabel = new JLabel("");
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
        helpMenu = new JMenu("Help");
        startMenu = new JMenuItem("Start New Game");
        quitMenu = new JMenuItem("quit");
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        howMenu = new JMenuItem("How to play");
        setSize(400,400);
        setJMenuBar(menuBar);
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        fileMenu.add(startMenu);
        fileMenu.add(quitMenu);
        helpMenu.add(howMenu);
        startMenu.addActionListener(this);
        howMenu.addActionListener(this);
        quitMenu.addActionListener(this);
        initialiseSetupGui();
    }

    private void initialiseGameGui() {
        cardDisplayLabel = new JLabel();
        selectionButton = new JButton("Confirm Input");
        playCardButton = new JButton("Play Selected Card");
        passButton = new JButton("Pass");
        selectionBox = new JComboBox<>();
        playerHandBox = new JComboBox<>();
        logPanel = new JPanel();
        loglist = new ArrayList<>();
        categoryLabel = new JLabel("");
        fieldPanel = new JPanel();
        selectionPanel = new JPanel();
        JPanel gamePanel2 = new JPanel();
        JPanel gameInfoPanel = new JPanel();
        JPanel handPanel = new JPanel();
        JPanel cardViewPanel = new JPanel();
        JPanel playerPanel = new JPanel();


        gamePanel.removeAll();
        gamePanel.setLayout(new GridLayout(1,3));
        setSize(960,500);
        gamePanel.add(gamePanel2);
        gamePanel.add(cardViewPanel);
        gamePanel.add(logPanel);

        gamePanel2.setLayout(new GridLayout(4,1));
        gamePanel2.add(gameInfoPanel);
        gamePanel2.add(fieldPanel);
        gamePanel2.add(selectionPanel);
        gamePanel2.add(handPanel);

        gameInfoPanel.setLayout(new GridLayout(3,1));
        gameInfoPanel.setBorder(BorderFactory.createTitledBorder("Game Information"));
        gameInfoPanel.add(playerPanel);
        playerPanel.setLayout(new GridLayout(1,playerLabels.size()));
        for (JLabel playerLabel : playerLabels) {
            playerPanel.add(playerLabel);
        }
        gameInfoPanel.add(infoLabel);
        gameInfoPanel.add(categoryLabel);
        infoLabel.setText("What to do: Please select a card");
        infoLabel.setHorizontalAlignment(SwingConstants.LEFT);
        categoryLabel.setHorizontalAlignment(SwingConstants.LEFT);
        passButton.addActionListener(this);

        handPanel.setLayout(new BorderLayout());
        handPanel.setBorder(BorderFactory.createTitledBorder("Turn Options"));
        handPanel.add(playerHandBox, BorderLayout.CENTER);
        handPanel.add(playCardButton, BorderLayout.SOUTH);
        handPanel.add(passButton, BorderLayout.NORTH);
        playCardButton.addActionListener(this);

        fieldPanel.setBorder(BorderFactory.createTitledBorder("Field Information"));


        cardViewPanel.setLayout(new BorderLayout());
        cardViewPanel.add(cardDisplayLabel);

        selectionPanel.setLayout(new BorderLayout());
        selectionPanel.setBorder(BorderFactory.createTitledBorder("Category Selection"));

        logPanel.setLayout(new GridLayout(30,1));
        logPanel.setBorder(BorderFactory.createTitledBorder("Event Log"));

        currentCategory = null;
        startRound();
        invalidate();
        validate();
        repaint();

    }

    private void addLog(String message) {
        System.out.println(message);
        logPanel.removeAll();
        JLabel label = new JLabel(message);
        if (loglist.size() == 30) {
            for (int i = 1; i < 30; i++) {
                loglist.set((i-1),loglist.get(i));
            }
            loglist.set(29,label);
        } else {
            loglist.add(label);
        }
        for (JLabel label1 : loglist) {
            logPanel.add(label1);
        }
        invalidate();
        validate();
        repaint();
    }

    private void startRound() {
        categoryLabel.setText("Current category: ");
        setPlayersPass(false);
        storeCards();
        currentPlayer = players.get(turnNo);
        playerLabels.get(turnNo).setBorder(BorderFactory.createLineBorder(Color.RED));
        addLog("New Round!");
        addLog("----------");
        roundEnd = false;
        if (lastCard instanceof PlayCard) {
            lastCard = null;
        }
        if (currentCategory == null) {
            askCategory();
        }
        if (currentPlayer instanceof User) {
            displayUserData();
        } else {
            runAutoTurns();
        }
    }

    private void initialiseSelectionGui() {
        /* makes the selection panel gui */
        selectionButton.setEnabled(true);
        selectionBox.removeAllItems();
        selectionBox.addItem(Category.HARDNESS);
        selectionBox.addItem(Category.CLEAVAGE);
        selectionBox.addItem(Category.SPECIFICGRAVITY);
        selectionBox.addItem(Category.CRUSTALABUNDANCE);
        selectionBox.addItem(Category.ECONOMICVALUE);
        selectionPanel.add(selectionBox, BorderLayout.NORTH);
        selectionPanel.add(selectionButton, BorderLayout.SOUTH);
        selectionButton.addActionListener(this);
        infoLabel.setText("What to do: Please select a category.");
        invalidate();
        validate();
        repaint();
    }

    private void displayUserData() {
        // remove previous display
        playerHandBox.removeAllItems();
        fieldPanel.removeAll();
        for (JLabel playerLabel : playerLabels) {
            playerLabel.setBorder(BorderFactory.createEmptyBorder());
        }
        //set new display
        playerLabels.get(turnNo).setBorder(BorderFactory.createLineBorder(Color.RED));
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
                        pass();
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
                        }
                        confirm = processCard();
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
            con.removeAll();
            gamePanel.removeAll();
            initialiseSetupGui();
        } else if (source == howMenu) {
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
            runTurnHuman();
            if (currentPlayer instanceof User) {
                displayUserData();
                invalidate();
                validate();
                repaint();
            } else {
                runAutoTurns();
            }
        } else if (source == passButton) {
            currentPlayer = players.get(turnNo);
            pass();
            runAutoTurns();
        } else if (source == selectionButton) {
            currentCategory = (Category) selectionBox.getSelectedItem();
            categoryLabel.setText("Current category: " + currentCategory);
            playCardButton.setEnabled(true);
            selectionButton.setEnabled(false);
            passButton.setEnabled(true);
            infoLabel.setText("What to do: Please select a card.");
            displayUserData();
            selectionPanel.removeAll();
            invalidate();
            validate();
            repaint();
        }
        /* sets selected card */
        if (source == playerHandBox) {
            int index = playerHandBox.getSelectedIndex();
            if (index >= 0) {
                currentCard = players.get(turnNo).getCardsHand().get(index);
                cardDisplayLabel.setIcon(currentCard.getImage());
            }
        }
        invalidate();
        validate();
        repaint();
    }

    private void nextTurn() {
        ++turnNo;
        if (turnNo == (players.size())) {
            turnNo = 0;
        }
        currentPlayer = players.get(turnNo);
    }

    private void findNextTurn() {
        Boolean found =false;
        nextTurn();
        while (!found) {
            if (!currentPlayer.getPass() && !currentPlayer.getWon()) {
                found = true;
            } else {
                nextTurn();
            }
        }
    }

    private void runAutoTurns() {
        Boolean confirm=false;
        while (!confirm) {
            System.out.println("heyo");
            currentPlayer = players.get(turnNo);
            if (!currentPlayer.getPass() && !currentPlayer.getWon()) {
                // if player is still playing
                if (currentPlayer instanceof Ai) {
                    // if next player is ai, run ai turn
                    runTurnAi();
                } else  {
                    // if player hasn't won or passed
                    confirm = true;
                    displayUserData();
                }
            }
            if (!confirm) {
                // if haven't found a human player and all other players are passed
                confirm = checkPassed();
            }
            invalidate();
            validate();
            repaint();
        }
        //check if the round has ended
        if (roundEnd) {
            startRound();
        } else {
            displayUserData();
        }
    }

    private void endGame() {
        playCardButton.setEnabled(false);
        selectionButton.setEnabled(false);
        confirmInputButton.setEnabled(false);
        passButton.setEnabled(false);
        displayWinners();
    }

    private void runTurnHuman() {
        confirm = false;
        // if player hasn't passed, player hasn't won game and round hasn't been won
        if (!currentPlayer.getPass() && !roundEnd && !currentPlayer.getWon()) {
            //store selected card
            confirm = checkMagnetite();
            if (!confirm) {
                processCard();
            }
        }
    }

    private Boolean checkMagnetite() {
        /* if player is first to play after a trump card (meaning they played it) */
        if ((turnNo == 0) && (lastCard instanceof TrumpCard)) {
            if (lastCard.getTitle().equals("The Geophysicist")) {
                if (currentCard.getTitle().equals("Magnetite")) {
                    addLog("<html>" + currentPlayer.getName() + " has won!" +
                            "<br>They played the trump card: 'The Geophysicist' " +
                            "<br>with the play card 'Magnetite'</html>");
                    playersWon.add(currentPlayer);
                    players.remove(currentPlayer);
                    return true;
                }
            }
        }
        return false;
    }

    private void createPlayers() {
        String[] playerNames = new String[5];
        Boolean[] playerAiCheck = new Boolean[5];
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
        playerLabels = new ArrayList<>();
        for (Player player : players) {
            playerLabels.add(new JLabel(player.getName(), SwingConstants.CENTER));
        }
        if (players.size() > 2) {
            createDeck();
            dealCards();
            initialiseGameGui();
        } else {
            infoLabel.setText("Invalid number of players.");
            invalidate();
            validate();
            repaint();
        }
    }

    private void displayHelp() {
        /* Displays how to play screen */
        helpLabel = new JLabel();
        nextHelp = new JButton("Next Page.");
        JFrame helpFrame = new JFrame();
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
    }

    private void getHelpCards() {
        int NO_HELP_CARDS = 4;
        helpCards = new ImageIcon[NO_HELP_CARDS];
        for (int i = 0; i < 4; i++) {
            try {
                URL imgURL = getClass().getResource("Slide6" + (i + 1) + ".jpg");
                helpCards[i] = new ImageIcon(imgURL);
                Image image = helpCards[i].getImage(); // transform it
                Image newimg = image.getScaledInstance(600, 700,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
                helpCards[i] = new ImageIcon(newimg);  // transform it back
            } catch (Exception e) {
                System.out.println("Couldn't find help file");
            }
        }
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
        addLog("The winning players in order are:");
        int x=0;
        for (Player player : playersWon) {
            ++x;
           addLog("(" + x + ") " + player.getName());
        }
    }

    private void createDeck() {
        //builds the deck
        deck = new Deck();
        deck.buildDeck();
    }

    private Boolean processCard() {
        /* Processes the card, decides whether to play it */
        if (currentCard instanceof TrumpCard) {
            //if trump
            playCard(currentCard);
            playerWonRound = currentPlayer;
            return true;
        } else {
            //if playcard
            if (lastCard instanceof PlayCard) {                         //if there is a last play card
                //if no last card
                Boolean indicator = ((PlayCard) currentCard).compareCards(
                        (PlayCard) lastCard, currentCategory); //compare cards
                if (indicator) {                            //if valid placement
                    playCard(currentCard);
                    return true;
                } else {
                    if (currentPlayer instanceof User) {
                        addLog("Error, card has lower value than the previous card.");
                    }
                    return false;
                }
            } else {
                //if there is no last play card
                playCard(currentCard);
                return true;
            }
        }
    }

    private void playCard(Card card) {
        /* Plays the card */
        currentPlayer.removeCard(card);                     //remove card from hand
        cardDisplayLabel.setIcon(new ImageIcon());
        invalidate();
        validate();
        repaint();
        lastCard = currentCard;
        //seperate methods for trump/play
        if (currentCard instanceof PlayCard) {
            field.addCard(currentCard);                            //add card to field
            addLog(currentPlayer.getName() + " played: " +
                    declareCard(currentCard));
        } else if (currentCard instanceof TrumpCard) {
            field.addCard(currentCard);
            Category category1 = ((TrumpCard) currentCard).getCategory();
            //change category unless its the geologist
            if (category1 != Category.GEOLOGIST) {
                currentCategory = category1;
            } else {
                //if geologist ask category
                askCategory();
            }
            addLog(currentPlayer.getName() + " played trump: " + currentCard.getTitle());
            addLog("Category has been changed to: " + currentCategory.toString());
            categoryLabel.setText("Category is: " + currentCategory);
            //reset player order
            //todo this is redundant
//            shiftArray(players, players.size() - (players.indexOf(currentPlayer)));
//            turnNo = 0;
        }
        currentCard = null;
        checkIfGameWinner();
        if (checkGameEnd()) {
            addLog("Game has Ended");
            endGame();
            invalidate();
            validate();
            repaint();
        } else {
            if (lastCard instanceof TrumpCard) {
                startRound();
            } else if (lastCard instanceof PlayCard) {
                findNextTurn();
            }
        }

    }

    private void askCategory() {
        if (currentPlayer instanceof Ai) {
            currentCategory = currentPlayer.askCategory();
            while (currentCategory == null) {
                currentCategory = currentPlayer.askCategory();
            }
            categoryLabel.setText("Current Category: " + currentCategory);
            addLog(currentPlayer.getName() + " changed the category to: " + currentCategory);
        } else {
            selectionButton.setEnabled(true);
            playCardButton.setEnabled(false);
            passButton.setEnabled(false);
            initialiseSelectionGui();
        }
        invalidate();
        validate();
        repaint();
    }


    private void storeCards() {
        if (!field.getCards().isEmpty()) {
            storedCards.addCards(field);
            field.getCards().clear();
        }
    }

    private void checkIfGameWinner() {
        if (currentPlayer.getCardsHand().size() == 0) {
            //if player has not already won
            if (!currentPlayer.getWon()) {
                addLog(currentPlayer.getName() + " has won!");
                playersWon.add(currentPlayer);
                currentPlayer.setWon(true);
            }
        }
    }

    private void checkRoundWinner() {
        //check for winner
        if (checkPassed()) {
            playerWonRound = getRoundWinner();
            addLog(playerWonRound.getName() + " Won the round!");
            // select winning player as starting player
            turnNo = players.indexOf(playerWonRound);
            roundEnd = true;
            currentCategory = null;
        }
    }

    private String declareCard(Card currentCard) {
        if (currentCategory == null) {
            // if not category is selected yet
            return currentCard.getTitle();
        } else {
            if (currentCard instanceof PlayCard) {
                return currentCard.getTitle() + ", " + currentCategory.toString().toLowerCase() +
                        ", (" + ((PlayCard) currentCard).getCategoryValue(currentCategory) + ")";
            } else {
                return currentCard.getTitle() + ", category: " + ((TrumpCard) currentCard).getCategory().toString();
            }
        }
    }

    private Boolean checkPassed() {
        // Checks to see if there is one player left to pass, returns true if there is
        ArrayList<Player> playersLeft = new ArrayList<>();
        for (Player player : players) {
            // account for players winning the game
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

    private void pass() {
        currentPlayer.setPass(true);
        //Player is dealt card when passing
        addLog(currentPlayer.getName() + " has decided to pass");
        invalidate();
        validate();
        repaint();
        //if deck isn't empty, deal card, else fill deck up with stored cards
        if (deck.getCards().isEmpty()) {
            deck.getCards().addAll(storedCards.getCards());
            storedCards.getCards().clear();
        }
        currentPlayer.getCardsHand().addAll(deck.dealCards(1));
        findNextTurn();
        checkRoundWinner();
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

}
