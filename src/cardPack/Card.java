package cardPack;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

import static cardPack.Rank.*;
import static cardPack.Suit.*;
import static cardPack.Suit.SPADES;

public class Card extends Canvas implements MouseListener {
    private final Suit suit;
    private final Rank rank;
    protected boolean visible;

    public Card(Suit suit, Rank rank, boolean v) {
        this.suit = suit;
        this.rank = rank;
        this.visible = v;
    }
    public Suit getSuit() {
        return suit;
    }
    public Rank getRank() {
        return rank;
    }
    public void setVisible(boolean v) {
        visible = v;
    }
    @Override
    public String toString() {
        String result = "";
        switch (suit) {
            case DIAMONDS -> result += "♦";
            case SPADES -> result += "♠";
            case CLUBS -> result += "♣";
            case HEARTS -> result += "♥";
            default -> {
            }
        }
        switch (rank) {
            case SIX -> result += "6";
            case SEVEN -> result += "7";
            case EIGHT -> result += "8";
            case NINE -> result += "9";
            case TEN -> result += "10";
            case JACK -> result += "J";
            case QUEEN -> result += "Q";
            case KING -> result += "K";
            case ACE -> result += "A";
            default -> {
            }
        }
        return result;
    }
    public static Rank stringToRank(String str){
        return switch (str) {
            case "6" -> SIX;
            case "7" -> SEVEN;
            case "8" -> EIGHT;
            case "9" -> NINE;
            case "10" -> TEN;
            case "J" -> JACK;
            case "Q" -> QUEEN;
            case "K" -> KING;
            case "A" -> ACE;
            default -> null;
        };
    }
    public static Suit stringToSuit(String str){
        return switch (str) {
            case "♥" -> HEARTS;
            case "♦" -> DIAMONDS;
            case "♣" -> CLUBS;
            case "♠" -> SPADES;
            default -> null;
        };
    }

    public static boolean isCard (String text){
        String[] splitText = text.split("");
        return (Objects.equals(splitText[0], "♣") || Objects.equals(splitText[0], "♦") || Objects.equals(splitText[0], "♠") || Objects.equals(splitText[0], "♥")) &&
                (Objects.equals(splitText[1], "6") || Objects.equals(splitText[1], "7") || Objects.equals(splitText[1], "8") ||
                        Objects.equals(splitText[1], "J") || Objects.equals(splitText[1], "K") || Objects.equals(splitText[1], "Q")
                        || Objects.equals(splitText[1], "9") || Objects.equals(splitText[1], "A") || (splitText.length >= 3 && Objects.equals(splitText[1], "1") && Objects.equals(splitText[2], "0")));
    }

    public static boolean compareCards(Card card1, Card card2) {
        return card1.getSuit() == card2.getSuit() && card1.getRank() == card2.getRank();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

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
