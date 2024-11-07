package edu.grinnell.csc207.util.logic;

import edu.grinnell.csc207.util.matrix.ArraySizeException;
import edu.grinnell.csc207.util.matrix.Matrix;
import edu.grinnell.csc207.util.matrix.MatrixV0;

import java.util.Random;

/**
 * The bingo card and associated rules.
 *
 * @author Sara Jaljaa
 */
public class BingoCard {

  // Constants

  /**
   * The default size of the bingo card (4x4).
   */
  private static final Integer DEF = 4;

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The bingo card.
   */
  private Matrix<Integer> card;

  /**
   * The side lengths of the square bingo card.
   */
  private Integer length;

  /**
   * The random number for the card's values.
   */
  private Random rnd;

  // +--------------+-------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Construct a bingo card that is a length x length
   * square.
   *
   * @param length
   *    The length of one side of the square.
   */
  public BingoCard(Integer length) {
    this.length = length;
    this.card = new MatrixV0<Integer>(length, length, 0);
    this.rnd = new Random();
    Integer[] vals = new Integer[length];

    try {
      for (int i = 0; i < length; i++) {
        this.card.insertCol((i % 2), this.randomize(vals));
      } // for
    } catch (ArraySizeException e) {
      return; // fix later
    } // try/catch

    // Free space if the card side lengths are odd
    if ((length % 2) == 1) {
      this.card.set((length / 2), (length / 2), 0);
    } // if
    // + Something with a random seed idk
  } // BingoCard(int)

  /**
   * Construct a bingo card from a Matrix.
   *
   * @param card
   *    The matrix array.
   */
  public BingoCard(Matrix<Integer> card) {
    this.card = card;
    if (card.height() == card.width()) {
      this.length = card.height();
    } else {
      this.length = DEF;
    } // elif
  } // BingoCard(Matrix<Integer>)

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Randomizes a column of the bingo grid.
   *
   * @param vals
   *    A column array of the bingo card.
   * @return
   *    A randomized array of Integers.
   */
  public Integer[] randomize(Integer[] vals) {
    for (int i = 0; i < vals.length; i++) {
      vals[i] = this.rnd.nextInt(3 * (this.length * this.length)) + 1;
    } // for
    return vals;
  } // randomize(Integer[])

  /**
   * Retrieve the value of a bingo card space.
   *
   * @param row
   *    The row number of the space (0-based).
   * @param col
   *    The column number of the space (0-based).
   * @return
   *    The value at the coordinate (row, col).
   */
  public Integer get(Integer row, Integer col) {
    return this.card.get(row, col);
  } // get(int, int)

  /**
   * The card dimensions.
   *
   * @return
   *    An integer representing the sides of the bingo square.
   */
  public Integer length() {
    return this.length;
  } // length()

  /**
   * If the value matches a cell on the bingo card, that cell is
   * crossed out.
   *
   * @param val
   *    The value to compare to.
   * @return
   *    True or false if at least one matching value is found.
   */
  public boolean match(Integer val) {
    boolean state = false;
    // Look through all the grid elements, if there is a match set
    // the index's value to 0 (i.e. crossed out)
    for (Integer rows = 0; rows < this.length; rows++) {
      for (Integer cols = 0; cols < this.length; cols++) {
        if (this.card.get(rows, cols) == val) {
          this.card.set(rows, cols, 0);
          state = true;
        } // if
      } // for(cols)
    } // for (rows)
    return state;
  } // match()

  /**
   * Horizontal bingo win.
   *
   * @return
   *    True or false if there is a single line of horizontal bingos.
   */
  public boolean horizWin() {
    Integer counter;

    // Check the number of consecutive bingo cells in a row
    for (Integer rows = 0; rows < this.length; rows++) {
      counter = 0;
      for (Integer cols = 0; cols < this.length; cols++) {
        if (this.card.get(rows, cols) == 0) {
          counter++;
          if (counter == 4) {
            return true;
          } // if
        } else {
          break;
        } // elif
      } // for(cols)
    } // for(rows)
    return false;
  } // horizWin()

  /**
   * Vertical bingo win.
   *
   * @return
   *    True or false if there is a single line of vertical bingos.
   */
  public boolean vertWin() {
    Integer counter;

    // Check the number of consecutive bingo cells in a column
    for (Integer cols = 0; cols < this.length; cols++) {
      counter = 0;
      for (Integer rows = 0; rows < this.length; rows++) {
        if (this.card.get(rows, cols) == 0) {
          counter++;
          if (counter == 4) {
            return true;
          } // if
        } else {
          break;
        } // elif
      } // for(cols)
    } // for(rows)
    return false;
  } // vertWin()

  /**
   * Diagonal bingo win.
   *
   * @return
   *    True or false if there is a line of diagonal bingos.
   */
  public boolean diagWin() {
    Integer counter = 0;
    Integer rows = 0;
    Integer cols = 0;

    while (rows != this.length && cols != this.length) {
      if (this.card.get(rows, cols) == 0) {
        counter++;
      } else {
        counter = 0;
      } // elif

      rows++;
      cols++;

      if (counter == 4) {
        return true;
      } // if
    } // while

    // Reset to check values in reverse diagonal
    rows = 0;
    cols = this.length - 1;
    counter = 0;

    while (rows != this.length && cols != -1) {
      if (this.card.get(rows, cols) == 0) {
        counter++;
      } else {
        counter = 0;
      } // elif

      rows++;
      cols--;

      if (counter == 4) {
        return true;
      } // if
    } // while
    return false;
  } // diagWin()

  // +-------+-------------------------------------------------------
  // | Extra |
  // +-------+

  /**
   * Determine if the bingo card is fully blacked-out.
   *
   * @return
   *    True or false if the entire card if full of bingos.
   */
  public boolean blackout() {
    for (Integer rows = 0; rows < this.length; rows++) {
      for (Integer cols = 0; cols < this.length; cols++) {
        if (!(this.card.get(rows, cols) == 0)) {
          return false;
        } // if
      } // for(cols)
    } // for(rows)
    return true;
  } // blackout()
} // BingoCard class
