package mainGame;
import cardPack.RenderedCard;

import java.util.ArrayList;
import java.util.List;
import static mainGame.HelpingMethods.sortHand;
    public class GameRound {
        public List<RenderedCard> attackingCards;
        public List<RenderedCard> cardsForNextAttack = new ArrayList<>();
        private final List<Player> players;
        private final Table gameTable;
        private final Player attackingPlayer;
        private final Player defendingPlayer;
        private boolean roundIsActive;
        private final int defenderCardCount;
        private int additionalCardsCount;
        private final List<String> gameHistory;
        public GameRound(List<Player> players, Table table, Player attackingPlaye, Player defendingPlayer, List<String> gameHistory) {
            this.players = players;
            this.gameTable = table;
            this.attackingPlayer = attackingPlaye;
            this.defendingPlayer = defendingPlayer;
            this.roundIsActive = true;
            this.defenderCardCount = defendingPlayer.getHand().size();
            this.gameHistory = gameHistory;
        }
        public List<RenderedCard> getCardsForNextAttack() {
            return cardsForNextAttack;
        }
        public Player getDefendingPlayer() {
            return defendingPlayer;
        }
        public boolean isRoundActive() {
            return roundIsActive;
        }
        public List<String> play() {
            attackingCards = new ArrayList<>();
            RenderedCard attackCard;
            attackCard = attackingPlayer.getStrategy().attack(attackingPlayer, defendingPlayer, gameTable);
            gameHistory.add(attackingPlayer.getName() + " атаковала " + attackCard);
            attackingCards.add(attackCard);
            cardsForNextAttack.add(attackCard);
            additionalCardsCount = 1;
            fillMassiveAttack();

            while (true) {
                cardsForNextAttack = HelpingMethods.sortHand(cardsForNextAttack, gameTable.getTrumpCard().getSuit());
                for (RenderedCard card : cardsForNextAttack) {
                    RenderedCard defCard = defendingPlayer.getStrategy().defence(defendingPlayer, card, gameTable, attackingCards, cardsForNextAttack);
                    if (defCard != null) {
                        attackingCards.add(defCard);
                        gameHistory.add(defendingPlayer.getName() + " побилась " + defCard);
                    } else {
                        roundIsActive = false;
                        defendingPlayer.takeCards(attackingCards);
                        cardsForNextAttack.clear();
                        gameHistory.add(defendingPlayer.getName() + " затянула");
                        break;
                    }
                }
                if (roundIsActive) {
                    cardsForNextAttack.clear();
                } else {
                    break;
                }
                if (isRoundOver()) {
                    gameTable.cardsOut.addAll(attackingCards);
                    for(Player pl:players){
                        pl.setHand(sortHand(pl.getHand(), gameTable.getTrumpCard().getSuit()));
                    }
                    gameHistory.add("Карт вышло :" + gameTable.cardsOut.size());
                    gameHistory.add("Карт в колоде " + gameTable.getDeck().getCards().size());
                    break;
                } else {
                    fillMassiveAttack();
                }
            }
            return gameHistory;
        }
        private void fillMassiveAttack() {
            boolean s = attackingPlayer.getStrategy().areThrowCard(attackingPlayer, attackingCards, players, gameTable.cardsOut);
            while (s && defenderCardCount > additionalCardsCount) {
                RenderedCard aaCard = attackingPlayer.getStrategy().throwCard(attackingPlayer, attackingCards, players, gameTable.cardsOut);
                attackingCards.add(aaCard);
                cardsForNextAttack.add(aaCard);
                additionalCardsCount++;
                gameHistory.add(attackingPlayer.getName() + " подбросила " + aaCard);
                s = attackingPlayer.getStrategy().areThrowCard(attackingPlayer, attackingCards, players, gameTable.cardsOut);
            }
            for (Player player : players) {
                if (player != defendingPlayer && player != attackingPlayer) {
                    s = player.getStrategy().areThrowCard(player, attackingCards, players, gameTable.cardsOut);
                    while (s && defenderCardCount > additionalCardsCount) {
                        RenderedCard aCard = player.getStrategy().throwCard(player, attackingCards, players, gameTable.cardsOut);
                        attackingCards.add(aCard);
                        cardsForNextAttack.add(aCard);
                        additionalCardsCount++;
                        gameHistory.add(player.getName() + " подбросила " + aCard);
                        s = player.getStrategy().areThrowCard(player, attackingCards, players, gameTable.cardsOut);
                    }
                }
            }
        }
        private boolean isRoundOver() {
            boolean flag = true;
            for (Player player : players) {
                if (player.getStrategy().areThrowCard(player, attackingCards, players, gameTable.cardsOut) && player != defendingPlayer) {
                    flag = false;
                    break;
                }
            }
            return defendingPlayer.getHand().size() == 0 || (!attackingPlayer.getStrategy().areThrowCard(attackingPlayer, attackingCards, players, gameTable.cardsOut) && flag);
        }
    }