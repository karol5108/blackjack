package sap.SecurityPlayApp.model;

import jakarta.persistence.Entity;
import lombok.*;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Card {
    public int value;

    public String rank;
    public String color;
    public String image;


    public void setValue(){
        if(rank.equals("K") ||rank.equals("Q") ||rank.equals("J") ||rank.equals( "10")) {
            this.value=10;
        }
        else if (rank.equals("A" )) {
            this.value=11;
        }
        else if (rank.equals("2")) {
            this.value=2;
        }
        else if (rank.equals("3")) {
            this.value=3;
        }
        else if (rank.equals("4" )) {
            this.value=4;
        }
        else if (rank.equals("5")) {
            this.value=5;
        }
        else if (rank.equals("6")) {
            this.value=6;
        }
        else if (rank.equals("7" )) {
            this.value=7;
        }
        else if (rank.equals("8")) {
            this.value=8;
        }
        else if (rank.equals("9")) {
            this.value=9;
        }
    }
    public void setValuePoker(){
        if(rank.equals("K") ) {
            this.value=13;
        }
        else if(rank.equals("Q")) {
            this.value=12;
        }
        else if (rank.equals("J")){
            this.value=11;
        }
        else if (rank.equals( "10")) {
            this.value=10;
        }
        else if (rank.equals("A" )) {
            this.value=14;
        }
        else if (rank.equals("2")) {
            this.value=2;
        }
        else if (rank.equals("3")) {
            this.value=3;
        }
        else if (rank.equals("4" )) {
            this.value=4;
        }
        else if (rank.equals("5")) {
            this.value=5;
        }
        else if (rank.equals("6")) {
            this.value=6;
        }
        else if (rank.equals("7" )) {
            this.value=7;
        }
        else if (rank.equals("8")) {
            this.value=8;
        }
        else if (rank.equals("9")) {
            this.value=9;
        }
    }

    @Override
    public String toString(){
        return this.color+this.rank;
    }
}

