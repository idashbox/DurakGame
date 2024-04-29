package mainGame;
import cardPack.Deck;
import cardPack.RenderedCard;

import java.util.ArrayList;
import java.util.List;
public class Table {
    private final Deck deck;
    public Deck getDeck() {
        return deck;
    }
    public List<Player> players;
    private RenderedCard trumpCard;
    public List<RenderedCard> cardsOut;

    public Table(List<Player> players) {
        this.players = players;
        deck = new Deck();
        cardsOut = new ArrayList<>();
        trumpCard = deck.drawCard(); // Определение козыря
        deck.getCards().add(trumpCard); // Перемещаем козырную карту в конец колоды
    }
    public void dealCards() {
        for (Player player : players) {
            for (int i = 0; i < 6; i++) {
                RenderedCard card = deck.drawCard();
                if(deck.getCards().isEmpty()){
                    trumpCard = card;
                    player.takeToTakenCards(card);
                }
                player.receiveCard(card);
            }
            player.setHand(HelpingMethods.sortHand(player.getHand(), trumpCard.getSuit()));
        }
    }
    public RenderedCard getCard() {
        return deck.getCard();
    }
    public RenderedCard getTrumpCard() {
        return trumpCard;
    }
}
