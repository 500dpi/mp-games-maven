package edu.grinnell.csc207.sample;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import edu.grinnell.csc207.util.logic.BingoCard;
import edu.grinnell.csc207.util.matrix.Matrix;

/**
 * A sample one-player game (is that a puzzle?). Intended as a potential
 * use of our Matrix interface.
 *
 * @author Samuel A. Rebelsky
 */
public class Game1P {
  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The default width.
   */
  static final int DEFAULT_WIDTH = 10;

  /**
   * The default number of rows.
   */
  static final int DEFAULT_HEIGHT = 8;

  // +----------------+----------------------------------------------
  // | Helper methods |
  // +----------------+

  /**
   * Print the insturctions.
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

  /**
   * Print the results of the game.
   *
   * @param pen
   *   What to use for printing.
   * @param board
   *   The game board at the end.
   */
  static void printState(PrintWriter pen, Matrix<String> board) {

  } 



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

    //creates a new 4x4 bingo card
    BingoCard card = new BingoCard(4);
    String userInput;
    // Get started
    printInstructions(pen);
    //while loop ends when some kind of match is detected
    while(!card.diagWin() && !card.horizWin() && !card.vertWin()) {

      //detect when the user clicks enter to play or escape to end
      pen.println("Click enter to generate a number, or 'x' to end the game");
      userInput = eyes.nextLine();
      if(userInput.equals("x")) {
        pen.println("The game has ended!");
        break;
      }

      /*print the board and random number generator here here
        ex. printstate(pen, card);
    */

    /*generate a random value between some range

    ex. int randNumber = random(1-25);
    */

    //card.match(randomNumber); <-- assumes that some visual display of a match will be done in the Bingo or BingoGraphics class

    //could also add a secondary cue of a match: pen.println("You matched!")/pen.println("No matches")

    } // while
    if(card.diagWin()) {
      pen.println("You won through a diagonal bingo!");
    } else if (card.vertWin()) {
      pen.println("You won through a vertical bingo!");
    } else {
      pen.println("You won through a horizontal win!");
    }
    pen.close();
    eyes.close();
  } // main(String[])
} // class Game1P
