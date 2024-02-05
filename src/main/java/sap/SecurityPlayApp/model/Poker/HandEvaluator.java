package sap.SecurityPlayApp.model.Poker;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sap.SecurityPlayApp.model.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HandEvaluator {
    private PlayerP playerToCheckHandValue;
    private List<Card> cardsTable;

    /// high card 1 ; para 2 ; dwie pary 3; trojka 4 ; strit 5 ; kolor 6 ; full 7 ; kareta 8 ; poker 9 ; krolewski poker 10
    private int handValue = 0;
    private Card highCard;

    private List<Card> winningCards;



    public void checkHandPlayer() {
        // royal flush
        highCard = setHighCard();
        this.winningCards = getRoyalFlush();
        if (winningCards != null && winningCards.size() == 5) {
            System.out.println(this.winningCards + " : royal flush ");
            this.handValue = 10;
            return;
        }
        // straight flush
        this.winningCards = getStraightFlush();
        if (winningCards != null && winningCards.size() == 5) {
            System.out.println(this.winningCards + " : straight flush ");
            this.handValue = 9;
            return;
        }
        // four of a kind
        this.winningCards = getFourOfAKind();
        if (winningCards != null && winningCards.size() == 4) {
            System.out.println(this.winningCards + " : four of a kind ");
            this.handValue = 8;
            return;
        }
        // full
        this.winningCards = getFull();
        if (winningCards != null && winningCards.size() == 5) {
            System.out.println(this.winningCards + " : full ");
            this.handValue = 7;
            return;
        }
        // flush
        this.winningCards = getFlush();
        if (winningCards != null && winningCards.size() == 5) {
            System.out.println(this.winningCards + " : flush ");
            this.handValue = 6;
            return;
        }
        // straight
        this.winningCards = getStraight();
        if (winningCards != null && winningCards.size() == 5) {
            System.out.println(this.winningCards + " : straight ");
            this.handValue = 5;
            return;
        }
        // three of a kind
        this.winningCards = getThreeOfAKind();
        if (winningCards != null && winningCards.size() == 3) {
            System.out.println(this.winningCards + " : three of a kind ");
            this.handValue = 4;
            return;
        }
        // two pair
        this.winningCards = getTwoPair();
        if (winningCards != null && winningCards.size() == 4) {
            System.out.println(this.winningCards + " : two pair ");
            this.handValue = 3;
            return;
        }
        // pair
        this.winningCards = getOnePair();
        if (winningCards != null && winningCards.size() == 2) {
            System.out.println(this.winningCards + " : pair ");
            this.handValue = 2;
            return;
        }
        //HIGH_CARD
        this.highCard = setHighCard();
        if (highCard != null) {
            System.out.println(this.highCard + " : high card ");
            this.handValue = 1;

        }
    }

    private List<Card> getRoyalFlush() {
        String royalColor = null;
        List<Card> royalCards = new ArrayList<>();
        int falseMatch = 0;
        while (falseMatch < 3) {
            if (playerToCheckHandValue.getCardsInHand().get(0).getValue() >= 10 &&
                    playerToCheckHandValue.getCardsInHand().get(1).getValue() >= 10) {
                if (playerToCheckHandValue.getCardsInHand().get(0).getColor()
                        .equals(this.playerToCheckHandValue.getCardsInHand().get(1).getColor())) {
                    royalColor = playerToCheckHandValue.getCardsInHand().get(0).getColor();
                    royalCards.addAll(playerToCheckHandValue.getCardsInHand());
                } else {
                    if ((playerToCheckHandValue.getCardsInHand().get(0).getColor().equals(cardsTable.get(0)) &&
                            playerToCheckHandValue.getCardsInHand().get(0).getColor().equals(cardsTable.get(1))) ||
                            (playerToCheckHandValue.getCardsInHand().get(0).getColor().equals(cardsTable.get(0)) &&
                                    playerToCheckHandValue.getCardsInHand().get(0).getColor().equals(cardsTable.get(2)))
                    ) {
                        royalColor = playerToCheckHandValue.getCardsInHand().get(0).getColor();
                        falseMatch++;
                    } else if ((playerToCheckHandValue.getCardsInHand().get(1).getColor().equals(cardsTable.get(0)) &&
                            playerToCheckHandValue.getCardsInHand().get(1).getColor().equals(cardsTable.get(1))) ||
                            (playerToCheckHandValue.getCardsInHand().get(1).getColor().equals(cardsTable.get(0)) &&
                                    playerToCheckHandValue.getCardsInHand().get(1).getColor().equals(cardsTable.get(2)))) {
                        royalColor = playerToCheckHandValue.getCardsInHand().get(1).getColor();
                        falseMatch++;
                    } else {
                        return null;
                    }
                }
            } else if (playerToCheckHandValue.getCardsInHand().get(0).getValue() >= 10) {
                royalColor = playerToCheckHandValue.getCardsInHand().get(0).getColor();
                falseMatch++;
            } else if (playerToCheckHandValue.getCardsInHand().get(1).getValue() >= 10) {
                royalColor = playerToCheckHandValue.getCardsInHand().get(1).getColor();
                falseMatch++;
            }

            for (Card card : cardsTable) {
                if (card.getColor().equals(royalColor) && card.getValue() >= 10) {
                    royalCards.add(card);
                } else {
                    falseMatch++;
                }
            }
            return sortCards(royalCards);
        }
        return null;
    }

    private List<Card> getStraightFlush() {
        List<Card> toCheck = getMerge(playerToCheckHandValue.getCardsInHand(), cardsTable);
        return getSequence(toCheck, 5, true);
    }
    private List<Card> getFourOfAKind(){
        List<Card> toCheck = getMerge(playerToCheckHandValue.getCardsInHand(), cardsTable);
        return checkSameRank(toCheck, 4);
    }
    private List<Card> getFull() {
        List<Card> toCheck = getMerge(playerToCheckHandValue.getCardsInHand(), cardsTable);
        List<Card> threeSame = checkSameRank(toCheck, 3);
        if(threeSame != null){
            toCheck.removeAll(threeSame);
            List<Card> twoSame = checkSameRank(toCheck, 2);
            if(twoSame != null){
                threeSame.addAll(twoSame);
                return threeSame;
            }
        }
        return null;
    }
    private List<Card> getFlush(){
        List<Card> toCheck = getMerge(playerToCheckHandValue.getCardsInHand(), cardsTable);
        List<Card> flush = new ArrayList<>();
        for(int i= 0; i<2; i++){
            flush.add(toCheck.get(i));
            toCheck.remove(toCheck.get(i));
            if(i==0 && flush.get(0).getColor().equals(toCheck.get(i+1).getColor())){
                flush.add(toCheck.get(i+1));
                toCheck.remove(toCheck.get(i+1));
            }
            for (Card card : toCheck){
                if (card.getColor().equals(flush.get(0).getColor())){
                    flush.add(card);
                }
            }
            if (flush.size() == 5){
                return sortCards(flush);
            }
            else {
                flush.clear();
            }
        }
        return null;
    }
    private List<Card> getStraight(){
        return getSequence(getMerge(playerToCheckHandValue.getCardsInHand(), cardsTable), 5, false);
    }

    private List<Card> getThreeOfAKind(){
        List<Card> toCheck = getMerge(playerToCheckHandValue.getCardsInHand(), cardsTable);
        return checkSameRank(toCheck, 3);
    }
    private List<Card> getTwoPair(){
        List<Card> toCheck = getMerge(playerToCheckHandValue.getCardsInHand(), cardsTable);
        List<Card> firstPair = checkSameRank(toCheck, 2);
        if (firstPair != null){
            toCheck.removeAll(firstPair);
            List<Card> secondPair = checkSameRank(toCheck, 2);
            if (secondPair != null){
                firstPair.addAll(secondPair);
                return sortCards(firstPair);
            }
        }
        return null;
    }

    private List<Card> getOnePair(){
        List<Card> toCheck = getMerge(playerToCheckHandValue.getCardsInHand(), cardsTable);
        return checkSameRank(toCheck, 2);
    }
    private Card setHighCard(){
        List<Card> toCheck = sortCards(playerToCheckHandValue.getCardsInHand());
        return  toCheck.get(0);
    }



    private List<Card> getSequence(List<Card> cardsToCheckSequence, int sequenceSize, boolean isSameColor) {
        List<Card> sorted = sortCards(cardsToCheckSequence);
        Card previousCard = null;
        List<Card> sequence = new ArrayList<>();

        for (Card card : sorted) {
            if (previousCard == null) {
                previousCard = card;
            } else {
                if (previousCard.getValue() - card.getValue() == 1) {
                    if (!isSameColor || previousCard.getColor().equals(card.getColor())) {
                        if (sequence.size() == sequenceSize - 2) {
                            sequence.add(previousCard);
                            sequence.add(card);
                            return sequence;
                        } else {
                            sequence.add(previousCard);
                            previousCard = card;
                        }
                    } else {
                        sequence.clear();
                    }

                }

            }
        }

        return null;
    }
    private List<Card> checkSameRank(List<Card> toCheck, int sameRankSize ) {
        List<Card> sameRank = new ArrayList<>();
        for(int i= 0; i<2; i++){
            sameRank.add(toCheck.get(i));
            for(Card card : toCheck){
                if(!card.equals(toCheck.get(i)) && card.getRank().equals(toCheck.get(i).getRank())){
                    sameRank.add(card);
                }
            }
            if (sameRank.size() == sameRankSize){
                return sameRank;
            }else {
                sameRank.clear();
            }
        }
        return null;
    }

    private List<Card> sortCards(List<Card> cardsToSort) {

        Collections.sort(cardsToSort, new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                return o1.getValue() > o2.getValue() ? -1 : 1;
            }
        });

        return cardsToSort;
    }

    private List<Card> getMerge(List<Card> cards1, List<Card> cards2) {
        List<Card> merged = new ArrayList<>();
        merged.addAll(cards1);
        merged.addAll(cards2);
        return merged;
    }

}
