/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threeinarowgame;

import java.util.Scanner;

/**
 *
 * @author Benjamin
 */
public class ThreeInARowGame {

    private String[][] table; // spelbord matris
    static char playerLebel;
    static boolean playAgain = true;
    static Scanner inputLetter;

    // klass konstruktör
    public ThreeInARowGame() {
        table = new String[3][3]; // reservera minnesutrymme för spelbord
        inputLetter = new Scanner(System.in); // read from standard in
        int moves = 0;
        System.out.println("Detta ett spelprogram för två spelare.\n"
                + "Den första att få sin tagg i tre platser i rad vinner spelet.");
        System.out.println();

        System.out.println("Låt oss spela tre-i-rad (ThreeInARowGame). ");
        System.out.print("Vi har två spelare X och O. Välj som spelar första X eller O !: ");
        playerLebel = inputLetter.next(".").charAt(0); //Acceptera spelare tag och konvertera till övre brev 
        playerLebel = Character.toUpperCase(playerLebel);
        if (Character.isLetter(playerLebel)) {
            createBoard(); // kalla metod för att visa spelplanen.
        }else{
            System.out.println("Enter a letter char. Try again");
            return;
        }
        

        // while statement - att iterera att acceptera koordinaterna och kontrollera vinnare eller rita
        while (moves <= 9) {
            if (moves > 8) { // if-statement att kontrollera om spela slut att rita
                //createBoard();
                if (checkWinner(playerLebel)) { // if-statement att kontrollera om spelare X är vinner
                 System.out.println(playerLebel + " You Win!!!");
                 break;
                 }
                 if (checkWinner(playerLebel)) { //if-statement att kontrollera om spelare O är vinner
                 System.out.println(playerLebel + " You Win!!!");
                 break;
                 }
                System.out.println("A Draw!");
                break;
            }

            if (playerLebel == 'X') { //if-statement att kontrollera om spelare tag är X eller O
                makeMove(inputLetter, playerLebel); // metod för att ta emot spelare X spel koordinater
                if (moves >= 2) {
                    
                    if (checkWinner(playerLebel)) { // if-statement att kontrollera om spelare X är vinner
                        createBoard(); // kalla metod för att visa spelplanen.
                        System.out.println("Player: " + playerLebel + " You Win!!!");
                        break;
                    }
                }
            } else {
                makeMove(inputLetter, playerLebel); //metod för att ta emot spelare O spel koordinater
                if (moves >= 2) {
                    if (checkWinner(playerLebel)) { //if-statement att kontrollera om spelare O är vinner
                        createBoard(); // kalla metod för att visa spelplanen.
                        System.out.println("Player: " + playerLebel + " You Win!!!");
                        break;
                    }
                }
            }

            createBoard(); // kalla metod för att visa spelplanen.
            moves++; // räknare för att hålla räkningen på antalet drag under spelets gång

            if (playerLebel == 'X') {
                playerLebel = 'O';
            } else {
                playerLebel = 'X';
            }
        }
    }

    /**
     * @param args the command line arguments main metoden för att starta
     * programmet.
     */
    public static void main(String[] args) {
        // TODO code application logic here
       // String answer;
        while (playAgain) {            
            ThreeInARowGame threeInARowGame = new ThreeInARowGame();
            System.out.println();
            System.out.print("Play once more (Y/N)? ");
            //answer = inputLetter.next().toUpperCase();
            playerLebel = inputLetter.next(".").charAt(0); //Acceptera spelare tag och konvertera till övre brev 
        
            if (Character.toUpperCase(playerLebel)=='N') {
                playAgain = false;
            }
        }
        System.out.println("Hej da! Välkommen åter!");
        
    }

    // metod för att visa spelplanen.
    public void createBoard() {

        System.out.println();
        for (String[] table1 : table) {
            for (int j = 0; j < table1.length; j++) {
                if (table1[j] == null) {
                    System.out.print("_");
                } else {
                    System.out.print(table1[j]);
                }
                if (j < 2) {
                    System.out.print(" | ");
                } else {
                    System.out.println();
                }
            }
        }
    }

    // metod för att processen spelare drag. 
    // Acceptera indata och kontrollera om koordinaten är tom eller ockupera 
    // (Accept input and check if coordinate is empty or occupy)
    public void makeMove(Scanner enteredLetter, char playerAlphabet) {
        int row;
        int column;
        Boolean goodInput = true;
        while (goodInput) {
            row = -1;
            column = -1;
            System.out.println("Player " + playerAlphabet + " enter coordinates to play ");
            System.out.print(playerAlphabet + ": 0 - 2) : ");
            if (enteredLetter.hasNextInt()) { // måste vara heltal
                row = enteredLetter.nextInt();
            }
            System.out.print(playerAlphabet + ": 0 - 2) : ");
            if (enteredLetter.hasNextInt()) {
                column = enteredLetter.nextInt();
            } else {
                enteredLetter.nextLine(); // consume a line without an integer
                System.out.println("Both inputs must be integers between 0 and 2.");
                continue;
            }

            // kontrollera om koordinaterna ligger inom intervallet (must be in the right coordinate range)
            if ((row < 0) || (row > 2) || (column < 0) || (column > 2)) {
                System.out.println("Both inputs must be integers between 0 and 2.");
            } // make sure the space is not occupied
            else if (table[row][column] != null) {
                System.out.println("That location is occupied");
            } else {
                table[row][column] = Character.toString(playerAlphabet);
                return;
            }
        }
    }

    // metod för att kontrollera vinnaren av spelet
    public boolean checkWinner(char playerAlphabet) {
        int playInRow; // Kontrollera raden för vinnaren
        int playDiagonal1 = 0; //kontrollera diagonalen för vinnaren
        int playDiagonal2 = 0; //kontrollera diagonalen för vinnaren

        int[] playInColumn = new int[table[0].length]; // assumes square table

        for (int i = 0; i < table.length; i++) {
            playInRow = 0;
            for (int j = 0; j < table[i].length; j++) {
                if (table[i][j] == null) {
                    continue;
                }
                if (table[i][j].equals(Character.toString(playerAlphabet))) {
                    playInRow++;
                    playInColumn[j]++;
                    if (i == j) {
                        playDiagonal1++;
                    } 
                    if ((i + j) == 2) {
                        playDiagonal2++;
                    }
                }
            }
            if (playInRow == 3) {
                return true;
            }
        }
        if (playDiagonal1 == 3 || playDiagonal2 == 3) {
            return true;
        }
        for (int i = 0; i < playInColumn.length; i++) {
            if (playInColumn[i] == 3) {
                return true;
            }
        }
        return false;
    }
}
