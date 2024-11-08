package edu.grinnell.csc207.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import edu.grinnell.csc207.util.BingoPrint;
import edu.grinnell.csc207.util.logic.BingoCard;
import edu.grinnell.csc207.util.logic.BingoNumbers;

/**
 * A sample one-player Bingo game.
 * The player generates numbers and attempts to match them to the Bingo card.
 * If they match 4 in a row, either horizontally, vertically, or diagonally, they win.
 *
 * @author Samuel A. Rebelsky
 */
public class Bingo {

  // +------+--------------------------------------------------------
  // | Main |
  // +------+

  /**
   * Run a game of bingo.
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
    BingoPrint.printInstructions(pen);

    String userInput;
    Integer rand;

    // Game loop continues until a win condition is met or the user quits
    while (!card.diagWin() && !card.horizWin() && !card.vertWin()) {

      // Prompt the user to proceed or quit
      pen.println("Press Enter to generate a number, or type 'x' to quit.");
      userInput = eyes.nextLine();
      if (userInput.equalsIgnoreCase("x")) {
        pen.println("The game has ended!");
        break;
      } // if

      // Generate a random number and display it
      rand = random.getRandom();
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
      } // elif
    } // while

    // Announce the type of win if achieved
    if (card.diagWin()) {
      pen.println("Congratulations! You won with a diagonal bingo!");
    } else if (card.vertWin()) {
      pen.println("Congratulations! You won with a vertical bingo!");
    } else if (card.horizWin()) {
      pen.println("Congratulations! You won with a horizontal bingo!");
    } // elif

    // Close resources
    pen.close();
    eyes.close();
  } // main(String[])
} // class Bingo
