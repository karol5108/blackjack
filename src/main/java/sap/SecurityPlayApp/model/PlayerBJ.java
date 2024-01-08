package sap.SecurityPlayApp.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerBJ {
   private int nbCards;
   private int result;
   private List<Card> cardsInHand;

   public int calculatePoints(){
      int totalPoints = 0;
      int nbOfAces = 0;
      for(Card card : cardsInHand){
         int cardValue = card.getValue();
         if(cardValue == 11){
            nbOfAces ++;
            totalPoints += cardValue;
         }else {
            totalPoints += cardValue;
         }
      }
      while (nbOfAces > 0 && totalPoints > 21 ){
         totalPoints -= 10;
         nbOfAces--;
      }
      return totalPoints;
   }
   public boolean hasCardWithValue(int valueToCheck) {
      for (Card card : cardsInHand) {
         if (card.getValue() == valueToCheck) {
            return true;
         }
      }
      return false;
   }
}
