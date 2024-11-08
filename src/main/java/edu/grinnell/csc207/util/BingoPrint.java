package edu.grinnell.csc207.util;

import java.io.PrintWriter;
import edu.grinnell.csc207.util.logic.BingoCard;

/**
 * Bingo card printing helpers.
 *
 * @author Sara Jaljaa
 * @author Sal Karki
 */
public class BingoPrint {

  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The string that marks a matching bingo value.
   */
  private static final String ELIM = "XX";

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Print the instructions for the game.
   *
   * @param pen
   *    The printwriter used to print the instructions.
   * @param dimen
   *    The dimensions of the bino card.
   */
  public static void printInstructions(PrintWriter pen, Integer dimen) {
    StringBuilder greet = new StringBuilder("Welcome to Bingo!\n");
    greet.append("The game board is a " + new String(dimen + "x" + dimen + " "));
    greet.append("""
        bingo card filled with random numbers.\n
        The goal is to mark down the random numbers given with the numbers on your bingo card.\n
        If you match any""");
    greet.append(" " + dimen + " in a row, you win!\n");
    pen.println(greet);
  } // printInstructions(PrintWriter)

  /**
   * The horizontal divider between each row.
   *
   * @param length
   *    The length of the bingo card's sides.
   * @return
   *    The horizontal divider string.
   */
  public static String horizLine(int length) {
    return "\n" + ("+----").repeat(length) + "+\n";
  } // line(int)

  /**
   * Prints the bingo card.
   *
   * @param card
   *    The bingo card.
   */
  public static void printCard(BingoCard card) {
    PrintWriter pen = new PrintWriter(System.out, true);
    pen.println(cardToString(card));
  } // printCard(BingoCard)

  /**
   * Creates an image of the bingo card.
   *
   * @param card
   *    The bingo card.
   * @return
   *    The image of the bingo card as a string.
   */
  public static String cardToString(BingoCard card) {
    StringBuilder str = new StringBuilder(horizLine(card.length()));
    String current;
    String div = "";

    // Go through each row of the grid L-->R, find the
    // value at that point, format it, then concatenate it
    for (int rows = 0; rows < card.length(); rows++) {
      for (int cols = 0; cols < card.length(); cols++) {
        current = (card.get(rows, cols).toString());

        // Set bingo cells to the marked-off string (XX)
        if (current.equals("0")) {
          current = ELIM;
        } // if

        // Single digit space adjust
        if (current.length() == 1) {
          current = "0" + current;
        } // if

        // Set end divider depending on position in row
        if (cols == card.length() - 1) {
          div = " |";
        } else {
          div = " ";
        } // elif

        // Concatenate the current value between vertical
        // dividers
        str.append("| ");
        str.append(current);
        str.append(div);
      } // for (cols)

      // End of the row, concat the horizontal divider
      str.append(horizLine(card.length()));
    } // for (rows)
    return str.toString();
  } // cardToString(BingoCard)
} // BingoGraphics class
