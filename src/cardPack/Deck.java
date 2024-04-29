package cardPack;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Deck {
    private final List<RenderedCard> cards;
    public List<RenderedCard> getCards() {
        return cards;
    }
    public Deck() {
        cards = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new RenderedCard(suit, rank, true));
            }
        }
        Collections.shuffle(cards);
    }
    public RenderedCard drawCard() {
        if (!cards.isEmpty()) {
            return cards.remove(0);
        }
        return null;
    }
    public RenderedCard getCard() {
        if (!cards.isEmpty()) {
            return cards.get(0);
        }
        return null;
    }
}
