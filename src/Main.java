import cardPack.*;
import mainGame.*;
import strategy.SmartStrategy;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import static cardPack.Card.*;
import static cardPack.RenderedCard.*;
import static mainGame.Player.isContains;

public class Main extends JFrame{
    static String[] names = {"DARIA1", "LISA2", "ANYA3", "LERA4", "LENA5", "ANGELINA6"};
    static int numPlayers = 0;
    private static List<String> history;
    static List<RenderedCard> cardForAttack;
    static List<RenderedCard> cardForDefence;
    static List<RenderedCard>[] cardForDefenceChangesThroughMoves;
    static List<RenderedCard>[] cardForAttackChangesThroughMoves;
    private ArrayList<RenderedCard> computerHand1;
    private ArrayList<RenderedCard> computerHand2;
    private ArrayList<RenderedCard> computerHand3;
    private ArrayList<RenderedCard> computerHand4;
    private ArrayList<RenderedCard> computerHand5;
    private ArrayList<RenderedCard> computerHand6;
    private ArrayList<RenderedCard>[] computerHandChangesThroughMoves1;
    private ArrayList<RenderedCard>[] computerHandChangesThroughMoves2;
    private ArrayList<RenderedCard>[] computerHandChangesThroughMoves3;
    private ArrayList<RenderedCard>[] computerHandChangesThroughMoves4;
    private ArrayList<RenderedCard>[] computerHandChangesThroughMoves5;
    private ArrayList<RenderedCard>[] computerHandChangesThroughMoves6;
    private static Table table;
    private JPanel gamePanel;
    private JPanel computerPanel1;
    private JPanel computerPanel2;
    private JPanel computerPanel3;
    private JPanel computerPanel4;
    private JPanel computerPanel5;
    private JPanel computerPanel6;
    private JPanel tablePanel;
    private JPanel tableAttackPanel;
    private JPanel tableDefencePanel;
    private JPanel sidePanel;
    private JButton nextButton;
    private JButton prevButton;
    private JButton startButton;
    private int currentTurnIndex = 0;
    private int[] turnIndexes;
    private int indexForTurnIndexes = 0;
    private JPanel buttonPanel;
    private JLabel infoLabel;
    private JLabel Player1;
    private JLabel Player2;
    private JLabel Player3;
    private JLabel Player6;
    private JLabel Player5;
    private JLabel Player4;
    private static JSpinner spinner;
    private JPanel spinnerPanel;
    private String[] infoLabelTroughMoves;
    public Main() {
        super("Durak");
        setIconImage(new ImageIcon("picture_data/♥️K.gif").getImage());

        initFrame();
        setVisible(true);
    }

    private void initFrame() {
        setTitle("Карточная игра");
        this.setContentPane(gamePanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();

        computerPanel1.setBackground(new Color(153, 153, 255));
        computerPanel1.setToolTipText("DARIA1");
        computerPanel2.setBackground(new Color(153, 153, 255));
        computerPanel2.setToolTipText("LISA2");
        computerPanel3.setBackground(new Color(153, 153, 255));
        computerPanel3.setToolTipText("ANYA3");
        computerPanel4.setBackground(new Color(153, 153, 255));
        computerPanel4.setToolTipText("LERA4");
        computerPanel5.setBackground(new Color(153, 153, 255));
        computerPanel5.setToolTipText("LENA5");
        computerPanel6.setBackground(new Color(153, 153, 255));
        computerPanel6.setToolTipText("ANGELINA6");

        tablePanel.setLayout(new GridLayout(2, 6, 1, 1));
        tableAttackPanel.setLayout(new GridLayout(1, 6, 1, 1));
        tableDefencePanel.setLayout(new GridLayout(1, 6, 1, 1));
        sidePanel.setLayout((new GridLayout(1,1,1,1)));
        spinnerPanel.setLayout((new GridLayout(1,1,1,1)));
        tablePanel.setBackground(new Color(204, 153, 255));
        tableAttackPanel.setBackground(new Color(204, 153, 255));
        tableDefencePanel.setBackground(new Color(204, 153, 255));
        sidePanel.setBackground(new Color(204, 153, 255));
        gamePanel.setBackground(new Color(102,102,255));

        SpinnerModel numberOfPlayers = new SpinnerNumberModel(2, 2, 6, 1);
        spinner = new JSpinner(numberOfPlayers);
        spinnerPanel.add(spinner);

        initButtons();

    }

    private void initButtons() {
        startButton.addActionListener(e -> startGame());

        nextButton.addActionListener(e -> {
            if (currentTurnIndex < history.size() - 1) {
                displayMove(currentTurnIndex);
            }else {
                infoLabel.setText("Это последний ход! Смотрите предыдущие ходы или начните новую игру!");
            }
        });

        prevButton.addActionListener(e -> {
            if (currentTurnIndex > 0) {
                displayPreviousMove(currentTurnIndex);
            }
        });
    }

    public void startGame() {
        history = new ArrayList<>();
        List<Player> players = input();
        game(players);
        initCards();

        setResizable(true);
        resetAll();
        displayMove(currentTurnIndex);
    }

    public static List<Player> input() {
        numPlayers = (Integer) spinner.getValue();
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            String playerName = names[i];
            Player currentPlayer = new Player(playerName, history);
            currentPlayer.setStrategy(new SmartStrategy());
            players.add(currentPlayer);
        }
        return players;
    }
    public void game(List<Player> players){
        Game game = new Game(table, history, cardForAttack, cardForDefence);
        game.goGame(players);
        table = game.getTable();
        history = game.getHistory();
        cardForAttack = game.getCardForAttack();
        cardForDefence = game.getCardForDefence();
    }
    public void resetAll(){
        updateFrame();
        computerPanel1.removeAll();
        computerPanel2.removeAll();
        computerPanel3.removeAll();
        computerPanel4.removeAll();
        computerPanel5.removeAll();
        computerPanel6.removeAll();
        tableAttackPanel.removeAll();
        tableDefencePanel.removeAll();
        sidePanel.removeAll();
        currentTurnIndex = 0;
        indexForTurnIndexes = 0;
    }

    private void initCards() {
        computerHand1 = new ArrayList<>();
        computerHand2 = new ArrayList<>();
        computerHand3 = new ArrayList<>();
        computerHand4 = new ArrayList<>();
        computerHand5 = new ArrayList<>();
        computerHand6 = new ArrayList<>();
        computerHandChangesThroughMoves1 = (ArrayList<RenderedCard>[]) new ArrayList[history.size()];
        computerHandChangesThroughMoves2 = (ArrayList<RenderedCard>[]) new ArrayList[history.size()];
        computerHandChangesThroughMoves3 = (ArrayList<RenderedCard>[]) new ArrayList[history.size()];
        computerHandChangesThroughMoves4 = (ArrayList<RenderedCard>[]) new ArrayList[history.size()];
        computerHandChangesThroughMoves5 = (ArrayList<RenderedCard>[]) new ArrayList[history.size()];
        computerHandChangesThroughMoves6 = (ArrayList<RenderedCard>[]) new ArrayList[history.size()];
        cardForAttack = new ArrayList<>();
        cardForDefence = new ArrayList<>();
        cardForAttackChangesThroughMoves = (ArrayList<RenderedCard>[]) new ArrayList[history.size()];
        cardForDefenceChangesThroughMoves = (ArrayList<RenderedCard>[]) new ArrayList[history.size()];
        infoLabelTroughMoves = new String[history.size()];
        turnIndexes = new int[history.size()];
    }

    private void displayMove(int turnIndex) {

        String moveInfo = history.get(turnIndex);
        while (moveInfo.contains("Карты в руке игрока")){
            ArrayList<String> hand = new ArrayList<>();
            int index = 1;
            while (isCard(history.get(turnIndex+index))){
                hand.add(history.get(turnIndex+index));
                index ++;
            }
            parseAndDisplay(moveInfo, hand);
            currentTurnIndex = turnIndex + index - 1;
            turnIndex = currentTurnIndex + 1;
            moveInfo = history.get(turnIndex);
        }
        if (!moveInfo.contains("Карты в руке игрока")){
            ArrayList<String> hand = new ArrayList<>();
            parseAndDisplay(moveInfo, hand);
            currentTurnIndex = turnIndex + 1;
        }
        turnIndexes[indexForTurnIndexes] = currentTurnIndex;
        indexForTurnIndexes ++;
        saveMovesToLists(indexForTurnIndexes);
    }

    private void displayPreviousMove(int turnIndex) {
        if (indexForTurnIndexes > 1){
            indexForTurnIndexes --;
            currentTurnIndex = turnIndex - 1;
            setMovesFromLists(indexForTurnIndexes);
            updateFrame();
            revalidate();
            repaint();
        }else{
            infoLabel.setText("Это самый первый ход! Смотрите следующие ходы!");
        }
    }
    public void saveMovesToLists(int turnIndex){
        for (int i = 0; i < numPlayers; i++){
            ArrayList<RenderedCard>[] hands = getHandChangesThroughMovesByName(names[i]);
            hands[turnIndex] = new ArrayList<>(getHandByName(names[i]));
        }
        cardForAttackChangesThroughMoves[turnIndex] = new ArrayList<>(cardForAttack);
        cardForDefenceChangesThroughMoves[turnIndex] = new ArrayList<>(cardForDefence);
        infoLabelTroughMoves[turnIndex] = infoLabel.getText();
    }

    public void setMovesFromLists(int turnIndex){
        for (int i = 0; i < numPlayers; i++){
            ArrayList<RenderedCard> hand = getHandByName(names[i]);
            hand.clear();
            ArrayList<RenderedCard>[] handChanges = getHandChangesThroughMovesByName(names[i]);
            hand.addAll(handChanges[turnIndex]);
        }
        cardForAttack.clear();
        cardForDefence.clear();
        cardForAttack.addAll(cardForAttackChangesThroughMoves[turnIndex]);
        cardForDefence.addAll(cardForDefenceChangesThroughMoves[turnIndex]);
        infoLabel.setText(infoLabelTroughMoves[turnIndex]);
    }

    private void parseAndDisplay(String moveInfo, ArrayList<String> hand) {
        tableAttackPanel.removeAll();
        tableDefencePanel.removeAll();

        if (moveInfo.contains("Карт в колоде")) {
            updateDeckInfo(moveInfo);
        } else if (moveInfo.contains("Карты в руке игрока")) {
            displayCards(moveInfo, hand);
        } else if (moveInfo.contains("Козырная карта")) {
            updateTrumpCard(moveInfo);
        } else if (moveInfo.contains("взяла карту")) {
            updatePlayerHand(moveInfo);
            infoLabel.setText(moveInfo);
        } else if (moveInfo.contains("Карты кончились")) {
            updateDeckEmpty(moveInfo);
        } else if (moveInfo.contains("вышла")) {
            playerExited(moveInfo);
        } else if (moveInfo.contains("Карт вышло")) {
            updateCardsOut();
        } else if (moveInfo.contains("Проиграл игрок")) {
            updateLoser(moveInfo);
        } else if (moveInfo.contains("Ничья")) {
            displayDraw();
        } else if (moveInfo.contains("атаковала")) {
            updateAttack(moveInfo);
            infoLabel.setText(moveInfo);
        } else if (moveInfo.contains("побилась")) {
            updateDefense(moveInfo);
            infoLabel.setText(moveInfo);
        } else if (moveInfo.contains("затянула")) {
            playerTookCards(moveInfo);
            infoLabel.setText(moveInfo);
        } else if (moveInfo.contains("подбросила")) {
            updateThrowCard(moveInfo);
            infoLabel.setText(moveInfo);
        }

        updateFrame();
        revalidate();
        repaint();
    }

    public void displayCards(String text, ArrayList<String> hand){
        String playerName = text.split(" ")[4];

        ArrayList<RenderedCard> playerHand = getHandByName(playerName);

        for (String s : hand) {
            RenderedCard card = createRenderedCard(s);
            if (!isContains(playerHand, card)) {
                playerHand.add(card);
            }
        }
    }

    private void updateDeckInfo(String text) {
        String[] parts = text.split(" ");
        infoLabel.setText("Карт в колоде: " + parts[3]);
    }

    private void updateTrumpCard(String text) {
        String trumpString = text.split(" ")[2];
        RenderedCard trumpCard = createRenderedCard(trumpString);
        sidePanel.add(trumpCard);
    }

    private void updatePlayerHand(String text) {
        String playerName = text.split(" ")[0];
        String stringCard = text.substring(text.lastIndexOf(" ") + 1);
        ArrayList<RenderedCard> hand = getHandByName(playerName);
        RenderedCard card = createRenderedCard(stringCard);
        hand.add(card);
    }

    private void playerExited(String text) {
        String playerName = text.split(" ")[0];
        infoLabel.setText(playerName + " вышла из игры");
    }

    private void updateAttack(String text) {
        String playerName = text.split(" ")[0];
        String stringCard = text.split(" ")[2];
        RenderedCard card = createRenderedCard(stringCard);
        cardForAttack.add(card);
        removeCard(playerName, card);
    }

    private void updateDefense(String text) {
        String playerName = text.split(" ")[0];
        String stringCard = text.split(" ")[2];
        RenderedCard card = createRenderedCard(stringCard);
        cardForDefence.add(card);
        removeCard(playerName, card);
    }

    private void updateThrowCard(String text) {
        String playerName = text.split(" ")[0];
        String stringCard = text.split(" ")[2];
        RenderedCard card = createRenderedCard(stringCard);
        cardForAttack.add(card);
        removeCard(playerName, card);
    }

    private void updateDeckEmpty(String text) {
        infoLabel.setText(text);
    }

    private void updateCardsOut() {
        cardForAttack.clear();
        cardForDefence.clear();
    }

    private void updateLoser(String text) {
        String playerName = text.split(": ")[1];
        infoLabel.setText("Проиграл игрок: " + playerName);
    }

    private void displayDraw() {
        infoLabel.setText("Игра закончилась ничьей");
    }

    private void playerTookCards(String text) {
        String playerName = text.split(" ")[0];
        ArrayList<RenderedCard> hand = getHandByName(playerName);
        hand.addAll(cardForAttack);
        cardForAttack.clear();
        hand.addAll(cardForDefence);
        cardForDefence.clear();
    }
    public void removeCard(String playerName, RenderedCard card){
        ArrayList<RenderedCard> hand = getHandByName(playerName);
        for (int i = 0; i < hand.size(); i++){
            if (compareCards(hand.get(i), card)){
                hand.remove(i);
            }
        }
    }
    private JPanel getPanelByName(String playerName) {
        return switch (playerName) {
            case "DARIA1" -> computerPanel1;
            case "LISA2" -> computerPanel2;
            case "ANYA3" -> computerPanel3;
            case "LERA4" -> computerPanel4;
            case "LENA5" -> computerPanel5;
            case "ANGELINA6" -> computerPanel6;
            default -> null;
        };
    }

    public ArrayList<RenderedCard> getHandByName(String playerName) {
        return switch (playerName) {
            case "DARIA1" -> computerHand1;
            case "LISA2" -> computerHand2;
            case "ANYA3" -> computerHand3;
            case "LERA4" -> computerHand4;
            case "LENA5" -> computerHand5;
            case "ANGELINA6" -> computerHand6;
            default -> null;
        };
    }
    public ArrayList<RenderedCard>[] getHandChangesThroughMovesByName(String playerName) {
        return switch (playerName) {
            case "DARIA1" -> computerHandChangesThroughMoves1;
            case "LISA2" -> computerHandChangesThroughMoves2;
            case "ANYA3" -> computerHandChangesThroughMoves3;
            case "LERA4" -> computerHandChangesThroughMoves4;
            case "LENA5" -> computerHandChangesThroughMoves5;
            case "ANGELINA6" -> computerHandChangesThroughMoves6;
            default -> null;
        };
    }

    public void updateFrame() {
        for (int i = 0; i < numPlayers; i++){
            showComputerCards(getPanelByName(names[i]),getHandByName(names[i]));
        }
        showTableCards();
    }

    protected void showTableCards() {
        tableAttackPanel.removeAll();
        tableDefencePanel.removeAll();
        if (cardForAttack != null) {
            for (RenderedCard temp : cardForAttack) {
                temp.setVisible(true);
                tableAttackPanel.add(temp);
            }
        }
        if (cardForDefence != null){
            for (RenderedCard temp : cardForDefence) {
                temp.setVisible(true);
                tableDefencePanel.add(temp);
            }
        }
    }

    protected void showComputerCards(JPanel computerPanel, ArrayList<RenderedCard> computerHand) {
        computerPanel.removeAll();
        for (RenderedCard temp : computerHand) {
            if (temp.isVisible()) {
                if (!Arrays.asList(computerPanel.getComponents()).contains(temp)) {
                    computerPanel.add(temp);
                }
            }
        }
    }

    public void setVisible(boolean visible) {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((d.width - getWidth()) / 2, (d.height - getHeight()) / 2);
        super.setVisible(visible);
    }
    public static void main(String[] args) {
        new Main();
    }
}