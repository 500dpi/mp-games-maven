package edu.grinnell.csc207.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import edu.grinnell.csc207.util.BingoPrint;
import edu.grinnell.csc207.util.logic.BingoCard;
import edu.grinnell.csc207.util.logic.BingoRNG;

/**
 * A sample one-player Bingo game.
 *
 * The player generates numbers and attempts to match them to the
 * Bingo card. If they match 4 in a row, either horizontally, vertically,
 * or diagonally, they win.
 *
 * @author Sal Karki
 * @author Sara Jaljaa
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
    BingoCard card = null;

    // Take command line arguments to set card dimensions (2-5, inclusive)
    if (args.length != 0) {
      for (int i = 0; i < args.length; i++) {
        if ((args[i].equals("-s"))
            && ((i + 1) != args.length)) {
          Integer dimensions = Integer.valueOf(args[i + 1]);
          pen.println(dimensions);
          if (dimensions > 1 && dimensions < 6) {
            card = new BingoCard(dimensions);
            break;
          } // if
        } // if
      } // for
    } // if

    // If no command line arguments, default card is set to 4x4
    if (card == null) {
      card = new BingoCard(4);
    } // if

    BingoRNG random = new BingoRNG(card.length());

    // Print the initial game instructions and bingo card
    pen.println("Here is your bingo card!");
    BingoPrint.printCard(card);
    BingoPrint.printInstructions(pen, card.length());

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
