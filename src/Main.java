/*
Program by Kenny Ratcliffe
This is a recreation of the game "Tycoon" from a game called Persona 5 Royal. It's a card game where you try to get rid
of your cards before any other player.
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.Random;
import java.io.IOException;

public class Main {

    //Initializes keyboard Scanner
    private static final Scanner keyboard = new Scanner(System.in);

    //Asks the user what card value and suit they would like to pick
    private static String obtainStringInput(String outputMessage) {
        //Initializing variable
        String userStringInput;

        //Asks the user for information
        System.out.print(outputMessage);
        userStringInput = keyboard.nextLine();

        return userStringInput;
    }

    //Copies one ActiveCard object to another
    private static void cloneActiveCardObject(ActiveCard One, ActiveCard two) {
        One.setActiveCardName(two.getActiveCardName());
        One.setActiveCardValue(two.getActiveCardValue());
        One.setAmountOfActiveCard(two.getAmountOfActiveCard());
    }

    private static void cloneCards(Cards One, Cards two) {
        for (int row = 0; row < 54; row++) {
            One.setCardName(two.getCardName(row), row);
            One.setCardSuit(two.getCardSuit(row), row);
            One.setPlayerOwner(two.getPlayerOwner(row), row);
        }
    }

    private static int[] countPlayerCards(Cards APC1){
        int[] playerCards = new int[4];

        for(int p=0; p<54; p++){
            switch (APC1.getPlayerOwner(p)) {
                case 0:
                    playerCards[0]++;
                    break;
                case 1:
                    playerCards[1]++;
                    break;
                case 2:
                    playerCards[2]++;
                    break;
                case 3:
                    playerCards[3]++;
                    break;
            }
        }

        return playerCards;
    }

    //Creates a random number
    private static int obtainRandomForCard(int[] playerCards, boolean jokerCheck) {
        //Initializing variables
        Random ranCard = new Random();
        int randomNum = 4;

        //Quits out of loop when a proper player is selected
        while (randomNum == 4) {
            randomNum = ranCard.nextInt(4);

            if (playerCards[randomNum] > 12 && !jokerCheck) {
                randomNum = 4;
            }
        }
        return randomNum;
    }

    //Outputs players card to the screen
    private static void returnPlayerCards(Cards APC1, int row) {
        //If statement checks if it's the players card and then displays it to the screen
        if (APC1.getPlayerOwner(row) == 0) {
            if (APC1.getCardName(row).equals("Joker")) {
                System.out.println(APC1.getCardName(row));
            } else {
                System.out.println(APC1.getCardName(row) + " of " + APC1.getCardSuit(row));
            }
        }
    }

    //Assigns cards to a text file
    private static int createCards(String suit, String cardCounterS, boolean jokerCheck, int[] playerCards, int row, Cards APC1) {
        //Initializing variable
        int randomNum = obtainRandomForCard(playerCards, jokerCheck);

        APC1.setCardSuit(suit, row);
        APC1.setCardName(cardCounterS, row);
        APC1.setPlayerOwner(randomNum, row);

        return randomNum;
    }

    //This handles the computer players logic
    private static boolean compLogic(int compPlayer, ActiveCard AC1, Cards APC1) throws IOException {
        //Initializing variables
        boolean numberOfCards = false;
        int k = 0;
        BufferedWriter CardFile = new BufferedWriter(new FileWriter("CompTurn.txt", false));

        if (AC1.getActiveCardName().equalsIgnoreCase("Joker") && APC1.getPlayerOwner(0) == compPlayer - 1) {
            AC1.setAmountOfActiveCard(1);
            AC1.setActiveCardNamePlus("8");
            System.out.println("Player " + compPlayer + " used a 3 of spades on that joker!!");
            numberOfCards = true;
        } else {
            //For statement runs through every card
            for (int i = 0; i < 54; i++) {
                //This if statement checks if the computer owns
                if (APC1.getPlayerOwner(i) == compPlayer - 1 && APC1.getCardValue(i) > AC1.getActiveCardValue()) {
                    if (AC1.getAmountOfActiveCard() == 0 || AC1.getAmountOfActiveCard() == 1) {
                        AC1.setAmountOfActiveCard(1);
                        APC1.removeOwnership(i);
                        AC1.setActiveCardNamePlus(APC1.getCardName(i));
                        if (Objects.equals(APC1.getCardName(i), "8")) {
                            System.out.println("Player " + compPlayer + " flushed the field with an 8");
                        } else {
                            System.out.println("Player " + compPlayer + " put a " + AC1.getActiveCardName() + " down and there is " + AC1.getAmountOfActiveCard() + " of it");

                        }

                        numberOfCards = true;
                        break;

                    }

                    //If statement converts numbers in ranges of 4 to the lowest of that number, so 0-3 becomes 0
                    if (i < 4) {
                        k = 0;
                    } else if (i < 8) {
                        k = 4;
                    } else if (i < 12) {
                        k = 8;
                    } else if (i < 16) {
                        k = 12;
                    } else if (i < 20) {
                        k = 16;
                    } else if (i < 24) {
                        k = 20;
                    } else if (i < 28) {
                        k = 24;
                    } else if (i < 32) {
                        k = 28;
                    } else if (i < 36) {
                        k = 32;
                    } else if (i < 40) {
                        k = 36;
                    } else if (i < 44) {
                        k = 40;
                    } else if (i < 48) {
                        k = 44;
                    } else if (i < 52) {
                        k = 48;
                    }

                    if (AC1.getAmountOfActiveCard() == 2) {
                        for (int o = 0; o < 3; o++) {
                            for (int w = 1; w < 4 - o; w++) {
                                if (APC1.getPlayerOwner(k + o) == compPlayer - 1 && APC1.getPlayerOwner(k + o + w) == compPlayer - 1) {
                                    AC1.setAmountOfActiveCard(2);
                                    APC1.removeOwnership(k + o);
                                    APC1.removeOwnership(k + o + w);
                                    AC1.setActiveCardNamePlus(APC1.getCardName(k));

                                    if (Objects.equals(APC1.getCardName(k), "8")) {
                                        System.out.println("Player " + compPlayer + " flushed the field with an 8");
                                    } else {
                                        System.out.println("Player " + compPlayer + " put a " + AC1.getActiveCardName() + " down and there is " + AC1.getAmountOfActiveCard() + " of it");

                                    }

                                    i = 56;
                                    numberOfCards = true;
                                    break;
                                }
                            }
                        }
                    } else if (AC1.getAmountOfActiveCard() == 3) {
                        if (APC1.getPlayerOwner(k) == compPlayer - 1 && APC1.getPlayerOwner(k + 1) == compPlayer - 1) {
                            if (APC1.getPlayerOwner(k + 2) == compPlayer - 1) {
                                AC1.setAmountOfActiveCard(3);
                                AC1.setActiveCardNamePlus(APC1.getCardName(k));
                                APC1.removeOwnership(k);
                                APC1.removeOwnership(k + 1);
                                APC1.removeOwnership(k + 2);

                                if (Objects.equals(APC1.getCardName(k), "8")) {
                                    System.out.println("Player " + compPlayer + " flushed the field with an 8");
                                } else {
                                    System.out.println("Player " + compPlayer + " put a " + AC1.getActiveCardName() + " down and there is " + AC1.getAmountOfActiveCard() + " of it");

                                }

                                numberOfCards = true;
                                break;
                            }
                            else if(APC1.getPlayerOwner(k + 3) == compPlayer - 1){
                                AC1.setAmountOfActiveCard(3);
                                AC1.setActiveCardNamePlus(APC1.getCardName(k));
                                APC1.removeOwnership(k);
                                APC1.removeOwnership(k + 1);
                                APC1.removeOwnership(k + 3);

                                if (Objects.equals(APC1.getCardName(k), "8")) {
                                    System.out.println("Player " + compPlayer + " flushed the field with an 8");
                                } else {
                                    System.out.println("Player " + compPlayer + " put a " + AC1.getActiveCardName() + " down and there is " + AC1.getAmountOfActiveCard() + " of it");

                                }

                                numberOfCards = true;
                                break;
                            }
                        } else if (APC1.getPlayerOwner(k + 2) == compPlayer - 1 && APC1.getPlayerOwner(k + 3) == compPlayer - 1) {
                            if (APC1.getPlayerOwner(k) == compPlayer - 1) {
                                AC1.setAmountOfActiveCard(3);
                                AC1.setActiveCardNamePlus(APC1.getCardName(k));
                                APC1.removeOwnership(k);
                                APC1.removeOwnership(k + 2);
                                APC1.removeOwnership(k + 3);

                                if (Objects.equals(APC1.getCardName(k), "8")) {
                                    System.out.println("Player " + compPlayer + " flushed the field with an 8");
                                } else {
                                    System.out.println("Player " + compPlayer + " put a " + AC1.getActiveCardName() + " down and there is " + AC1.getAmountOfActiveCard() + " of it");

                                }

                                numberOfCards = true;
                                break;
                            }
                            else if (APC1.getPlayerOwner(k + 1) == compPlayer - 1){
                                AC1.setAmountOfActiveCard(3);
                                AC1.setActiveCardNamePlus(APC1.getCardName(k));
                                APC1.removeOwnership(k + 1);
                                APC1.removeOwnership(k + 2);
                                APC1.removeOwnership(k + 3);

                                if (Objects.equals(APC1.getCardName(k), "8")) {
                                    System.out.println("Player " + compPlayer + " flushed the field with an 8");
                                } else {
                                    System.out.println("Player " + compPlayer + " put a " + AC1.getActiveCardName() + " down and there is " + AC1.getAmountOfActiveCard() + " of it");

                                }

                                numberOfCards = true;
                                break;
                            }
                        }
                    } else if (AC1.getAmountOfActiveCard() == 4) {
                        if (APC1.checkFourCards(k, compPlayer - 1)) {
                            AC1.setAmountOfActiveCard(3);
                            AC1.setActiveCardNamePlus(APC1.getCardName(k));
                            APC1.removeOwnership(k);
                            APC1.removeOwnership(k + 1);
                            APC1.removeOwnership(k + 2);
                            APC1.removeOwnership(k + 3);
                            if (Objects.equals(APC1.getCardName(k), "8")) {
                                System.out.println("Player " + compPlayer + " flushed the field with an 8");
                            } else {
                                System.out.println("Player " + compPlayer + " put a " + AC1.getActiveCardName() + " down and there is " + AC1.getAmountOfActiveCard() + " of it");

                            }

                            numberOfCards = true;
                            break;
                        }
                    }

                }

            }
        }

        if (!numberOfCards) {
            System.out.println("Player " + compPlayer + " passed");
            CardFile.write("86\n0\n0\n0\n4\n86\n86");
            CardFile.newLine();
            CardFile.close();

        }

        return numberOfCards;
    }

    private static void assignCards(Cards APC1) {
        //Initializing variables
        int cardCounter = 3;
        int index = 52;

        int[] playerCards = new int[4];
        Arrays.fill(playerCards, 0);

        String cardCounterS = "Jack";

        //For statements initializes card values and assigns them to players equally
        for (int row = 0; row < 31; ) {

            playerCards[createCards("Spades", String.valueOf(cardCounter), false, playerCards, row, APC1)]++;
            row++;

            playerCards[createCards("Clubs", String.valueOf(cardCounter), false, playerCards, row, APC1)]++;
            row++;

            playerCards[createCards("Diamonds", String.valueOf(cardCounter), false, playerCards, row, APC1)]++;
            row++;

            playerCards[createCards("Hearts", String.valueOf(cardCounter), false, playerCards, row, APC1)]++;
            row++;

            cardCounter++;
        }

        //For statements initializes card values and assigns them to players equally
        for (int row = 32; row < 51; ) {

            playerCards[createCards("Spades", cardCounterS, false, playerCards, row, APC1)]++;
            row++;

            playerCards[createCards("Clubs", cardCounterS, false, playerCards, row, APC1)]++;
            row++;

            playerCards[createCards("Diamonds", cardCounterS, false, playerCards, row, APC1)]++;
            row++;

            playerCards[createCards("Hearts", cardCounterS, false, playerCards, row, APC1)]++;
            row++;

            if (row == 36) {
                cardCounterS = "Queen";
            } else if (row == 40) {
                cardCounterS = "King";
            } else if (row == 44) {
                cardCounterS = "Ace";
            } else {
                cardCounterS = "2";
            }
        }

        playerCards[createCards(" ", "Joker", true, playerCards, index, APC1)]++;

        index++;

        playerCards[createCards(" ", "Joker", true, playerCards, index, APC1)]++;
    }

    public static void main(String[] args) throws IOException {

        //Initializing variables
        Cards APC1 = new Cards();
        Cards APCB1 = new Cards();
        PlayerChoice PC1 = new PlayerChoice();
        ActiveCard AC1 = new ActiveCard();
        ActiveCard ACB1 = new ActiveCard();

        boolean win = false;
        boolean playAgain = true;
        boolean hasCheck;
        boolean workCheck;
        boolean CardCheck = false;

        int numberOfCards;
        int turnNumber = 1;
        int numbersOfPasses = 0;

        String[] suits = new String[6];
        Arrays.fill(suits, "0");

        int[] playerCards = new int[4];
        Arrays.fill(playerCards, 0);

        int[] playerPoints = new int[4];
        Arrays.fill(playerPoints, 0);

        //Call for assignCards() which assigns cards created by createCards() to each player
        assignCards(APC1);

        //While loop plays as long as the user wants to, i.e. as long as playAgain is true
        while (playAgain) {
            //While loop runs as long as win does not equal true
            while (!win) {

                if (AC1.getActiveCardValue() != 8) {
                    //Says the active card as long as turn number is not 1
                    if (turnNumber != 1) {
                        System.out.println("\nThe active card is " + AC1.getActiveCardName() + " and there is " + AC1.getAmountOfActiveCard() + " of it\n");
                    }

                    playerCards = countPlayerCards(APC1);

                    //Says how many cards each player has
                    for (int i = 0; i < 4; i++) {
                        System.out.println("Player " + (i + 1) + " has " + playerCards[i] + " cards left");
                    }

                    //Says the players cards
                    for (int i = 0; i < 54; i++) {
                        returnPlayerCards(APC1, i);
                    }

                    //Copies card to cardBackup
                    cloneCards(APCB1, APC1);

                    //First parameter is the one that will be copied to, second one is the one that will be copied from
                    cloneActiveCardObject(ACB1, AC1);

                    //Resets hasCheck
                    hasCheck = false;

                    //If hasCheck is false it loops
                    while (!hasCheck) {

                        //The first time it runs it explains the input format
                        if (turnNumber == 1) {
                            //Gets the player input for card name
                            PC1.setCardName(obtainStringInput("What card would you like to pick (name only): "));

                            if (!PC1.getCardName().equalsIgnoreCase("pass")) {
                                //Gets the player input for suit
                                PC1.setSuit(obtainStringInput("What suit would you like to pick (first letter only): "));
                            }
                        } else {
                            //Gets the player input for card name
                            PC1.setCardName(obtainStringInput("What card would you like to pick: "));

                            if (!PC1.getCardName().equalsIgnoreCase("pass")) {
                                //Gets the player input for suit
                                PC1.setSuit(obtainStringInput("What suit would you like to pick: "));
                            }
                        }

                        //If the player chooses to pass then it breaks out of the hasCheck while loop
                        if (PC1.getSuit().equalsIgnoreCase("p")) {
                            CardCheck = false;
                            break;
                        } else {
                            CardCheck = true;
                        }

                        if (turnNumber != 1) {
                            if (((PC1.getCardValue() > AC1.getActiveCardValue()) && ((PC1.getSuit().length() == AC1.getAmountOfActiveCard()) || AC1.getAmountOfActiveCard() == 0))) {
                                workCheck = true;

                            } else if (PC1.getCardValue() == 3 && Objects.equals(PC1.getSuit(), "s") && AC1.getActiveCardValue() == 8) {
                                workCheck = false;
                                hasCheck = true;
                                APC1.removeOwnership(0);
                                AC1.setActiveCardNamePlus("8");
                                AC1.setAmountOfActiveCard(1);
                            } else {
                                workCheck = false;

                                //Copies cardBackup to card
                                cloneCards(APC1, APCB1);

                                //First parameter is the one that will be copied to, second one is the one that will be copied from
                                cloneActiveCardObject(AC1, ACB1);
                            }
                        } else {
                            workCheck = true;
                        }

                        //If workCheck is true this runs
                        if (workCheck) {

                            numberOfCards = 0;

                            //For statement checks every value in the suitValue string
                            for (int i = 0; i < PC1.getSuit().length(); i++) {
                                suits[i] = String.valueOf(PC1.getSuit().charAt(i));

                                //These if statements check if the suitValue character selected equals a letter specified
                                if (suits[i].equalsIgnoreCase("s")) {

                                    if (APC1.getPlayerOwner((PC1.getCardValue() * 4) - 12) == 0) {
                                        hasCheck = true;
                                        APC1.removeOwnership((PC1.getCardValue() * 4) - 12);
                                        numberOfCards++;

                                        AC1.setActiveCardNamePlus(PC1.getCardName());
                                        AC1.setAmountOfActiveCard(numberOfCards);

                                    } else {
                                        hasCheck = false;

                                        cloneCards(APC1, APCB1);

                                        //First parameter is the one that will be copied to, second one is the one that will be copied from
                                        cloneActiveCardObject(AC1, ACB1);

                                        break;
                                    }
                                }

                                if (suits[i].equalsIgnoreCase("c")) {
                                    if (APC1.getPlayerOwner((PC1.getCardValue() * 4) - 11) == 0) {
                                        hasCheck = true;
                                        APC1.removeOwnership((PC1.getCardValue() * 4) - 11);
                                        numberOfCards++;

                                        AC1.setActiveCardNamePlus(PC1.getCardName());
                                        AC1.setAmountOfActiveCard(numberOfCards);

                                    } else {
                                        hasCheck = false;

                                        cloneCards(APC1, APCB1);

                                        //First parameter is the one that will be copied to, second one is the one that will be copied from
                                        cloneActiveCardObject(AC1, ACB1);

                                        break;
                                    }
                                }

                                if (suits[i].equalsIgnoreCase("d")) {
                                    if (APC1.getPlayerOwner((PC1.getCardValue() * 4) - 10) == 0) {
                                        hasCheck = true;
                                        APC1.removeOwnership((PC1.getCardValue() * 4) - 10);
                                        numberOfCards++;

                                        AC1.setActiveCardNamePlus(PC1.getCardName());
                                        AC1.setAmountOfActiveCard(numberOfCards);

                                    } else {
                                        hasCheck = false;

                                        cloneCards(APC1, APCB1);

                                        //First parameter is the one that will be copied to, second one is the one that will be copied from
                                        cloneActiveCardObject(AC1, ACB1);

                                        break;
                                    }
                                }

                                if (suits[i].equalsIgnoreCase("h")) {
                                    if (APC1.getPlayerOwner((PC1.getCardValue() * 4) - 9) == 0) {
                                        hasCheck = true;
                                        APC1.removeOwnership((PC1.getCardValue() * 4) - 9);
                                        numberOfCards++;

                                        AC1.setActiveCardNamePlus(PC1.getCardName());
                                        AC1.setAmountOfActiveCard(numberOfCards);

                                    } else {
                                        hasCheck = false;

                                        cloneCards(APC1, APCB1);

                                        //First parameter is the one that will be copied to, second one is the one that will be copied from
                                        cloneActiveCardObject(AC1, ACB1);

                                        break;
                                    }
                                }

                                if (suits[i].equalsIgnoreCase("j")) {
                                    if (APC1.getPlayerOwner(52) == 0) {
                                        hasCheck = true;
                                        APC1.removeOwnership(52);
                                        numberOfCards++;

                                        AC1.setActiveCardNamePlus(PC1.getCardName());
                                        AC1.setAmountOfActiveCard(numberOfCards);

                                    } else if (APC1.getPlayerOwner(53) == 0) {
                                        hasCheck = true;

                                        APC1.removeOwnership(53);
                                        numberOfCards++;

                                        AC1.setActiveCardNamePlus(PC1.getCardName());
                                        AC1.setAmountOfActiveCard(numberOfCards);

                                    } else {
                                        hasCheck = false;

                                        cloneCards(APC1, APCB1);

                                        //First parameter is the one that will be copied to, second one is the one that will be copied from
                                        cloneActiveCardObject(AC1, ACB1);

                                        break;
                                    }
                                }
                            }
                            System.out.println("Active card 2 slot: " + AC1.getActiveCardValue());
                        }
                    }

                } else {
                    CardCheck = false;
                }


                if (CardCheck) {
                    numbersOfPasses = 0;
                } else {
                    numbersOfPasses++;
                }

                if (numbersOfPasses == 3) {
                    AC1.resetActiveCard();
                    numbersOfPasses = 0;
                }

                if (playerCards[0] == 0) {
                    win = true;
                    break;
                }

                //End of player turn

                //Player 2 turn
                if (AC1.getActiveCardValue() != 8) {

                    CardCheck = compLogic(2, AC1, APC1);

                    if (playerCards[1] == 0) {
                        win = true;
                        break;
                    }

                } else {
                    CardCheck = false;
                }

                if (CardCheck) {
                    numbersOfPasses = 0;
                } else {
                    numbersOfPasses++;
                }

                if (numbersOfPasses == 3) {
                    AC1.resetActiveCard();
                    numbersOfPasses = 0;
                }

                //Player 3 turn
                if (AC1.getActiveCardValue() != 8) {

                    CardCheck = compLogic(3, AC1, APC1);

                    if (playerCards[2] == 0) {
                        win = true;
                        break;
                    }

                } else {
                    CardCheck = false;
                }

                if (CardCheck) {
                    numbersOfPasses = 0;
                } else {
                    numbersOfPasses++;
                }

                if (numbersOfPasses == 3) {
                    AC1.resetActiveCard();
                    numbersOfPasses = 0;
                }

                //Player 4 turn
                if (AC1.getActiveCardValue() != 8) {

                    CardCheck = compLogic(4, AC1, APC1);

                    if (playerCards[3] == 0) {
                        win = true;
                        break;
                    }

                } else {
                    CardCheck = false;
                }

                if (CardCheck) {
                    numbersOfPasses = 0;
                } else {
                    numbersOfPasses++;
                }

                if (numbersOfPasses == 3) {
                    AC1.resetActiveCard();
                    numbersOfPasses = 0;
                }

                turnNumber++;
            }

            int person1 = 0;
            int person2 = 0;

            for(int i=0; i<4; i++){
                person1 = i;
                person2 = i;
                for(int w=1; w<4; w++){
                    person2+=w;

                    if(person2>3){
                        if(person2==4){
                            person2 = 0;
                        }
                        else if(person2==5){
                            person2 = 1;
                        }
                        else if(person2==6){
                            person2 = 2;
                        }
                    }

                    if(person1>person2){

                    }
                }
            }


            playAgain = false;
        }

    }

}



