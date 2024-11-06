package edu.grinnell.csc207.util.game;

import java.io.PrintWriter;

/**
 * Bingo card printing helpers.
 */
public class BingoGraphics {

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
   * Where XX is replaced with values.
   *
   * @param card
   *    The bingo card.
   */
  public static void printCard(BingoCard card) {
    PrintWriter pen = new PrintWriter(System.out, true);
    pen.println(cardToString(card));
  } // printCard(BingoCard)

  /**
   * Creates a string from the bingo card.
   *
   * @param card
   *    The bingo card.
   * @return
   *    The string format of the bingo card.
   */
  public static String cardToString(BingoCard card) {
    String line = "+----+----+----+----+\n";
    String current;

    for (int rows = 0; rows < card.dimen(); rows++) {
      for (int cols = 0; cols < card.dimen(); cols++) {
        current = (card.get(rows, cols).toString());

        // Set bingo cells to the marked-off string
        if (current.equals("0")) {
          current = ELIM;
        } // if

        // Digit space adjust
        if (current.length() == 1) {
          current = "0" + current;
        } // if

        // Print dividers
        if (cols == card.dimen() - 1) {
          line = line + "| " + current + " |";
        } else {
          line = line +  "| " + current + " ";
        } // elif
      } // for (cols)
      line = line + "\n+----+----+----+----+\n";
    } // for (rows)
    return line;
  } // cardToString(BingoCard)
} // BingoGraphics class
