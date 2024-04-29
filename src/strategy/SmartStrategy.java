package strategy;
import cardPack.Rank;
import cardPack.RenderedCard;
import mainGame.HelpingMethods;
import mainGame.Player;
import cardPack.Suit;
import mainGame.Table;
import java.util.*;
import static mainGame.HelpingMethods.*;
public class SmartStrategy implements IStrategy {
    private String nameStrategy = "Продвинутый уровень игры";
    public String getNameStrategy() {
        return nameStrategy;
    }
    private Suit trumpSuit;
    public void setTrumpSuit(Suit trumpSuit) {
        this.trumpSuit = trumpSuit;
    }
    public RenderedCard attack(Player me, Player defPlayer, Table table) {
        me.setHand(sortHand(me.getHand(),trumpSuit));
        if (table.getDeck().getCards().isEmpty()){
            List <RenderedCard> hhh = new ArrayList<>();
            hhh.addAll(defPlayer.getHand());
            System.out.println("карты противника "+ hhh.size());
            switch (table.players.size()){
                case 2:
                    if(me.getHand().size()==1){
                        System.out.println("у меня одна карта ее и выброшу");
                        return me.getHand().remove(0);
                    }
                    if(hhh.size()==1){
                        System.out.println("у него одна карта попробую закдиать его");
                        for(RenderedCard j:me.getHand()){
                            if(HelpingMethods.canBeatCard(hhh.get(0),j,trumpSuit)){
                                me.getHand().remove(j);
                                return j;
                            }
                        }
                    }
                    if(me.getHand().size() == 2){

                        for(RenderedCard card1 : me.getHand()){
                            for(RenderedCard card2:hhh){
                                if(!HelpingMethods.canBeatCard(card2,card1,trumpSuit)){
                                    me.getHand().remove(card1);
                                    System.out.println("у меня две  карты, выброшу по возможности ту, которую он не побьет.");
                                    return card1;
                                }
                            }
                        }
                        return me.getHand().remove(1);
                    }
                    List<RenderedCard> cardsInTable = new ArrayList<>();
                    for(RenderedCard card1 : me.getHand()){
                        List<RenderedCard> others = new ArrayList<>(me.getHand());
                        List<RenderedCard> othersAt = new ArrayList<>(hhh);
                        RenderedCard card2 = null;
                        for(RenderedCard card20000:hhh) {
                            if(HelpingMethods.canBeatCard(card20000,card1,trumpSuit)){
                                card2 = card20000;
                                others.remove(card1);
                                othersAt.remove(card2);
                                break;
                            }
                        }
                        if(card2!= null && HelpingMethods.canBeatCard(card2,card1,trumpSuit)
                                && (HelpingMethods.getUniqueRanks(others).contains(card2.getRank())//здесь мы не убираем сами карты, и поэтому сюда всегда заходят
                                || HelpingMethods.getUniqueRanks(others).contains(card1.getRank())) ){//если карта 2 бьети карту 1 и появляется возможность подбросить
                            cardsInTable.add(card1);
                            cardsInTable.add(card2);
                            RenderedCard card121 = null;
                            List<RenderedCard> posAttCards = HelpingMethods.ThrowCard(others,cardsInTable,trumpSuit);//здесь мы получаем массив карт, которые мы можем подкинуть
                            if (posAttCards!=null && othersAt.size()>1){
                                for(RenderedCard card111 : posAttCards){
                                    boolean flag = true;
                                    for(RenderedCard card22 : othersAt) {
                                        if (HelpingMethods.canBeatCard(card22,card111,trumpSuit)) {//здесь нужно ввести флаг и если он никакой не может побиться
                                            flag = false;
                                            card121 = card22;//карта 121 та карта, которой побьется чел второй раз и ее надо исключить
                                            cardsInTable.add(card22);
                                            cardsInTable.add(card111);// это к
                                            break;
                                        }
                                    }
                                    if (flag){
                                        System.out.println("я кину карту " + card1 + "чтобы вышла карта" + card2 +" и кину карту "+ card111.toString() + " которую он побить не сможет");
                                        me.getHand().remove(card1);
                                        return card1;
                                    }else{
                                        List<RenderedCard> others2 = others;
                                        others2.remove(card111);
                                        List<RenderedCard> othersAt2 = othersAt;
                                        othersAt2.remove(card121);
                                        posAttCards = HelpingMethods.ThrowCard(others2,cardsInTable,trumpSuit);
                                        if (posAttCards!=null && !othersAt2.isEmpty()){
                                            for(RenderedCard card1111 : posAttCards){
                                                boolean flag2 = true;
                                                for(RenderedCard card222:othersAt2){
                                                    if(HelpingMethods.canBeatCard(card222,card1111,trumpSuit)
                                                            && card1111!=card111 && card1111!=card1 && card222!=card2
                                                                && card121!=card222){
                                                        flag2 = false;
                                                        break;
                                                    }
                                                }
                                                if(flag2){
                                                    System.out.println("я кину карту " + card1 + "чтобы вышли карты" + card2 + card121 + card111 + " и кину карту " + card1111.toString() + " которую он побить не сможет");
                                                    me.getHand().remove(card1);
                                                    return card1;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return me.getHand().remove(0);
                default:
                    return me.getHand().remove(0);
            }
        }
        return me.getHand().remove(0);
    }
    public RenderedCard defence(Player me, RenderedCard recaptureCard, Table table, List<RenderedCard> cardsInTable, List<RenderedCard> cardsForAttack){// стандартный алгоритм
        List<RenderedCard> possibleCardsToPlay = new ArrayList<>();
        for (RenderedCard card : me.getHand()) {// Создаем список карт, которыми можно побить карту recaptureCard
            if (canBeatCard(card, recaptureCard, trumpSuit)) {
                possibleCardsToPlay.add(card);
            }
        }
        if (!possibleCardsToPlay.isEmpty()) {
            me.getHand().remove(possibleCardsToPlay.get(0));
            return possibleCardsToPlay.get(0);
        } else {
            return null; // Если нет подходящих карт, вернуть null
        }
    }
    public RenderedCard throwCard(Player me, List<RenderedCard> desc, List<Player> players, List<RenderedCard> cardsOut) {
        List<RenderedCard> possibleCards = new ArrayList<>();
        Set<Rank> unRanks = getUniqueRanks(desc);
        for (RenderedCard card : me.getHand()) {
            if (unRanks.contains(card.getRank())) {
                possibleCards.add(card);
            }
        }
        possibleCards = sortHand(possibleCards, trumpSuit);
        for (RenderedCard card : possibleCards) {
            if (me.getHand().indexOf(card) + 1 / me.getHand().size() < 0.68) {
                me.getHand().remove(card);
                return card;
            }
            if (me.getHand().size() == 1){
                System.out.println("подбрасываю и выхожу");
                me.getHand().remove(card);
                return card;
            }
        }
        if (players.size()==2 && cardsOut.size()>=24){
            for (RenderedCard card : possibleCards) {
                me.getHand().remove(card);
                return card;
            }
        }
        return null;
    }

    public boolean areThrowCard(Player me, List<RenderedCard> desc, List<Player> players, List<RenderedCard> cardsOut) {
        List<RenderedCard> possibleCards = new ArrayList<>();
        Set<Rank> unRanks = getUniqueRanks(desc);
        for (RenderedCard card : me.getHand()) {
            if (unRanks.contains(card.getRank())) {
                possibleCards.add(card);
            }
        }
        possibleCards = sortHand(possibleCards, trumpSuit);
        for (RenderedCard card : possibleCards) {
            if (me.getHand().indexOf(card) + 1 / me.getHand().size() < 0.68) {
                return true;
            }
            if (me.getHand().size() == 1){
                System.out.println("подбрасываю и выхожу");
                return true;
            }
        }
        if (players.size()==2 && cardsOut.size()>=24){
            for (RenderedCard card : possibleCards) {
                return true;
            }
        }
        return false;
    }
    public RenderedCard deff2(Player me, RenderedCard recaptureCard,  Table table) {
        List<RenderedCard> opponentCards = me.getOpponentsCards(table.players);
        if(opponentCards.isEmpty()){
            return deff2(me,recaptureCard, table);
        }
        List<RenderedCard> possibleCardsToPlay = new ArrayList<>();
        // Создаем список карт, которыми можно побить карту recaptureCard
        for (RenderedCard card : me.getHand()) {
            if (canBeatCard(card, recaptureCard, trumpSuit)) {
                possibleCardsToPlay.add(card);
            }
        }
        Comparator<RenderedCard> cardComparator = new Comparator<RenderedCard>() { // Создаем компаратор для сортировки по количеству карт у оппонентов с тем же рангом
            @Override
            public int compare(RenderedCard card1, RenderedCard card2) {
                int count1 = cardsWithRank(card1, opponentCards).size();
                int count2 = cardsWithRank(card2, opponentCards).size();
                return Integer.compare(count1, count2);
            }
        };
        possibleCardsToPlay = sortHand(possibleCardsToPlay,trumpSuit); // Сортируем массив возможных карт для хода
        Collections.sort(possibleCardsToPlay, cardComparator);
        if (!possibleCardsToPlay.isEmpty()) {
            me.getHand().remove(possibleCardsToPlay.get(0));
            return possibleCardsToPlay.get(0); // Возвращаем карту с наименьшим количеством карт у оппонентов с тем же рангом
        } else {
            return null; // Если нет подходящих карт, вернуть null
        }
    }
}
