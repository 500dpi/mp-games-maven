package edu.grinnell.csc207.util.logic;

import java.util.Random;

import edu.grinnell.csc207.util.matrix.Matrix;

/**
 * Random number generation for bingo.
 */
public final class BingoNumbers {

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  private Random callRng;

  /**
   * To hold randomized grid values in the bingo card.
   */
  private Random gridRng;

  private Integer bound;

  // +--------------+-------------------------------------------------
  // | Constructors |
  // +--------------+

  public BingoNumbers(Integer dimen) {
    this.callRng = new Random();
    this.gridRng = new Random();
    this.bound = (dimen * dimen) * 3;
  } // BingoNumbers()

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  public int getRandom() {
    return this.callRng.nextInt(this.bound) + 1;
  } // getRandom()

  /**
   * Randomizes a column of the bingo grid.
   *
   * @param card
   *    The matrix that stores the bingo card values.
   * @return
   *    A randomized array of Integers.
   */
  public Integer[] randomCard(Matrix<Integer> card) {
    Integer[] grid = new Integer[card.height()];

    for (int i = 0; i < grid.length; i++) {
      grid[i] = (Integer) this.gridRng.nextInt(
          3 * (grid.length * grid.length)) + 1;
    } // for
    return grid;
  } // randomize(Integer[])
} // class BingoNumbers
