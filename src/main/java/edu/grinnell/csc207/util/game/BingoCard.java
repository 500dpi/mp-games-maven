package edu.grinnell.csc207.util.game;

import edu.grinnell.csc207.util.matrix.Matrix;
import edu.grinnell.csc207.util.matrix.MatrixV0;

import java.util.Random;
import java.io.PrintWriter;

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
   * The dimensions of the square bingo card.
   */
  private Integer dimensions;

  /**
   * To generate randomized bingo card values.
   */
  private Random randomCard;

  // +-------------+-------------------------------------------------
  // | Constructor |
  // +-------------+

  /**
   * Construct a bingo card that is dimen x dimen.
   *
   * @param dimen
   *    The length of one side of the square.
   */
  public BingoCard(Integer dimen) {
    this.dimensions = dimen;
    this.card = new MatrixV0<Integer>(dimen, dimen, 0);
    // Free space
    if ((dimen % 2) == 1) {
      this.card.set((dimen / 2) - 1, (dimen / 2) - 1, 0);
    } // if
    // + Something with a random seed idk
  } // BingoCard(int)

  /**
   * Construct a bingo card from a Matrix.
   *
   * @param mat
   *    The matrix array.
   */
  public BingoCard(Matrix<Integer> mat) {
    this.card = mat;
    if (mat.height() == mat.width()) {
      this.dimensions = mat.height();
    } else {
      this.dimensions = DEF;
    } // elif
  } // BingoCard(Matrix<Integer>)

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

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
   * The square dimensions.
   *
   * @return
   *    An integer representing the sides of the bingo square.
   */
  public Integer dimen() {
    return this.dimensions;
  } // dimen()

  /**
   * If the value matches a cell on the bingo card, that cell is
   * crossed out.
   *
   * @param val
   *    The value to compare to.
   * @return
   *    True or false if a matching value is found.
   */
  public boolean match(Integer val) {
    // Look through all the grid elements, if there is a match set
    // the index's value to 0 (i.e. crossed out)
    for (Integer rows = 0; rows < this.dimensions; rows++) {
      for (Integer cols = 0; cols < this.dimensions; cols++) {
        if (this.card.get(rows, cols) == val) {
          this.card.set(rows, cols, 0);
          return true;
        } // if
      } // for(cols)
    } // for (rows)
    return false;
  } // match()

  /**
   * Horizontal bingo win.
   *
   * @return
   *    True or false if there is a line of bingos.
   */
  public boolean horizWin() {
    Integer counter;
    PrintWriter pen = new PrintWriter(System.out, true);

    // Check the number of consecutive bingo cells in a row
    for (Integer rows = 0; rows < this.dimensions; rows++) {
      counter = 0;
      for (Integer cols = 0; cols < this.dimensions; cols++) {
        pen.println(counter);
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
   *    True or false if there is a line of bingos.
   */
  public boolean vertWin() {
    Integer counter;

    // Check the number of consecutive bingo cells in a column
    for (Integer cols = 0; cols < this.dimensions; cols++) {
      counter = 0;
      for (Integer rows = 0; rows < this.dimensions; rows++) {
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
   *    True or false if there is a line of bingos.
   */
  public boolean diagWin() {
    Integer counter = 0;
    Integer rows = 0;
    Integer cols = 0;

    while (rows != this.dimensions && cols != this.dimensions) {
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
    cols = this.dimensions - 1;
    counter = 0;

    while (rows != this.dimensions && cols != -1) {
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
    for (Integer rows = 0; rows < this.dimensions; rows++) {
      for (Integer cols = 0; cols < this.dimensions; cols++) {
        if (!(this.card.get(rows, cols) == 0)) {
          return false;
        } // if
      } // for(cols)
    } // for(rows)
    return true;
  } // blackout()
} // BingoCard class
