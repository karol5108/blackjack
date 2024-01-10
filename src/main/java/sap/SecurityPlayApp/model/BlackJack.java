package sap.SecurityPlayApp.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BlackJack {
    private String level;
    private Deck deck;
    private PlayerBJ player;
    private PlayerBJ hustler;
    private int cardsOnTable;
    private List<Card> initialCardsPlayer;
    private List<Card> initialCardsHustler;

    private boolean isStayButtonClicked;

    private static final int MAX_HISTORY_SIZE = 10;

    private LinkedList<String> gameHistory = new LinkedList<>();


    public void startGame() {
        deck.shuffle();
        System.out.println(deck);

        isStayButtonClicked = false;

        initialCardsPlayer = new ArrayList<>();
        initialCardsPlayer.add(deck.getCard(1));
        player.setResult(deck.getCard(1).getValue());
        initialCardsPlayer.add(deck.getCard(3));
        player.setCardsInHand(initialCardsPlayer);
        player.setResult(player.calculatePoints());


        System.out.println(player.getCardsInHand() + " : " + player.calculatePoints());


        initialCardsHustler = new ArrayList<>();
        initialCardsHustler.add(deck.getCard(0));
        hustler.setResult(deck.getCard(0).getValue());
        initialCardsHustler.add(deck.getCard(2));
        hustler.setCardsInHand(initialCardsHustler);
        hustler.setResult(hustler.calculatePoints());


        System.out.println(hustler.getCardsInHand() + " : " + hustler.calculatePoints());

        this.cardsOnTable = 4;
    }

    public String checkWinner() {
        String winner = null;
        System.out.println(player.getResult() + " : " + hustler.getResult());
        if (player.getResult() == hustler.getResult()) {
            winner = "D";
        } else {
            if (player.getResult() <= 21 && hustler.getResult() <= 21) {
                if (player.getResult() > hustler.getResult())
                    winner = "P";
                else
                    winner = "H";
            } else {
                if (player.getResult() > 21)
                    winner = "H";
                else
                    winner = "P";
            }
        }
        return winner;
    }

    public void getOne() {
        if (player.getResult() < 21 && !isStayButtonClicked) {
            initialCardsPlayer.add(deck.getCard(cardsOnTable));
            player.setCardsInHand(initialCardsPlayer);
            player.setResult(player.calculatePoints());
            cardsOnTable++;
            System.out.println(player.getCardsInHand() + " : " + player.calculatePoints());

        } else {
            System.out.println("za duzy wynik zeby pobrac karte");
        }
    }

    public void getOneHustler() {
        System.out.println(hustler.getResult());
        if (hustler.getResult() < 21 && player.getResult() <= 21) {
            if (hustler.getResult() <= 11) {
                initialCardsHustler.add(deck.getCard(cardsOnTable));
                hustler.setCardsInHand(initialCardsHustler);
                hustler.setResult(hustler.calculatePoints());
                System.out.println("<<11");
            } else if (hustler.getResult() == 12 && (player.hasCardWithValue(2) ||
                    player.hasCardWithValue(3) ||
                    player.hasCardWithValue(7) ||
                    player.hasCardWithValue(8) ||
                    player.hasCardWithValue(9) ||
                    player.hasCardWithValue(10) ||
                    player.hasCardWithValue(11))) {
                System.out.println("==12");
                initialCardsHustler.add(deck.getCard(cardsOnTable));
                hustler.setCardsInHand(initialCardsHustler);
                hustler.setResult(hustler.calculatePoints());
            } else if (hustler.getResult() > 12 && hustler.getResult() < 17 &&
                    (player.hasCardWithValue(7) ||
                            player.hasCardWithValue(8) ||
                            player.hasCardWithValue(9) ||
                            player.hasCardWithValue(10) ||
                            player.hasCardWithValue(11))) {
                initialCardsHustler.add(deck.getCard(cardsOnTable));
                hustler.setCardsInHand(initialCardsHustler);
                hustler.setResult(hustler.calculatePoints());
                System.out.println("<<17");

            } else if (hustler.getResult() > 17) {
                System.out.println("za duzy wynik");
            }
            System.out.println(hustler.getCardsInHand() + " : " + hustler.calculatePoints());
        }
        isStayButtonClicked = true;
    }

    public void finishGame() {
        hustler.setResult(0);
        hustler.setCardsInHand(null);
        hustler.setNbCards(0);
        player.setResult(0);
        player.setCardsInHand(null);
        player.setNbCards(0);
        cardsOnTable = 0;

    }

    public List<String> getGameHistory() {
        return new ArrayList<>(gameHistory);
    }
    public void addToGameHistory(String result) {
        if (gameHistory.size() >= MAX_HISTORY_SIZE) {
            gameHistory.removeFirst();  // Usuń najstarszy wynik
        }
        gameHistory.add(result);
    }
}
