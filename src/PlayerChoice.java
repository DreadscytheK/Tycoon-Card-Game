/*
Program by Kenny Ratcliffe
This is a recreation of the game "Tycoon" from a game called Persona 5 Royal. It's a card game where you try to get rid
of your cards before any other player.
 */

public class PlayerChoice {
    private String suit;
    private String cardName;
    private int cardValue;

    public PlayerChoice(){
        suit = "";
        cardName = "";
        cardValue = 0;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;

        if ("Jack".equalsIgnoreCase(cardName)) {
            cardValue = 11;
        } else if ("Queen".equalsIgnoreCase(cardName)) {
            cardValue = 12;
        } else if ("King".equalsIgnoreCase(cardName)) {
            cardValue = 13;
        } else if ("Ace".equalsIgnoreCase(cardName)) {
            cardValue = 14;
        } else if ("2".equalsIgnoreCase(cardName)) {
            cardValue = 15;
        } else if ("Joker".equalsIgnoreCase(cardName)) {
            cardValue = 16;
        } else if ("Pass".equalsIgnoreCase(cardName)) {
            suit = "p";
        } else {
            cardValue = Integer.parseInt(cardName);
        }
    }

    public String getSuit() {
        return suit;
    }

    public String getCardName() {
        return cardName;
    }

    public int getCardValue() {
        return cardValue;
    }
}
