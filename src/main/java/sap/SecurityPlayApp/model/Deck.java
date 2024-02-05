package sap.SecurityPlayApp.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;


@ToString
@Getter
@Setter
public class Deck {
    private Card[] cards = new Card[52];
    private String[] ranks = {"A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2"};
    private String[] colors = {"♥", "♠", "♦", "♣"};

    public Deck(String pokerOrBJ) {
        int k = 0;
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 13; i++) {
                cards[k] = new Card();
                cards[k].setColor(colors[j].toString());
                cards[k].setRank(ranks[i].toString());
                String fileName = new String( ranks[i].toString() + colors[j].toString() + ".png");
                //System.out.println(fileName);
                cards[k].setImage(fileName);
                if(pokerOrBJ.equals("bj"))
                    cards[k].setValue();
                else
                    cards[k].setValuePoker();
                k++;
            }
        }
    }
    public void shuffle() {
        Collections.shuffle(Arrays.asList(cards));
    }
    public Card getCard(int n){
        return cards[n];
    }
}
