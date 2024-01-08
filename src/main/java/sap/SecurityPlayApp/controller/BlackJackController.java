package sap.SecurityPlayApp.controller;

import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sap.SecurityPlayApp.model.BlackJack;
import sap.SecurityPlayApp.model.Deck;
import sap.SecurityPlayApp.model.PlayerBJ;

import java.io.IOException;

@Controller
@RequestMapping
public class BlackJackController {
    private BlackJack blackJack;
    private Deck deck;
    private PlayerBJ player;
    private PlayerBJ hustler;

    @GetMapping("/login")
    public String test(){
        return "login";
    }


    @GetMapping
    public String tittlePage(Authentication authentication,Model model){
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("loggedIn", true);
            model.addAttribute("username", authentication.getName());
        } else {
            model.addAttribute("loggedIn", false);
        }
        return "tittle";
    }

    @GetMapping("/start")
    public String startGame(Model model){

            deck = new Deck();
            player = new PlayerBJ();
            hustler = new PlayerBJ();

            blackJack = new BlackJack();
            blackJack.setDeck(deck);
            blackJack.setPlayer(player);
            blackJack.setHustler(hustler);

            blackJack.startGame();
            //blackJack.getOne();
            //blackJack.getOneHustler();
            System.out.println( blackJack.checkWinner());

        model.addAttribute("playerCards", blackJack.getPlayer().getCardsInHand());
        model.addAttribute("hustlerCards", blackJack.getHustler().getCardsInHand());
        model.addAttribute("playerCardsValue", blackJack.getPlayer().getResult());
        model.addAttribute("hustlerCardsValue", (blackJack.getHustler().getResult() - blackJack.getHustler().getCardsInHand().get(1).getValue()));
        model.addAttribute("gameFinished", false);
        model.addAttribute("loggedIn", true);

        return "blackjack";
    }
    @PostMapping("/get-one-player")
    public String getOnePlayer(Model model){
        model.addAttribute("loggedIn", true);
        if(!blackJack.isStayButtonClicked()){
            blackJack.getOne();
            model.addAttribute("playerCards", blackJack.getPlayer().getCardsInHand());
            model.addAttribute("hustlerCards", blackJack.getHustler().getCardsInHand());
            if(blackJack.getPlayer().getResult() > 21){
                model.addAttribute("winner", blackJack.checkWinner());
                model.addAttribute("gameFinished", true);
                model.addAttribute("playerCardsValue", blackJack.getPlayer().getResult());
                model.addAttribute("hustlerCardsValue", blackJack.getHustler().getResult());// Dodaj informację o zakończeniu gry
                blackJack.finishGame();
                blackJack.setStayButtonClicked(true);
            }else {
                model.addAttribute("gameFinished", false);
                model.addAttribute("playerCardsValue", blackJack.getPlayer().getResult());
                model.addAttribute("hustlerCardsValue", (blackJack.getHustler().getResult() - blackJack.getHustler().getCardsInHand().get(1).getValue()));
            }
        }
        return "blackjack";
    }
    @PostMapping("/finish-game")
    public String finishGame(Model model) {
        model.addAttribute("loggedIn", true);
        if(!blackJack.isStayButtonClicked()){
            blackJack.getOneHustler();
            if (blackJack.getHustler().getResult() < 18){
                blackJack.getOneHustler();
            }
            model.addAttribute("playerCards", blackJack.getPlayer().getCardsInHand());
            model.addAttribute("hustlerCards", blackJack.getHustler().getCardsInHand());
            model.addAttribute("winner", blackJack.checkWinner());
            model.addAttribute("gameFinished", true);
            model.addAttribute("playerCardsValue", blackJack.getPlayer().getResult());
            model.addAttribute("hustlerCardsValue", blackJack.getHustler().getResult());// Dodaj informację o zakończeniu gry

            blackJack.finishGame();
        }
        return "blackjack";
    }
    @PostMapping("/new-game")
    public String newGame(Model model){
        if(blackJack.isStayButtonClicked()) {
            deck.shuffle();
            blackJack.startGame();
        }
            model.addAttribute("playerCards", blackJack.getPlayer().getCardsInHand());
            model.addAttribute("hustlerCards", blackJack.getHustler().getCardsInHand());
            model.addAttribute("playerCardsValue", blackJack.getPlayer().getResult());
            model.addAttribute("hustlerCardsValue", (blackJack.getHustler().getResult() - blackJack.getHustler().getCardsInHand().get(1).getValue()));
            model.addAttribute("loggedIn", true);
            model.addAttribute("gameFinished", false);


        return "blackjack";
    }
}
