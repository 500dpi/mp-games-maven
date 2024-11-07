package edu.grinnell.csc207.util.logic;

import java.io.PrintWriter;

/**
 * Bingo card printing helpers.
 *
 * @author Sara Jaljaa
 */
public class BingoPrint {

  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The string that marks a matching bingo value.
   */
  private static final String ELIM = "XX";

  // /**
  //  * A divider for formatting the bingo card.
  //  */
  // private String line;

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

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
   * Prints the bingo card as a string with the
   * form:
   *
   * +----+----+----+----+
   * | XX | XX | XX | XX |
   * +----+----+----+----+
   * | XX | XX | XX | XX |
   * +----+----+----+----+
   * | XX | XX | XX | XX |
   * +----+----+----+----+
   * | XX | XX | XX | XX |
   * +----+----+----+----+
   *
   * Where XX is replaced with integer values.
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

    // Go through each row of the grid L->R, find the
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
