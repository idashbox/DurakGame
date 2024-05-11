package strategy;
import cardPack.RenderedCard;
import mainGame.Player;
import cardPack.Suit;
import mainGame.Table;

import java.util.List;


public interface IStrategy {
    String getNameStrategy();
    RenderedCard attack(Player me, Player defPlayer, Table table);
    void setTrumpSuit(Suit trumpSuit);
    RenderedCard defence(Player me, RenderedCard recaptureCard, Table table, List<RenderedCard> cardsInTable, List<RenderedCard> cardsForAttack);
    RenderedCard throwCard(Player me, List<RenderedCard> desc, List<Player> players, List<RenderedCard> cardsOut);
    boolean areThrowCard(Player me, List<RenderedCard> desc, List<Player> players,List<RenderedCard> cardsOut);
}