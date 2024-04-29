package mainGame;
import cardPack.Card;
import cardPack.Rank;
import cardPack.RenderedCard;
import cardPack.Suit;
import java.util.*;
public class HelpingMethods {
    @SafeVarargs
    public static List<RenderedCard> cardsWithRank(RenderedCard card, List<RenderedCard>... opponentCards) {
        List<RenderedCard> res = new ArrayList<>();
        for (List<RenderedCard> opponent : opponentCards) {
            for (RenderedCard opponentCard : opponent) {
                if (opponentCard.getRank() == card.getRank()) {
                    res.add(opponentCard);
                }
            }
        }
        return res;
    }
    public static Set<Rank> getUniqueRanks(List<RenderedCard> cards) {
        Set<Rank> uniqueRanks = new HashSet<>();
        for (RenderedCard card : cards) {
            uniqueRanks.add(card.getRank());
        }
        return uniqueRanks;
    }
    public static boolean canBeatCard(RenderedCard card, RenderedCard recaptureCard, Suit trumpSuit) {
        if (card.getSuit() == recaptureCard.getSuit()) {
            return card.getRank().ordinal() > recaptureCard.getRank().ordinal();
        } else return card.getSuit() == trumpSuit; // Козырная карта всегда бьет
    }
    public static List<RenderedCard> sortHand(List<RenderedCard> hand, Suit trumpSuit) {
        if(hand.size()<2){
            return hand;
        }
        // Создаем компаратор для сортировки карт
        Comparator<Card> cardComparator = (card1, card2) -> {
            if (card1.getSuit() != trumpSuit && card2.getSuit() != trumpSuit) {
                // Если обе карты не являются козырями, сравниваем их ранг
                return Integer.compare(card1.getRank().ordinal(), card2.getRank().ordinal());
            } else if (card1.getSuit() == trumpSuit && card2.getSuit() == trumpSuit) {
                // Если обе карты являются козырями, сравниваем их ранг
                return Integer.compare(card1.getRank().ordinal(), card2.getRank().ordinal());
            } else if (card1.getSuit() != trumpSuit) {
                // Если только card1 не является козырем, она идет первой
                return -1;
            } else {
                // Иначе card2 не является козырем, она идет первой
                return 1;
            }
        };
        hand.sort(cardComparator); // Сортируем массив карт, используя созданный компаратор
        return hand;
    }





    public static List<RenderedCard> ThrowCard( List<RenderedCard> me, List<RenderedCard> desc, Suit trumpSuit) {
        List<RenderedCard> possibleCards = new ArrayList<>();// здесь карты, которые в теории мы можем подкинуть
        Set<Rank> unRanks = getUniqueRanks(desc);
        for (RenderedCard card : me) {
            if (unRanks.contains(card.getRank()) ) {
                possibleCards.add(card);
            }
        }
        sortHand(possibleCards, trumpSuit);
        if(!possibleCards.isEmpty()){
            return possibleCards;
        }
        return null;
    }



}
