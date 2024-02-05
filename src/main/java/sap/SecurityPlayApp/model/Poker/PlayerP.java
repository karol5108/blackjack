package sap.SecurityPlayApp.model.Poker;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sap.SecurityPlayApp.model.Card;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerP {
    private String name;
    private List<Card> cardsInHand;
    private int credits;
    private boolean moveDone;
    private int currentBet;
    private String handResult;
    private int handResultValue;
    List<Card> winningCards;
    Card highCard;
}
