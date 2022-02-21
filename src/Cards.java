
public class Cards {

    private String[] suit = new String[54];
    private String[] cardName = new String[54];
    private int[] cardValue = new int[54];
    private int[] playerOwner = new int[54];

    public Cards() {
        for (int i = 0; i < 54; i++) {
            suit[i] = "";
            cardName[i] = "0";
            cardValue[i] = 0;
            playerOwner[i] = 97;
        }
    }

    public void removeOwnership(int index) {
        this.playerOwner[index] = 97;
    }

    public boolean checkFourCards(int index, int compPlayer) {
        return playerOwner[index] == compPlayer && playerOwner[index + 1] == compPlayer && playerOwner[index + 2] == compPlayer && playerOwner[index + 3] == compPlayer;
    }

    public void setCardSuit(String suit, int index) {
        this.suit[index] = suit;
    }

    public void setCardName(String cardName, int index) {
        this.cardName[index] = cardName;

        if ("Jack".equalsIgnoreCase(cardName)) {
            cardValue[index] = 11;
        } else if ("Queen".equalsIgnoreCase(cardName)) {
            cardValue[index] = 12;
        } else if ("King".equalsIgnoreCase(cardName)) {
            cardValue[index] = 13;
        } else if ("Ace".equalsIgnoreCase(cardName)) {
            cardValue[index] = 14;
        } else if ("2".equalsIgnoreCase(cardName)) {
            cardValue[index] = 15;
        } else if ("Joker".equalsIgnoreCase(cardName)) {
            cardValue[index] = 16;
        } else {
            cardValue[index] = Integer.parseInt(cardName);
        }
    }

    public void setPlayerOwner(int playerOwner, int index) {
        this.playerOwner[index] = playerOwner;
    }

    public String getCardName(int index) {
        return cardName[index];
    }

    public String getCardSuit(int index) {
        return suit[index];
    }

    public int getCardValue(int index) {
        return cardValue[index];
    }

    public int getPlayerOwner(int index) {
        return playerOwner[index];
    }

}
