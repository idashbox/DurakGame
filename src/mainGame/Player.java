package mainGame;
import cardPack.RenderedCard;
import strategy.IStrategy;

import java.util.*;
import java.util.List;

import static cardPack.Card.compareCards;

public class  Player {
    String name;
    private List<RenderedCard> hand;
    private IStrategy strategy;
    public IStrategy getStrategy() {return strategy;}
    public void setStrategy(IStrategy strategy) {
        this.strategy = strategy;
    }
    List<RenderedCard> takenCards;
    List<String> history;
    public void setHand(List<RenderedCard> hand) {
        this.hand = hand;
    }
    public Player(String name, List<String> history) {
        this.name = name;
        hand = new ArrayList<>();
        takenCards = new ArrayList<>();
        this.history = history;
    }
    public List<String> displayHand() {
        System.out.println("Карты в руке игрока " + name + ":");
        history.add("Карты в руке игрока " + name);
        for (RenderedCard card : hand) {
            System.out.print(card.toColorString());
            history.add(card.toString());
            card.setVisible(true);
            card.setImage();
        }
        System.out.println();
        return history;
    }
    public void receiveCard(RenderedCard card) {
        hand.add(card);
    }
    public void takeCards(List<RenderedCard> cards) {
        hand.addAll(cards);
        takenCards.addAll(cards);
    }
    public void takeToTakenCards(RenderedCard card) {
        takenCards.add(card);
    }
    public List<RenderedCard> getHand() {
        return hand;
    }
    public List<RenderedCard> getTakenCards() {
        return takenCards;
    }
    public String getName() {
        return name;
    }
    public List<RenderedCard> getOpponentsCards(List<Player> players){
        List<RenderedCard> val = new ArrayList<>();
        for(Player player:players){
            val.addAll(player.getTakenCards());
        }
        val.removeAll(hand);
        return val;
    }
    public static boolean isContains(ArrayList<RenderedCard> hand, RenderedCard card){
        for (RenderedCard handCard: hand){
            if (compareCards(handCard, card)){
                return true;
            }
        }
        return false;
    }
}
