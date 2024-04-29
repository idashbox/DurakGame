package mainGame;
import cardPack.RenderedCard;

import java.util.ArrayList;
import java.util.List;
import static mainGame.HelpingMethods.sortHand;
public class GameRound {
    public List<RenderedCard> cardsIn;
    public List<RenderedCard> cardsForAttack = new ArrayList<>();
    private final List<Player> players;
    private final Table table;
    private final Player attackPlayer;
    private final Player defPlayer;
    private boolean status;
    private final int countDefCards;
    private int countThrowingCards;
    private final List<String> history;
    public GameRound(List<Player> players, Table table, Player attackPlayer, Player defPlayer, List<String> history) {
        this.players = players;
        this.table = table;
        this.attackPlayer = attackPlayer;
        this.defPlayer = defPlayer;
        this.status = true;
        this.countDefCards = defPlayer.getHand().size();
        this.history = history;
    }
    public List<RenderedCard> getCardsForAttack() {
        return cardsForAttack;
    }
    public Player getDefPlayer() {
        return defPlayer;
    }
    public boolean isStatus() {
        return status;
    }
    public List<String> play() {
        cardsIn = new ArrayList<>();
        RenderedCard attackCard;
        attackCard = attackPlayer.getStrategy().attack(attackPlayer, defPlayer,table); // Ход атакующего игрока
        System.out.println(attackPlayer.getName() + " атаковала " + attackCard.toColorString());
        history.add(attackPlayer.getName() + " атаковала " + attackCard);
        cardsIn.add(attackCard); // Карта атаки заносится в cardsIn
        cardsForAttack.add(attackCard); // Здесь мы помещаем карту, и отсюда достаем, когда бьем
        countThrowingCards = 1; //походили одной картой
        fillMassiveAttack();

        while (true) {
            cardsForAttack = HelpingMethods.sortHand(cardsForAttack, table.getTrumpCard().getSuit());//сортируем карты и бьем по порядку
            for (RenderedCard card : cardsForAttack) {
                RenderedCard defCard = defPlayer.getStrategy().defence(defPlayer, card, table, cardsIn, cardsForAttack);
                if (defCard != null) {
                    cardsIn.add(defCard);
                    System.out.println(defPlayer.getName() + " побилась " + defCard);
                    history.add(defPlayer.getName() + " побилась " + defCard);
                } else {
                    status = false;
                    defPlayer.takeCards(cardsIn);
                    cardsForAttack.clear();
                    System.out.println(defPlayer.getName() + " затянула");
                    history.add(defPlayer.getName() + " затянула");
                    System.out.println();
                    break;
                }
            }
            if (status) {
                cardsForAttack.clear();
            } else {
                break;
            }
            if (isRoundOver()) {//обнуление необходимого для следующей итерации
                table.cardsOut.addAll(cardsIn);
                for(Player pl:players){
                    pl.setHand(sortHand(pl.getHand(),table.getTrumpCard().getSuit()));
                }
                System.out.println("Карт вышло :" + table.cardsOut.size());
                history.add("Карт вышло :" + table.cardsOut.size());
                System.out.println("Карт в колоде " + table.getDeck().getCards().size());
                history.add("Карт в колоде " + table.getDeck().getCards().size());
                System.out.println();
                break;
            } else {
                fillMassiveAttack();
            }
        }
        return history;
    }
    private void fillMassiveAttack() {
        boolean s = attackPlayer.getStrategy().areThrowCard(attackPlayer, cardsIn, players,table.cardsOut);
        while (s && countDefCards > countThrowingCards) {
            RenderedCard aaCard = attackPlayer.getStrategy().throwCard(attackPlayer, cardsIn, players, table.cardsOut);
            cardsIn.add(aaCard);
            cardsForAttack.add(aaCard);
            countThrowingCards++;
            System.out.println(attackPlayer.getName() + " подбросила " + aaCard.toColorString());
            history.add(attackPlayer.getName() + " подбросила " + aaCard);
            s = attackPlayer.getStrategy().areThrowCard(attackPlayer, cardsIn, players,table.cardsOut);
        }
        for (Player player : players) {
            if (player != defPlayer && player != attackPlayer) { // Убедиться, что игрок не является защищающимся или атакующим
                s = player.getStrategy().areThrowCard(player, cardsIn, players, table.cardsOut);
                while (s && countDefCards > countThrowingCards) {
                    RenderedCard aCard = player.getStrategy().throwCard(player, cardsIn, players, table.cardsOut);
                    cardsIn.add(aCard);
                    cardsForAttack.add(aCard);
                    countThrowingCards++;
                    System.out.println(player.getName() + " подбросила " + aCard.toColorString());
                    history.add(player.getName() + " подбросила " + aCard);
                    s = player.getStrategy().areThrowCard(player, cardsIn, players, table.cardsOut);
                }
            }
        }
    }
    private boolean isRoundOver() {
        boolean flag = true;
        for (Player player : players) {
            if (player.getStrategy().areThrowCard(player, cardsIn, players, table.cardsOut) && player != defPlayer) {
                flag = false;
                break;
            }
        }
        return defPlayer.getHand().size() == 0 || (!attackPlayer.getStrategy().areThrowCard(attackPlayer, cardsIn, players, table.cardsOut) && flag);
    }
}