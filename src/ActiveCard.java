/*
Program by Kenny Ratcliffe
This is a recreation of the game "Tycoon" from a game called Persona 5 Royal. It's a card game where you try to get rid
of your cards before any other player.
 */

public class ActiveCard {
    private String cardName;
    private int cardValue;
    private int amountOfCard;

    public ActiveCard() {
        cardName = "";
        cardValue = 0;
        amountOfCard = 0;
    }

    public void resetActiveCard() {
        cardName = "";
        cardValue = 0;
        amountOfCard = 0;
    }

    public void setActiveCardNamePlus(String cardName) {
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
        } else {
            cardValue = Integer.parseInt(cardName);
        }
    }

    public void setActiveCardName(String cardName) {
        this.cardName = cardName;
    }

    public void setActiveCardValue(int cardValue) {
        this.cardValue = cardValue;
    }

    public void setAmountOfActiveCard(int amountOfCard) {
        this.amountOfCard = amountOfCard;
    }

    public String getActiveCardName() {
        return cardName;
    }

    public int getActiveCardValue() {
        return cardValue;
    }

    public int getAmountOfActiveCard() {
        return amountOfCard;
    }
}
