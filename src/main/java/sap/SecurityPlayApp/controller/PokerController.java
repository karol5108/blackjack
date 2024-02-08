package sap.SecurityPlayApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sap.SecurityPlayApp.model.Deck;
import sap.SecurityPlayApp.model.Poker.PlayerP;
import sap.SecurityPlayApp.model.Poker.Poker;

@Controller
public class PokerController {

    private Deck deck;
    private PlayerP playerP;

    private PlayerP huslterP;
    private Poker poker;


    @GetMapping("/start-poker")
    public String startPoker(Model model) {
        if (deck==null || playerP == null || huslterP == null || poker == null){
            deck = new Deck("poker");
            playerP = new PlayerP();
            playerP.setName("player");
            playerP.setCredits(1000);
            huslterP = new PlayerP();
            huslterP.setName("hustler");
            huslterP.setCredits(1000);

            poker = new Poker();
            poker.setDeck(deck);
            poker.setPlayer(playerP);
            poker.setHustler(huslterP);

        }
        poker.startGame();

        if (poker.isGameFinished()){
            model.addAttribute("playerCards", poker.getPlayer().getCardsInHand());
            model.addAttribute("hustlerCards", poker.getHustler().getCardsInHand());
            model.addAttribute("cardsOnTable", poker.getCardsOnTable());
            model.addAttribute("gameFinished", true);
            model.addAttribute("loggedIn", true);
            model.addAttribute("winner", poker.getWinner());
            model.addAttribute("creditsPlayer", poker.getPlayer().getCredits());
            model.addAttribute("creditsHustler", poker.getHustler().getCredits());
            model.addAttribute("winner", poker.getWinner());

        }else {
            model.addAttribute("playerCards", poker.getPlayer().getCardsInHand());
            model.addAttribute("hustlerCards", poker.getHustler().getCardsInHand());
            model.addAttribute("cardsOnTable", poker.getCardsOnTable());
            model.addAttribute("gameFinished", false);
            model.addAttribute("loggedIn", true);
            model.addAttribute("betHustler", poker.getCurrentBetHustler());
            model.addAttribute("betPlayer", poker.getCurrentBetPlayer());
            model.addAttribute("moneyOnTable", poker.getMoneyOnTable());
            model.addAttribute("creditsPlayer", poker.getPlayer().getCredits());
            model.addAttribute("creditsHustler", poker.getHustler().getCredits());
        }


        return "poker";
    }
    @GetMapping("/check")
    public String check(Model model){
        if (!poker.getPlayer().isMoveDone()){
            poker.checkPlayer();
        }
        if (poker.isGameFinished()){
            model.addAttribute("playerCards", poker.getPlayer().getCardsInHand());
            model.addAttribute("hustlerCards", poker.getHustler().getCardsInHand());
            model.addAttribute("cardsOnTable", poker.getCardsOnTable());
            model.addAttribute("gameFinished", true);
            model.addAttribute("loggedIn", true);
            model.addAttribute("winner", poker.getWinner());
            model.addAttribute("creditsPlayer", poker.getPlayer().getCredits());
            model.addAttribute("creditsHustler", poker.getHustler().getCredits());
            model.addAttribute("winner", poker.getWinner());
            if (poker.getWinner().equals("player")){
                if (poker.getPlayerHand().getHandValue() == 1){
                    model.addAttribute("winningCards",poker.getPlayerHand().getHighCard());
                }else {
                    model.addAttribute("winningCards", poker.getPlayerHand().getWinningCards());
                }
            }else {
                if (poker.getHustlerHand().getHandValue() == 1){
                    model.addAttribute("winningCards",poker.getHustlerHand().getHighCard());
                }else {
                    model.addAttribute("winningCards", poker.getHustlerHand().getWinningCards());
                }
            }
        }else {
            model.addAttribute("playerCards", poker.getPlayer().getCardsInHand());
            model.addAttribute("hustlerCards", poker.getHustler().getCardsInHand());
            model.addAttribute("cardsOnTable", poker.getCardsOnTable());
            model.addAttribute("gameFinished", false);
            model.addAttribute("loggedIn", true);
            model.addAttribute("betHustler", poker.getCurrentBetHustler());
            model.addAttribute("betPlayer", poker.getCurrentBetPlayer());
            model.addAttribute("moneyOnTable", poker.getMoneyOnTable());
            model.addAttribute("creditsPlayer", poker.getPlayer().getCredits());
            model.addAttribute("creditsHustler", poker.getHustler().getCredits());
        }
        return "poker";
    }
    @GetMapping("/fold")
    public String fold(Model model){
        poker.playerFold();
        model.addAttribute("playerCards", poker.getPlayer().getCardsInHand());
        model.addAttribute("hustlerCards", poker.getHustler().getCardsInHand());
        model.addAttribute("cardsOnTable", poker.getCardsOnTable());
        model.addAttribute("gameFinished", true);
        model.addAttribute("loggedIn", true);
        model.addAttribute("winner", poker.getWinner());
        model.addAttribute("creditsPlayer", poker.getPlayer().getCredits());
        model.addAttribute("creditsHustler", poker.getHustler().getCredits());


        return "poker";
    }
    @GetMapping("/rise")
    public String rise(Model model){
        poker.betPlayer(poker.getCurrentBetHustler() + 10 - poker.getCurrentBetPlayer());
        if (poker.isGameFinished()){
            model.addAttribute("playerCards", poker.getPlayer().getCardsInHand());
            model.addAttribute("hustlerCards", poker.getHustler().getCardsInHand());
            model.addAttribute("cardsOnTable", poker.getCardsOnTable());
            model.addAttribute("gameFinished", true);
            model.addAttribute("loggedIn", true);
            model.addAttribute("winner", poker.getWinner());
            model.addAttribute("creditsPlayer", poker.getPlayer().getCredits());
            model.addAttribute("creditsHustler", poker.getHustler().getCredits());
            model.addAttribute("winner", poker.getWinner());
            if (poker.getWinner().equals("player")){
                if (poker.getPlayerHand().getHandValue() == 1){
                    model.addAttribute("winningCards",poker.getPlayerHand().getHighCard());
                }else {
                    model.addAttribute("winningCards", poker.getPlayerHand().getWinningCards());
                }
            }else {
                if (poker.getHustlerHand().getHandValue() == 1){
                    model.addAttribute("winningCards",poker.getHustlerHand().getHighCard());
                }else {
                    model.addAttribute("winningCards", poker.getHustlerHand().getWinningCards());
                }
            }
        }else {
            model.addAttribute("playerCards", poker.getPlayer().getCardsInHand());
            model.addAttribute("hustlerCards", poker.getHustler().getCardsInHand());
            model.addAttribute("cardsOnTable", poker.getCardsOnTable());
            model.addAttribute("gameFinished", false);
            model.addAttribute("loggedIn", true);
            model.addAttribute("betHustler", poker.getCurrentBetHustler());
            model.addAttribute("betPlayer", poker.getCurrentBetPlayer());
            model.addAttribute("moneyOnTable", poker.getMoneyOnTable());
            model.addAttribute("creditsPlayer", poker.getPlayer().getCredits());
            model.addAttribute("creditsHustler", poker.getHustler().getCredits());
        }
        return "poker";
    }
    @GetMapping("/new-game")
    public String newGame(Model model){
        poker.newGame();
        if (poker.isGameFinished()){
            model.addAttribute("playerCards", poker.getPlayer().getCardsInHand());
            model.addAttribute("hustlerCards", poker.getHustler().getCardsInHand());
            model.addAttribute("cardsOnTable", poker.getCardsOnTable());
            model.addAttribute("gameFinished", true);
            model.addAttribute("loggedIn", true);
            model.addAttribute("winner", poker.getWinner());
            model.addAttribute("creditsPlayer", poker.getPlayer().getCredits());
            model.addAttribute("creditsHustler", poker.getHustler().getCredits());
            model.addAttribute("winner", poker.getWinner());

        }else {
            model.addAttribute("playerCards", poker.getPlayer().getCardsInHand());
            model.addAttribute("hustlerCards", poker.getHustler().getCardsInHand());
            model.addAttribute("cardsOnTable", poker.getCardsOnTable());
            model.addAttribute("gameFinished", false);
            model.addAttribute("loggedIn", true);
            model.addAttribute("betHustler", poker.getCurrentBetHustler());
            model.addAttribute("betPlayer", poker.getCurrentBetPlayer());
            model.addAttribute("moneyOnTable", poker.getMoneyOnTable());
            model.addAttribute("creditsPlayer", poker.getPlayer().getCredits());
            model.addAttribute("creditsHustler", poker.getHustler().getCredits());
        }
        return "poker";
    }
    @GetMapping("/riseX2")
    public String riseX2(Model model){
        poker.betPlayer(poker.getCurrentBetHustler() * 2 - poker.getCurrentBetPlayer()- poker.getCurrentBetPlayer());
        if (poker.isGameFinished()){
            model.addAttribute("playerCards", poker.getPlayer().getCardsInHand());
            model.addAttribute("hustlerCards", poker.getHustler().getCardsInHand());
            model.addAttribute("cardsOnTable", poker.getCardsOnTable());
            model.addAttribute("gameFinished", true);
            model.addAttribute("loggedIn", true);
            model.addAttribute("winner", poker.getWinner());
            model.addAttribute("creditsPlayer", poker.getPlayer().getCredits());
            model.addAttribute("creditsHustler", poker.getHustler().getCredits());
            model.addAttribute("winner", poker.getWinner());
            if (poker.getWinner().equals("player")){
                if (poker.getPlayerHand().getHandValue() == 1){
                    model.addAttribute("winningCards",poker.getPlayerHand().getHighCard());
                }else {
                    model.addAttribute("winningCards", poker.getPlayerHand().getWinningCards());
                    System.out.println( poker.getPlayerHand().getWinningCards());
                }
            }else {
                if (poker.getHustlerHand().getHandValue() == 1){
                    model.addAttribute("winningCards",poker.getHustlerHand().getHighCard());
                }else {
                    model.addAttribute("winningCards", poker.getHustlerHand().getWinningCards());
                    System.out.println( poker.getHustlerHand().getWinningCards());
                }
            }
        }else {
            model.addAttribute("playerCards", poker.getPlayer().getCardsInHand());
            model.addAttribute("hustlerCards", poker.getHustler().getCardsInHand());
            model.addAttribute("cardsOnTable", poker.getCardsOnTable());
            model.addAttribute("gameFinished", false);
            model.addAttribute("loggedIn", true);
            model.addAttribute("betHustler", poker.getCurrentBetHustler());
            model.addAttribute("betPlayer", poker.getCurrentBetPlayer());
            model.addAttribute("moneyOnTable", poker.getMoneyOnTable());
            model.addAttribute("creditsPlayer", poker.getPlayer().getCredits());
            model.addAttribute("creditsHustler", poker.getHustler().getCredits());
        }
        return "poker";
    }
    @GetMapping("/riseCustom")
    public String riseCustom(@RequestParam(name="customBet") int bet, Model model){
        poker.betPlayer(bet - poker.getCurrentBetPlayer());
        if (poker.isGameFinished()){
            model.addAttribute("playerCards", poker.getPlayer().getCardsInHand());
            model.addAttribute("hustlerCards", poker.getHustler().getCardsInHand());
            model.addAttribute("cardsOnTable", poker.getCardsOnTable());
            model.addAttribute("gameFinished", true);
            model.addAttribute("loggedIn", true);
            model.addAttribute("winner", poker.getWinner());
            model.addAttribute("creditsPlayer", poker.getPlayer().getCredits());
            model.addAttribute("creditsHustler", poker.getHustler().getCredits());
            model.addAttribute("winner", poker.getWinner());
            if (poker.getWinner().equals("player")){
                if (poker.getPlayerHand().getHandValue() == 1){
                    model.addAttribute("winningCards",poker.getPlayerHand().getHighCard());
                }else {
                    model.addAttribute("winningCards", poker.getPlayerHand().getWinningCards());
                }
            }else {
                if (poker.getHustlerHand().getHandValue() == 1){
                    model.addAttribute("winningCards",poker.getHustlerHand().getHighCard());
                }else {
                    model.addAttribute("winningCards", poker.getHustlerHand().getWinningCards());
                }
            }
        }else {
            model.addAttribute("playerCards", poker.getPlayer().getCardsInHand());
            model.addAttribute("hustlerCards", poker.getHustler().getCardsInHand());
            model.addAttribute("cardsOnTable", poker.getCardsOnTable());
            model.addAttribute("gameFinished", false);
            model.addAttribute("loggedIn", true);
            model.addAttribute("betHustler", poker.getCurrentBetHustler());
            model.addAttribute("betPlayer", poker.getCurrentBetPlayer());
            model.addAttribute("moneyOnTable", poker.getMoneyOnTable());
            model.addAttribute("creditsPlayer", poker.getPlayer().getCredits());
            model.addAttribute("creditsHustler", poker.getHustler().getCredits());
        }
        return "poker";
    }

}