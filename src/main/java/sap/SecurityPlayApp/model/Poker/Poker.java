package sap.SecurityPlayApp.model.Poker;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sap.SecurityPlayApp.model.Card;
import sap.SecurityPlayApp.model.Deck;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Poker {
    private Deck deck;
    private int moneyOnTable;
    private int whoFirst = 1;
    // 1 - hustler first ; 2 - player first
    private int currentBetPlayer;
    private int currentBetHustler;
    private PlayerP hustler;
    private PlayerP player;
    private List<Card> cardsOnTable;
    private List<Card> initialCardsPlayer;
    private List<Card> initialCardsHustler;
    private int cardsInGame;
    private HandEvaluator playerHand;
    private HandEvaluator hustlerHand;
    private boolean gameFinished;
    private String winner;

    public void startGame() {

        deck.shuffle();
        System.out.println("!! new game started !!");
        System.out.println(deck);
        moneyOnTable = 0;
        gameFinished = false;
        winner = null;
        if (whoFirst == 1) {
            currentBetPlayer = 2;
            currentBetHustler = 5;
            player.setCredits(player.getCredits() - currentBetPlayer);
            hustler.setCredits(hustler.getCredits() - currentBetHustler);
            hustler.setMoveDone(true);
            player.setMoveDone(false);

            cardsOnTable = new ArrayList<>();

            initialCardsPlayer = new ArrayList<>();

            initialCardsPlayer.add(deck.getCard(1));
            initialCardsPlayer.add(deck.getCard(3));


            player.setCardsInHand(initialCardsPlayer);

            initialCardsHustler = new ArrayList<>();

            initialCardsHustler.add(deck.getCard(0));
            initialCardsHustler.add(deck.getCard(2));


            hustler.setCardsInHand(initialCardsHustler);

            this.cardsInGame = 4;

            System.out.println("hustler bet : " + this.getCurrentBetHustler() + hustler.isMoveDone());
            System.out.println("player bet : " + this.getCurrentBetPlayer() + player.isMoveDone());

            System.out.println(hustler.getCardsInHand());
            System.out.println(player.getCardsInHand());

            moneyOnTable = currentBetPlayer + currentBetHustler;
        }
        if (whoFirst == 2) {
            currentBetPlayer = 5;
            player.setMoveDone(true);
            hustler.setMoveDone(false);
            currentBetHustler = 2;
            player.setCredits(player.getCredits() - currentBetPlayer);
            hustler.setCredits(hustler.getCredits() - currentBetHustler);
            cardsOnTable = new ArrayList<>();

            initialCardsPlayer = new ArrayList<>();
            initialCardsPlayer.add(deck.getCard(0));
            initialCardsPlayer.add(deck.getCard(2));
            player.setCardsInHand(initialCardsPlayer);

            initialCardsHustler = new ArrayList<>();
            initialCardsHustler.add(deck.getCard(1));
            initialCardsHustler.add(deck.getCard(3));
            hustler.setCardsInHand(initialCardsHustler);


            this.cardsInGame = 4;

            System.out.println("player bet : " + this.getCurrentBetPlayer() + player.isMoveDone());
            System.out.println("hustler bet : " + this.getCurrentBetHustler() + hustler.isMoveDone());

            System.out.println(player.getCardsInHand());
            System.out.println(hustler.getCardsInHand());
            moneyOnTable = currentBetPlayer + currentBetHustler;
            this.moveHustler();
        }
        System.out.println(" current credits player:  " + player.getCredits() +
                "\n current credits hustler: " + hustler.getCredits());
        System.out.println("money on table start new game: " + moneyOnTable);
    }

    public void moveHustler() {

        HandEvaluator currentCardsHustler = new HandEvaluator();
        currentCardsHustler.setPlayerToCheckHandValue(hustler);
        if (cardsOnTable.size() < 1) {
            Card joker = new Card(0, "joker", "joker", "none");
            List<Card> noCardsOnTable = new ArrayList<>();
            noCardsOnTable.add(joker);
            currentCardsHustler.setCardsTable(noCardsOnTable);
        } else {
            currentCardsHustler.setCardsTable(cardsOnTable);
        }
        Random random = new Random();
        int i = random.nextInt(10);
        System.out.println(i);
        System.out.println(currentCardsHustler.getCardsTable() + " : : : " + currentCardsHustler.getPlayerToCheckHandValue().getCardsInHand());
        currentCardsHustler.checkHandPlayer();

        int handValue = currentCardsHustler.getHandValue();

        int toCheck = currentBetPlayer - currentBetHustler;

        // hustler ai - BEFORE FLOP
        if (cardsOnTable.size() == 0) {
            /// MOVES WITH "HIGH CARD"
            if (handValue == 1) {
                if (currentCardsHustler.getHighCard().getValue() == 14) {
                    /// hustler rise 10/20/30
                    int decRise = random.nextInt(3);
                    int bet = 0;
                    if (decRise == 0) {
                        bet = 10;
                    } else if (decRise == 1) {
                        bet = 20;
                    } else {
                        bet = 30;
                    }
                    betHustler(bet);
                    return;
                }
                if (currentCardsHustler.getHighCard().getValue() > 10) {
                    /// hustler rise 10/20
                    int decRise = random.nextInt(2);
                    int bet = 0;
                    if (decRise == 0) {
                        bet = 10;
                    } else {
                        bet = 20;
                    }
                    betHustler(bet);
                    return;
                }
                if ((hustler.getCardsInHand().get(0).getValue() - hustler.getCardsInHand().get(1).getValue() == 1 ||
                        hustler.getCardsInHand().get(0).getValue() - hustler.getCardsInHand().get(1).getValue() == -1) &&
                                hustler.getCardsInHand().get(0).getColor().equals(hustler.getCardsInHand().get(1).getColor())) {
                    /// hustler rise 10/20
                    int decRise = random.nextInt(2);
                    int bet = 0;
                    if (decRise == 0) {
                        bet = 10;
                    } else {
                        bet = 20;
                    }
                    betHustler(bet);
                    return;
                }
                if (hustler.getCardsInHand().get(0).getValue() - hustler.getCardsInHand().get(1).getValue() == 1 ||
                        hustler.getCardsInHand().get(0).getValue() - hustler.getCardsInHand().get(1).getValue() == -1 ||
                        hustler.getCardsInHand().get(0).getColor().equals(hustler.getCardsInHand().get(1).getColor())) {
                    if (currentBetPlayer > 0) {
                        if (currentBetPlayer < 10) {
                            checkHustler();
                        } else {
                            hustlerFold();
                        }
                    } else {
                        betHustler(5);
                    }
                    return;
                }
                if (currentBetPlayer > 5) {
                    hustlerFold();
                    return;
                } else {
                    checkHustler();
                    return;
                }
            }
            if (handValue == 2) {
                ///hustler rise 10/20/30
                int decRise = random.nextInt(3);
                int bet = 0;
                if (decRise == 0) {
                    bet = 10;
                } else if (decRise == 1) {
                    bet = 20;
                } else {
                    bet = 30;
                }
                betHustler(bet);
                return;
            }
        }
        /// hustler ai - AFTER FLOP
        if (cardsOnTable.size() > 0) {
            if (handValue > 6) {
                /// hustler rise 30/40/50
                int decRise = random.nextInt(4);
                int bet = 0;
                if (decRise == 0) {
                    bet = 30;
                } else if (decRise == 1) {
                    bet = 40;
                } else if (decRise == 2) {
                    bet = 50;
                } else {
                    checkHustler();
                    return;
                }
                betHustler(bet);
                return;
            }
            if (handValue > 4) {
                /// hustler rise 30/40/50
                int decRise = random.nextInt(4);
                int bet = 0;
                if (decRise == 0) {
                    bet = 10;
                } else if (decRise == 1) {
                    bet = 20;
                } else if (decRise == 2) {
                    bet = 30;
                } else {
                    checkHustler();
                    return;
                }
                betHustler(bet);
                return;
            }
            if (handValue > 2) {
                if (currentBetPlayer > 0) {
                    if (currentBetPlayer > 40) {
                        hustlerFold();
                        return;
                    } else {
                        checkHustler();
                        return;
                    }
                } else {
                    /// hustler rise 10/20
                    int decRise = random.nextInt(2);
                    int bet = 0;
                    if (decRise == 0) {
                        bet = 10;
                    } else {
                        bet = 20;
                    }
                    betHustler(bet);
                    return;
                }
            }
            if (handValue == 2) {
                if (currentBetPlayer > 0) {
                    if (currentBetPlayer > 20) {
                        hustlerFold();
                        return;
                    } else {
                        checkHustler();
                        return;
                    }
                } else {
                    /// hustler rise 10 or check
                    int decRise = random.nextInt(2);
                    int bet = 0;
                    if (decRise == 0) {
                        bet = 10;
                    } else {
                        checkHustler();
                        return;
                    }
                    betHustler(bet);
                    return;
                }
            }
            if (handValue == 1) {
                if (currentBetPlayer > 0) {
                    if (currentBetPlayer > 10) {
                        hustlerFold();
                    } else {
                        checkHustler();
                    }
                }else {
                    checkHustler();
                }
            }
        }

    }

    public void nextCardOnTable() {
        System.out.println(" current credits player:  " + player.getCredits() +
                "\n current credits hustler: " + hustler.getCredits());
        if (player.isMoveDone() && hustler.isMoveDone()) {
            if (cardsInGame == 4) {

                cardsOnTable.add(deck.getCard(5));
                cardsOnTable.add(deck.getCard(6));
                cardsOnTable.add(deck.getCard(7));


                cardsInGame = 8;

                System.out.println(cardsOnTable);
                player.setMoveDone(false);
                hustler.setMoveDone(false);
                // moneyOnTable += currentBetPlayer + currentBetHustler;
                currentBetPlayer = 0;
                currentBetHustler = 0;
                System.out.println("Current stake: " + moneyOnTable);
                if (whoFirst == 1) {
                    moveHustler();
                }
            } else {
                if (cardsOnTable.size() < 5) {
                    cardsOnTable.add(deck.getCard(cardsInGame));
                    cardsInGame++;
                    System.out.println(cardsOnTable);
                    player.setMoveDone(false);
                    hustler.setMoveDone(false);
                    //moneyOnTable += currentBetPlayer + currentBetHustler;
                    currentBetPlayer = 0;
                    currentBetHustler = 0;
                    System.out.println("Current stake: " + moneyOnTable);
                    if (whoFirst == 1) {
                        moveHustler();
                    }

                } else {
                    System.out.println("max card on table (5)");
                    this.checkTable();
                    gameFinished = true;
                }
            }
        } else {
            if (!player.isMoveDone())
                System.out.println("player move !");
            if (!hustler.isMoveDone())
                System.out.println("hustler move !");
        }
    }

    public void newGame() {
        if (whoFirst == 1) {
            whoFirst = 2;
        } else {
            whoFirst = 1;
        }
        this.startGame();
    }

    public void hustlerFold() {
        System.out.println(player.getCredits());
        this.player.setCredits(player.getCredits() + moneyOnTable);
        winner = "player";
        playerHand = new HandEvaluator();
        playerHand.setPlayerToCheckHandValue(player);
        if (cardsOnTable.size() < 1) {
            Card joker = new Card(0, "joker", "joker", "none");
            List<Card> noCardsOnTable = new ArrayList<>();
            noCardsOnTable.add(joker);
            playerHand.setCardsTable(noCardsOnTable);
        } else {
            playerHand.setCardsTable(cardsOnTable);
        }
        playerHand.checkHandPlayer();
        gameFinished = true;
        System.out.println("hustler fold " +
                "\n player win : " + moneyOnTable +
                "\n current credits player:  " + player.getCredits() +
                "\n current credits hustler: " + hustler.getCredits());

    }

    public void playerFold() {
        this.hustler.setCredits(hustler.getCredits() + moneyOnTable);
        winner = "hustler";
        hustlerHand = new HandEvaluator();
        hustlerHand.setPlayerToCheckHandValue(hustler);
        if (cardsOnTable.size() < 1) {
            Card joker = new Card(0, "joker", "joker", "none");
            List<Card> noCardsOnTable = new ArrayList<>();
            noCardsOnTable.add(joker);
           hustlerHand.setCardsTable(noCardsOnTable);
        } else {
            hustlerHand.setCardsTable(cardsOnTable);
        }
        hustlerHand.checkHandPlayer();
        gameFinished = true;
        System.out.println("player fold " +
                "\n hustler win : " + moneyOnTable +
                "\n current credits player:  " + player.getCredits() +
                "\n current credits hustler: " + hustler.getCredits());

    }

    public void checkPlayer() {
        if (currentBetHustler > 0) {
            betPlayer(currentBetHustler - currentBetPlayer);
        } else {
            player.setMoveDone(true);
            System.out.println("player check 0");
            if (!hustler.isMoveDone()) {
                moveHustler();
            }
            nextCardOnTable();
        }
    }

    public void checkHustler() {
        if (currentBetPlayer > 0) {
            betHustler(currentBetPlayer - currentBetHustler);
        } else {
            hustler.setMoveDone(true);
            System.out.println("hustler check 0");
            if (player.isMoveDone()) {
                nextCardOnTable();
            }
        }
    }

    public void betPlayer(int bet) {
        if (bet <= player.getCredits() && (bet + currentBetPlayer) >= currentBetHustler) {
            player.setCredits(player.getCredits() - bet);
            moneyOnTable += bet;
            currentBetPlayer += bet;
            System.out.println(player.getName() + " bet : " + currentBetPlayer);
            player.setMoveDone(true);
            if (currentBetPlayer > currentBetHustler) {
                hustler.setMoveDone(false);
                moveHustler();
            } else {
                nextCardOnTable();
            }

        } else {
            System.out.println("not enough balance to bet");
        }
    }

    public void betHustler(int bet) {
        if (bet <= hustler.getCredits() && (bet + currentBetHustler) >= currentBetPlayer) {
            hustler.setCredits(hustler.getCredits() - bet);
            moneyOnTable += bet;
            currentBetHustler += bet;
            System.out.println(hustler.getName() + " bet : " + currentBetHustler);
            hustler.setMoveDone(true);
            if (currentBetPlayer < currentBetHustler) {
                player.setMoveDone(false);
            } else {
                nextCardOnTable();
            }

        } else {
            System.out.println("not enough balance to bet");
        }
    }

    public void checkTable() {
        if (cardsOnTable.size() == 5 && hustler.isMoveDone() && player.isMoveDone()) {
            playerHand = new HandEvaluator();
            playerHand.setPlayerToCheckHandValue(player);
            playerHand.setCardsTable(cardsOnTable);
            playerHand.checkHandPlayer();
            System.out.println(playerHand.getPlayerToCheckHandValue().getCardsInHand());
            System.out.println(playerHand.getCardsTable());

            hustlerHand = new HandEvaluator();
            hustlerHand.setPlayerToCheckHandValue(hustler);
            hustlerHand.setCardsTable(cardsOnTable);
            hustlerHand.checkHandPlayer();

            System.out.println("Player hand value: " + playerHand.getHandValue() + " -> winning cards : " + playerHand.getWinningCards());
            System.out.println("Hustler hand value: " + hustlerHand.getHandValue() + " -> winning cards : " + hustlerHand.getWinningCards());
            if (hustlerHand.getHandValue() > playerHand.getHandValue()) {
                System.out.println("hustler is winner ");
                winner = "hustler";
                this.hustler.setCredits(hustler.getCredits() + moneyOnTable);
                System.out.println("hustler better hand " +
                        "\n hustler win : " + moneyOnTable +
                        "\n current credits player:  " + player.getCredits() +
                        "\n current credits hustler: " + hustler.getCredits());
            } else if (hustlerHand.getHandValue() < playerHand.getHandValue()) {
                System.out.println("player is winner ");
                winner = "player";
                this.player.setCredits(player.getCredits() + moneyOnTable);
                System.out.println("player better hand:  " +
                        "\n player win : " + moneyOnTable +
                        "\n current credits player:  " + player.getCredits() +
                        "\n current credits hustler: " + hustler.getCredits());
            } else {
                if (hustlerHand.getHandValue() > 1) {
                    if (hustlerHand.getWinningCards().get(0).getValue() > playerHand.getWinningCards().get(0).getValue()) {
                        System.out.println("hustler is winner ");
                        winner = "hustler";
                        this.hustler.setCredits(hustler.getCredits() + moneyOnTable);
                        System.out.println("hustler better hand " +
                                "\n hustler win : " + moneyOnTable +
                                "\n current credits player:  " + player.getCredits() +
                                "\n current credits hustler: " + hustler.getCredits());
                    } else if (hustlerHand.getWinningCards().get(0).getValue() < playerHand.getWinningCards().get(0).getValue()) {
                        System.out.println("player is winner ");
                        winner = "player";
                        this.player.setCredits(player.getCredits() + moneyOnTable);
                        System.out.println("player better hand:  " +
                                "\n player win : " + moneyOnTable +
                                "\n current credits player:  " + player.getCredits() +
                                "\n current credits hustler: " + hustler.getCredits());
                    } else {

                        winner = "draw";
                        int halfWin = moneyOnTable / 2;
                        this.player.setCredits(player.getCredits() + halfWin);
                        this.hustler.setCredits(hustler.getCredits() + halfWin);
                        System.out.println("same hand:  " +
                                "\n player win : " + halfWin +
                                "\n hustler win : " + halfWin +
                                "\n current credits player:  " + player.getCredits() +
                                "\n current credits hustler: " + hustler.getCredits());
                    }
                } else {
                    if (hustlerHand.getHighCard().getValue() > playerHand.getHighCard().getValue()) {
                        System.out.println("hustler is winner ");
                        winner = "hustler";
                        this.hustler.setCredits(hustler.getCredits() + moneyOnTable);
                        System.out.println("hustler better hand " +
                                "\n hustler win : " + moneyOnTable +
                                "\n current credits player:  " + player.getCredits() +
                                "\n current credits hustler: " + hustler.getCredits());
                    } else if (hustlerHand.getHighCard().getValue() < playerHand.getHighCard().getValue()) {
                        System.out.println("player is winner ");
                        winner = "player";
                        this.player.setCredits(player.getCredits() + moneyOnTable);
                        winner = "player";
                        System.out.println("player better hand:  " +
                                "\n player win : " + moneyOnTable +
                                "\n current credits player:  " + player.getCredits() +
                                "\n current credits hustler: " + hustler.getCredits());
                    } else {
                        winner = "draw";
                        int halfWin = moneyOnTable / 2;
                        this.player.setCredits(player.getCredits() + halfWin);
                        this.hustler.setCredits(hustler.getCredits() + halfWin);
                        System.out.println("same hand:  " +
                                "\n player win : " + halfWin +
                                "\n hustler win : " + halfWin +
                                "\n current credits player:  " + player.getCredits() +
                                "\n current credits hustler: " + hustler.getCredits());
                    }
                }
            }

        } else {

            System.out.println(" game not finished ");
        }
    }

}
