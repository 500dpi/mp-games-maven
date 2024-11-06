package edu.grinnell.csc207.util.game;

public class BingoGraphics {

  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The string that marks a matching bingo cell.
   */
  private static final String ELIM = "XX";
  
  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  // Bingo Card:
  //
  // +----+----+----+----+
  // | XX | XX | XX | XX |
  // +----+----+----+----+
  // | XX | XX | XX | XX |
  // +----+----+----+----+
  // | XX | XX | XX | XX |
  // +----+----+----+----+
  // | XX | XX | XX | XX |
  // +----+----+----+----+

  /**
   * Prints the bingo card as a string to display.
   *
   * @param card
   *    The bingo card.
   */
  public static void printCard(BingoCard card) {
    cardToString(card);
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

    for (int rows = 0; rows < card.dimensions; rows++) {
      for (int cols = 0; cols < card.dimensions; cols++) {
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
        if (cols == card.dimensions - 1) {
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
