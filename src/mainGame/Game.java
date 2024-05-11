package mainGame;

import cardPack.RenderedCard;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private Table table;
    private List<String> history;
    private List<RenderedCard> cardForAttack;
    private List<RenderedCard> cardForDefence;

    public Game(Table table, List<String> history, List<RenderedCard> cardForAttack, List<RenderedCard> cardForDefence) {
        this.table = table;
        this.history = history;
        this.cardForAttack = cardForAttack;
        this.cardForDefence = cardForDefence;
    }

    public Table getTable() {
        return table;
    }

    public List<String> getHistory() {
        return history;
    }

    public List<RenderedCard> getCardForAttack() {
        return cardForAttack;
    }

    public List<RenderedCard> getCardForDefence() {
        return cardForDefence;
    }

    public void goGame(List<Player> players) {
        boolean availabilityOfCards = true;
        List<Player> winners = new ArrayList<>();
        table = new Table(players);
        table.dealCards();
        history = new ArrayList<>();
        cardForAttack = new ArrayList<>();
        cardForDefence = new ArrayList<>();
        for(Player pl : players){
            history = pl.displayHand();
        }
        RenderedCard trumpCard = table.getTrumpCard();
        for (Player p:players){
            p.getStrategy().setTrumpSuit(trumpCard.getSuit());
        }
        history.add("Карт в колоде " + table.getDeck().getCards().size());
        history.add("Козырная карта: " + trumpCard);
        Player firstPlayer = determineFirstPlayer(table.getTrumpCard(), players);
        history.add("Первым ходит " + firstPlayer.getName());
        int sws = players.indexOf(firstPlayer);
        while (players.size() > 1) {
            GameRound gg = new GameRound(players, table, players.get(sws % players.size()), players.get((sws + 1) % players.size()), history);
            cardForAttack = gg.getCardsForNextAttack();
            history = gg.play();
            if (availabilityOfCards) {
                history.add("Осталось "+ table.getDeck().getCards().size()+" карт в колоде");
            }
            for (int i = 0; i < players.size(); i++) {
                if (availabilityOfCards) {
                    while (players.get((sws + i) % players.size()).getHand().size()<6) {
                        if (!table.getDeck().getCards().isEmpty()) {
                            if(table.getDeck().getCard()==trumpCard){
                                players.get((sws + i) % players.size()).takeToTakenCards(table.getCard());
                            }
                            RenderedCard a = table.getDeck().drawCard();
                            players.get((sws + i) % players.size()).receiveCard(a);
                            history.add(players.get((sws + i) % players.size()).getName() + " взял карту "+ a);
                            players.get((sws + i) % players.size()).setHand(HelpingMethods.sortHand(players.get((sws + i) % players.size()).getHand(),trumpCard.getSuit() ));
                        } else {
                            availabilityOfCards = false;
//                            history.add("Карты кончились " + table.getDeck().getCards().size());
                            break;
                        }
                    }
                } else {
                    break;
                }
            }
            for (Player pl : players){
                history = pl.displayHand();
            }
            if (gg.isRoundActive()){
                sws = players.indexOf(gg.getDefendingPlayer());
            }else{
                sws = (sws+2) % players.size();
            }
            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).getHand().isEmpty()) {
                    winners.add(players.get(i));
//                    history.add(players.get(i).getName() + " вышла");
                    players.remove(i);
                    i--;
                }
            }
        }
        for(Player pl :players){
            List<RenderedCard> d = new ArrayList<>();
            pl.setHand(d);
        }
        history.add("Карт вышло: "+ table.cardsOut.size());
        if(players.size()==1){
//            history.add("Проиграл игрок: "+ players.get(0).getName());
        }
        history.add("Ничья");
    }
    public static Player determineFirstPlayer(RenderedCard trumpCard, List<Player> players) {
        Player firstPlayer = null;
        int targetRank = (trumpCard.getRank().ordinal() < 5) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (Player player : players) {
            for (RenderedCard card : player.getHand()) {
                if (card.getSuit() == trumpCard.getSuit()) {
                    int cardRank = card.getRank().ordinal();
                    if ((trumpCard.getRank().ordinal() < 5 && cardRank > targetRank) ||
                            (trumpCard.getRank().ordinal() >= 5 && cardRank < targetRank)) {
                        targetRank = cardRank;
                        firstPlayer = player;
                    }
                }
            }
        }
        if (firstPlayer == null) {
            int randomIndex = (int) (Math.random() * players.size());
            firstPlayer = players.get(randomIndex);
        }
        return firstPlayer;
    }
}
