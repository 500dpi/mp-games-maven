package edu.grinnell.csc207.sample;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import edu.grinnell.csc207.util.logic.BingoCard;
import edu.grinnell.csc207.util.logic.BingoNumbers;
import edu.grinnell.csc207.util.logic.BingoPrint;

/**
 * A sample one-player Bingo game.
 * The player generates numbers and attempts to match them to the Bingo card.
 * If they match 4 in a row, either horizontally, vertically, or diagonally, they win.
 * 
 * @author Samuel A. Rebelsky
 */
public class Game1P {

  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  // +----------------+----------------------------------------------
  // | Helper methods |
  // +----------------+

  /**
   * Print the instructions for the game.
   *
   * @param pen
   *  The printwriter used to print the instructions.
   */
  public static void printInstructions(PrintWriter pen) {
    pen.println("""
                Welcome to Bingo!

                The game board is a 4x4 bingo card filled with random numbers.

                The goal is to mark down the random numbers given with the numbers on your bingo card.

                If you match 4 in a row, you win!

                """);
  } // printInstructions(PrintWriter)

  // +------+--------------------------------------------------------
  // | Main |
  // +------+

  /**
   * Run the game.
   *
   * @param args
   *   Command-line arguments.
   */
  public static void main(String[] args) throws IOException {
    PrintWriter pen = new PrintWriter(System.out, true);
    Scanner eyes = new Scanner(System.in);

    // Initialize a new 4x4 bingo card
    BingoCard card = new BingoCard(4);
    BingoNumbers random = new BingoNumbers();

    // Print the initial game instructions and bingo card
    pen.println("Here is your bingo card!");
    BingoPrint.printCard(card);
    printInstructions(pen);

    String userInput;

    // Game loop continues until a win condition is met or the user quits
    while (!card.diagWin() && !card.horizWin() && !card.vertWin()) {

      // Prompt the user to proceed or quit
      pen.println("Press Enter to generate a number, or type 'x' to quit.");
      userInput = eyes.nextLine();
      if (userInput.equalsIgnoreCase("x")) {
        pen.println("The game has ended!");
        break;
      }

      // Generate a random number and display it
      random.findRandom();
      Integer rand = random.getRandom();
      pen.println("The next number is ---> " + rand);

      // Check for a match on the card
      boolean isMatch = card.match(rand);

      // Print the updated bingo card to show any changes
      BingoPrint.printCard(card);

      // Notify the user if a match was found
      if (isMatch) {
        pen.println("You matched a number!");
      } else {
        pen.println("No match found.");
      }
    }

    // Announce the type of win if achieved
    if (card.diagWin()) {
      pen.println("Congratulations! You won with a diagonal bingo!");
    } else if (card.vertWin()) {
      pen.println("Congratulations! You won with a vertical bingo!");
    } else if (card.horizWin()) {
      pen.println("Congratulations! You won with a horizontal bingo!");
    }

    // Close resources
    pen.close();
    eyes.close();
  } // main(String[])
} // class Game1P
